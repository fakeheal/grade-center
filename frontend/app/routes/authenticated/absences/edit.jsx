import React, { useState } from 'react';
import { Form, redirect, useActionData, useLoaderData } from 'react-router';
import apiConfig from '../../../api.config';
import settings from '../../../settings';
import AbsenceForm from '../../../layout/forms/AbsenceForm';
import validateAbsence from '../../../utilities/validation';

export function meta() {
    return [
        { title: 'Edit Absence - Grade Center' },
        { name: 'description', content: 'Edit student absence.' },
    ];
}

export async function loader({ params }) {
    console.log("Fetching absence for ID:", params.id);
    const absenceUrl = `${apiConfig.baseUrl}/absences/${params.id}`;
    console.log("Absence fetch URL:", absenceUrl);
    const [absenceRes, studentsRes, classesRes, subjectsRes] = await Promise.all([
        fetch(absenceUrl),
        fetch(`${apiConfig.baseUrl}/students?schoolId=${settings.schoolId}&size=1000`),
        fetch(`${apiConfig.baseUrl}/classes?schoolId=${settings.schoolId}&size=1000`),
        fetch(`${apiConfig.baseUrl}/subjects?schoolId=${settings.schoolId}&size=1000`),
    ]);

    if (!absenceRes.ok) throw new Response('Failed to load absence', { status: 500 });

    const absence = await absenceRes.json();
    const studentsRaw = (await studentsRes.json()).content ?? [];
    const classesRaw = (await classesRes.json()).content ?? [];
    const subjectsRaw = (await subjectsRes.json()) ?? [];

    const students = studentsRaw.map(s => ({ id: s.id, firstName: s.firstName, lastName: s.lastName }));
    const classes = classesRaw.map(c => ({ id: c.id, name: c.name, grade: c.grade }));
    const subjects = subjectsRaw.map(s => ({ id: s.id, name: s.name }));

    return { absence, students, classes, subjects };
}

export async function action({ request, params }) {
    const formData = await request.formData();

    const payload = {
        studentId: Number(formData.get('studentId')),
        classId: Number(formData.get('classId')),
        subjectId: Number(formData.getAll('subjectIds')[0]),
        date: formData.get('date'),
        hour: Number(formData.get('hour')),
        excused: formData.get('excused') === 'on',
        reason: formData.get('reason')?.trim() || null,
    };

    const errors = validateAbsence(payload);
    if (Object.keys(errors).length) return { errors };

    const res = await fetch(`${apiConfig.baseUrl}/absences/${params.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    });

    if (!res.ok) {
        const msg = (await res.json())?.message ?? 'Failed to update absence.';
        return { errors: { general: msg } };
    }

    return redirect('/absences');
}

export default function EditAbsence() {
    const { absence, students, classes, subjects } = useLoaderData();
    const actionData = useActionData();
    const errors = actionData?.errors ?? {};

    const [formData, setFormData] = useState({
        studentId: absence.student.id,
        classId: absence.classEntity.id,
        subjectIds: [absence.subject.id],
        date: absence.date,
        hour: absence.hour,
        excused: absence.excused,
        reason: absence.reason || '',
    });

    const handleFormChange = (field, value) => {
        setFormData(prev => ({ ...prev, [field]: value }));
    };

    return (
        <div className="p-6 max-w-xl mx-auto">
            <h1 className="text-2xl font-bold mb-4">Edit Absence</h1>

            {errors.general && (
                <div className="alert alert-error mb-4">{errors.general}</div>
            )}

            <Form method="post" replace>
                <AbsenceForm
                    students={students}
                    classes={classes}
                    subjects={subjects}
                    errors={errors}
                    formData={formData}
                    onFormChange={handleFormChange}
                />
            </Form>
        </div>
    );
}
