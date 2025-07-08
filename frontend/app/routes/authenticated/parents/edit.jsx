import {
    Form,
    Link,
    redirect,
    useActionData,
    useLoaderData,
    useParams,
} from 'react-router';
import apiConfig from '../../../api.config';
import settings from '../../../settings';
import ParentForm from '../../../layout/forms/ParentForm';
import { validateParent } from '../../../utilities/validation';
import { getJwt } from '../../../utilities/auth';

export async function loader({ request, params }) {
    // fetch parent
    const token = getJwt(request.headers.get('cookie') ?? '');
    const parentRes = await fetch(`${apiConfig.baseUrl}/parents/${params.id}`, { headers: { Authorization: `Bearer ${token}` } });
    if (!parentRes.ok) throw new Response('Parent not found', { status: 404 });
    const parent = await parentRes.json();

    // fetch students
    const studentsRes = await fetch(
        `${apiConfig.baseUrl}/students?schoolId=${settings.schoolId}&size=1000`, { headers: { Authorization: `Bearer ${token}` } }
    );
    const studentsRaw = (await studentsRes.json()).content ?? [];

    // The backend now returns students with firstName and lastName directly
    const students = studentsRaw;

    // The parent object now directly contains studentIds from the backend
    // No need for complex pre-selection logic here

    return { parent, students };
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
        studentIds: formData.getAll('studentIds').map(Number),
    };

    const errors = validateParent(payload, 'EDIT');
    if (Object.keys(errors).length) return { errors };

    const token = getJwt(request.headers.get('cookie') ?? '');
    const res = await fetch(`${apiConfig.baseUrl}/parents/${params.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
    });

    if (!res.ok) {
        const msg = (await res.json())?.message ?? 'Failed to update.';
        return { errors: { general: msg } };
    }

    return { success: true };
}

import React, { useState, useEffect } from 'react';

export default function EditParent() {
    const { parent, students } = useLoaderData();
    const actionData = useActionData();
    const errors = actionData?.errors ?? {};
    const success = actionData?.success;

    // Central state for the entire form
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        repeatPassword: '',
        studentIds: [],
    });

    // Effect to populate form state when loader data is available
    useEffect(() => {
        if (parent) {
            setFormData({
                firstName: parent.firstName ?? '',
                lastName: parent.lastName ?? '',
                email: parent.email ?? '',
                password: '',
                repeatPassword: '',
                studentIds: parent.studentIds || parent.children?.map(c => c.id) || [],
            });
        }
    }, [parent]);

    // Handler to update state for any form field
    const handleFormChange = (field, value) => {
        setFormData(prev => ({ ...prev, [field]: value }));
    };
    
    // Handler specifically for student selections
    const handleStudentSelectionChange = (newStudentIds) => {
        setFormData(prev => ({ ...prev, studentIds: newStudentIds }));
    };

    return (
        <div className="p-6 max-w-xl mx-auto">
            <h1 className="text-2xl font-bold mb-4">Edit Parent</h1>

            {success && (
                <div className="alert alert-success mb-4">
                    Parent saved successfully.
                </div>
            )}
            {errors.general && (
                <div className="alert alert-error mb-4">{errors.general}</div>
            )}

            <Form method="post">
                <ParentForm
                    students={students}
                    errors={errors}
                    formData={formData}
                    onFormChange={handleFormChange}
                    onStudentChange={handleStudentSelectionChange}
                    isEdit={true}
                />
            </Form>

            <hr className="my-6" />

            <Link to="/parents" className="btn btn-neutral w-full">
                Back to Parent List
            </Link>
        </div>
    );
}
