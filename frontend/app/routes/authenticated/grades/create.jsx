import React, { useState } from 'react';
import {
    Form,
    redirect,
    useActionData,
    useLoaderData,
} from 'react-router';
import apiConfig from '../../../api.config';
import settings from '../../../settings';
import GradeForm from '../../../layout/forms/GradeForm';
import { validateGrade } from '../../../utilities/validation'; // Assuming you'll create this validation function
import { getJwt } from '../../../utilities/auth';

export async function loader({ request }) {
    const token = getJwt(request.headers.get('cookie') ?? '');
    const [studentsRes, teachersRes, subjectsRes] = await Promise.all([
        fetch(`${apiConfig.baseUrl}/students?schoolId=${settings.schoolId}&size=1000`, { headers: { Authorization: `Bearer ${token}` } }),
        fetch(`${apiConfig.baseUrl}/teachers?schoolId=${settings.schoolId}&size=1000`, { headers: { Authorization: `Bearer ${token}` } }),
        fetch(`${apiConfig.baseUrl}/subjects?schoolId=${settings.schoolId}&size=1000`, { headers: { Authorization: `Bearer ${token}` } }),
    ]);

    const studentsRaw = (await studentsRes.json()).content ?? [];
    const teachersRaw = (await teachersRes.json()).content ?? [];
    const subjectsRaw = (await subjectsRes.json()) ?? [];

    const students = studentsRaw.map(s => ({ id: s.id, firstName: s.firstName, lastName: s.lastName }));
    const teachers = teachersRaw.map(t => ({ id: t.id, user: { firstName: t.user.firstName, lastName: t.user.lastName } }));
    const subjects = subjectsRaw.map(sub => ({ id: sub.id, name: sub.name }));

    return { students, teachers, subjects };
}

export async function action({ request }) {
    const formData = await request.formData();

    const payload = {
        studentId: Number(formData.get('studentId')),
        teacherId: Number(formData.get('teacherId')),
        subjectId: Number(formData.get('subjectId')),
        value: Number(formData.get('value')),
        date: new Date().toISOString().split('T')[0], // Current date
        schoolYearId: 1, // TODO: Implement dynamic school year selection
    };

    const errors = validateGrade(payload, 'CREATE');
    if (Object.keys(errors).length) return { errors };

    const token = getJwt(request.headers.get('cookie') ?? '');
    const res = await fetch(`${apiConfig.baseUrl}/grades`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
    });

    if (!res.ok) {
        const msg = (await res.json())?.message ?? 'Failed to add grade.';
        return { errors: { general: msg } };
    }

    return redirect('/grades');
}

export default function CreateGrade() {
    const { students, teachers, subjects } = useLoaderData();
    const actionData = useActionData();
    const errors = actionData?.errors ?? {};

    const [formData, setFormData] = useState({
        studentId: '',
        teacherId: '',
        subjectId: '',
        value: '',
    });

    const handleFormChange = (field, value) => {
        setFormData(prev => ({ ...prev, [field]: value }));
    };

    return (
        <div className="p-6 max-w-xl mx-auto">
            <h1 className="text-2xl font-bold mb-4">Add Grade</h1>

            {errors.general && (
                <div className="alert alert-error mb-4">{errors.general}</div>
            )}

            <Form method="post" replace>
                <GradeForm
                    students={students}
                    teachers={teachers}
                    subjects={subjects}
                    errors={errors}
                    formData={formData}
                    onFormChange={handleFormChange}
                />
            </Form>
        </div>
    );
}
