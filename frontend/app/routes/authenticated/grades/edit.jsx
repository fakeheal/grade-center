import React, { useState, useEffect } from 'react';
import {
    Form,
    redirect,
    useActionData,
    useLoaderData,
    useParams,
    Link,
} from 'react-router';
import apiConfig from '../../../api.config';
import settings from '../../../settings';
import GradeForm from '../../../layout/forms/GradeForm';
import { validateGrade } from '../../../utilities/validation';

export async function loader({ params }) {
    const gradeRes = await fetch(`${apiConfig.baseUrl}/grades/${params.id}`);
    if (!gradeRes.ok) throw new Response('Grade not found', { status: 404 });
    const grade = await gradeRes.json();

    const [studentsRes, teachersRes, subjectsRes] = await Promise.all([
        fetch(`${apiConfig.baseUrl}/students?schoolId=${settings.schoolId}&size=1000`),
        fetch(`${apiConfig.baseUrl}/teachers?schoolId=${settings.schoolId}&size=1000`),
        fetch(`${apiConfig.baseUrl}/subjects?schoolId=${settings.schoolId}&size=1000`),
    ]);

    const studentsRaw = (await studentsRes.json()).content ?? [];
    const teachersRaw = (await teachersRes.json()).content ?? [];
    const subjectsRaw = (await subjectsRes.json()) ?? [];

    const students = studentsRaw.map(s => ({ id: s.id, firstName: s.firstName, lastName: s.lastName }));
    const teachers = teachersRaw.map(t => ({ id: t.id, user: { firstName: t.user.firstName, lastName: t.user.lastName } }));
    const subjects = subjectsRaw.map(sub => ({ id: sub.id, name: sub.name }));

    return { grade, students, teachers, subjects };
}

export async function action({ request, params }) {
    const formData = await request.formData();

    const payload = {
        studentId: Number(formData.get('studentId')),
        teacherId: Number(formData.get('teacherId')),
        subjectId: Number(formData.get('subjectId')),
        value: Number(formData.get('value')),
        date: new Date().toISOString().split('T')[0], // Current date
        schoolYearId: 1, // TODO: Implement dynamic school year selection
    };

    const errors = validateGrade(payload, 'EDIT');
    if (Object.keys(errors).length) return { errors };

    const res = await fetch(`${apiConfig.baseUrl}/grades/${params.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    });

    if (!res.ok) {
        const msg = (await res.json())?.message ?? 'Failed to update grade.';
        return { errors: { general: msg } };
    }

    return { success: true };
}

export default function EditGrade() {
    const { grade, students, teachers, subjects } = useLoaderData();
    const actionData = useActionData();
    const errors = actionData?.errors ?? {};
    const success = actionData?.success;

    const [formData, setFormData] = useState({
        studentId: '',
        teacherId: '',
        subjectId: '',
        value: '',
    });

    useEffect(() => {
        if (grade) {
            setFormData({
                studentId: grade.student.id,
                teacherId: grade.teacher.id,
                subjectId: grade.subject.id,
                value: grade.value,
            });
        }
    }, [grade]);

    const handleFormChange = (field, value) => {
        setFormData(prev => ({ ...prev, [field]: value }));
    };

    return (
        <div className="p-6 max-w-xl mx-auto">
            <h1 className="text-2xl font-bold mb-4">Edit Grade</h1>

            {success && (
                <div className="alert alert-success mb-4">
                    Grade saved successfully.
                </div>
            )}
            {errors.general && (
                <div className="alert alert-error mb-4">{errors.general}</div>
            )}

            <Form method="post">
                <GradeForm
                    students={students}
                    teachers={teachers}
                    subjects={subjects}
                    errors={errors}
                    formData={formData}
                    onFormChange={handleFormChange}
                />
            </Form>

            <hr className="my-6" />

            <Link to="/grades" className="btn btn-neutral w-full">
                Back to Grades List
            </Link>
        </div>
    );
}
