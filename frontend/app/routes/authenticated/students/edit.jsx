import { Form, redirect, useActionData, useLoaderData, useParams } from 'react-router';
import apiConfig from '../../../api.config';
import settings from '../../../settings';
import StudentForm from '../../../layout/forms/StudentForm';
import { validateStudent } from '../../../utilities/validation';

export async function loader({ params }) {
    // fetch student
    const studentRes = await fetch(`${apiConfig.baseUrl}/students/${params.id}`);
    if (!studentRes.ok) throw new Response('Student not found', { status: 404 });
    const student = await studentRes.json();

    // fetch parents
    const parentsRes = await fetch(
        `${apiConfig.baseUrl}/parents?schoolId=${settings.schoolId}&size=1000`
    );
    const parentsRaw = (await parentsRes.json()).content ?? [];
    const parents = parentsRaw;

    return { student, parents };
}

export async function action({ request, params }) {
    const formData = await request.formData();

    const payload = {
        firstName: formData.get('firstName')?.trim(),
        lastName: formData.get('lastName')?.trim(),
        email: formData.get('email')?.trim(),
        password: formData.get('password'),
        repeatPassword: formData.get('repeatPassword'),
        schoolId: settings.schoolId,
        grade: Number(formData.get('grade')),
        classId: Number(formData.get('classId')),
        parentIds: formData.getAll('parentIds').map(Number),
    };

    const errors = validateStudent(payload, 'EDIT');
    if (Object.keys(errors).length) return { errors };

    const res = await fetch(`${apiConfig.baseUrl}/students/${params.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    });

    if (!res.ok) {
        const msg = (await res.json())?.message ?? 'Failed to update student.';
        return { errors: { general: msg } };
    }

    return { success: true };
}

import React, { useState, useEffect } from 'react';
import { Link } from 'react-router';

export default function EditStudent() {
    const { student, parents } = useLoaderData();
    const actionData = useActionData();
    const errors = actionData?.errors ?? {};
    const success = actionData?.success;

    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        repeatPassword: '',
        grade: '',
        classId: '',
        parentIds: [],
    });

    useEffect(() => {
        if (student) {
            setFormData({
                firstName: student.firstName ?? '',
                lastName: student.lastName ?? '',
                email: student.email ?? '',
                password: '',
                repeatPassword: '',
                grade: student.grade ?? '',
                classId: student.classId ?? '',
                parentIds: student.parentIds || student.parents?.map(p => p.id) || [],
            });
        }
    }, [student]);

    const handleFormChange = (field, value) => {
        setFormData(prev => ({ ...prev, [field]: value }));
    };
    
    const handleParentSelectionChange = (newParentIds) => {
        setFormData(prev => ({ ...prev, parentIds: newParentIds }));
    };

    return (
        <div className="p-6 max-w-xl mx-auto">
            <h1 className="text-2xl font-bold mb-4">Edit Student</h1>

            {success && (
                <div className="alert alert-success mb-4">
                    Student saved successfully.
                </div>
            )}
            {errors.general && (
                <div className="alert alert-error mb-4">{errors.general}</div>
            )}

            <Form method="post">
                <StudentForm
                    parents={parents}
                    errors={errors}
                    formData={formData}
                    onFormChange={handleFormChange}
                    onParentChange={handleParentSelectionChange}
                    isEdit={true}
                />
            </Form>

            <hr className="my-6" />

            <Link to="/students" className="btn btn-neutral w-full">
                Back to Student List
            </Link>
        </div>
    );
}
