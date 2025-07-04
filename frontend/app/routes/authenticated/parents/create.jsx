import React, { useState } from 'react';
import {
    Form,
    redirect,
    useActionData,
    useLoaderData,
} from 'react-router';
import apiConfig from '../../../api.config';
import settings from '../../../settings';
import ParentForm from '../../../layout/forms/ParentForm';
import { validateParent } from '../../../utilities/validation';

export async function loader() {
    // 1) get all students for this school
    const studentsRes = await fetch(
        `${apiConfig.baseUrl}/students?schoolId=${settings.schoolId}&size=1000`
    );
    const studentsRaw = (await studentsRes.json()).content ?? [];

    // The backend now returns students with firstName and lastName directly
    const students = studentsRaw;

    return students;
}

export async function action({ request }) {
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

    const errors = validateParent(payload, 'CREATE');
    if (Object.keys(errors).length) return { errors };

    const res = await fetch(`${apiConfig.baseUrl}/parents`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    });

    if (!res.ok) {
        const msg = (await res.json())?.message ?? 'Failed to create parent.';
        return { errors: { general: msg } };
    }

    return redirect('/parents');
}

export default function CreateParent() {
    const students = useLoaderData();
    const actionData = useActionData();
    const errors = actionData?.errors ?? {};

    // Central state for the entire form
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        repeatPassword: '',
        studentIds: [],
    });

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
            <h1 className="text-2xl font-bold mb-4">Add Parent</h1>

            {errors.general && (
                <div className="alert alert-error mb-4">{errors.general}</div>
            )}

            <Form method="post" replace>
                <ParentForm
                    students={students}
                    errors={errors}
                    formData={formData}
                    onFormChange={handleFormChange}
                    onStudentChange={handleStudentSelectionChange}
                    isEdit={false}
                />
            </Form>
        </div>
    );
}