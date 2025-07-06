import React, { useState } from 'react';
import {
    Form,
    redirect,
    useActionData,
    useLoaderData,
} from 'react-router';
import apiConfig from '../../../api.config';
import settings from '../../../settings';
import StudentForm from '../../../layout/forms/StudentForm';
import { validateStudent } from '../../../utilities/validation'; // Assuming you'll create this validation function

export async function loader() {
    // 1) get all parents for this school
    const parentsRes = await fetch(
        `${apiConfig.baseUrl}/parents?schoolId=${settings.schoolId}&size=1000`
    );
    const parentsRaw = (await parentsRes.json()).content ?? [];

    const parents = parentsRaw;

    return parents;
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
        grade: Number(formData.get('grade')),
        classId: Number(formData.get('classId')),
        parentIds: formData.getAll('parentIds').map(Number),
    };

    const errors = validateStudent(payload, 'CREATE');
    if (Object.keys(errors).length) return { errors };

    const res = await fetch(`${apiConfig.baseUrl}/students`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    });

    if (!res.ok) {
        const msg = (await res.json())?.message ?? 'Failed to create student.';
        return { errors: { general: msg } };
    }

    return redirect('/students');
}

export default function CreateStudent() {
    const parents = useLoaderData();
    const actionData = useActionData();
    const errors = actionData?.errors ?? {};

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

    const handleFormChange = (field, value) => {
        setFormData(prev => ({ ...prev, [field]: value }));
    };
    
    const handleParentSelectionChange = (newParentIds) => {
        setFormData(prev => ({ ...prev, parentIds: newParentIds }));
    };

    return (
        <div className="p-6 max-w-xl mx-auto">
            <h1 className="text-2xl font-bold mb-4">Add Student</h1>

            {errors.general && (
                <div className="alert alert-error mb-4">{errors.general}</div>
            )}

            <Form method="post" replace>
                <StudentForm
                    parents={parents}
                    errors={errors}
                    formData={formData}
                    onFormChange={handleFormChange}
                    onParentChange={handleParentSelectionChange}
                    isEdit={false}
                />
            </Form>
        </div>
    );
}
