import React from 'react';
import UserFields from './components/UserFields';
import ParentSelect from './components/ParentSelect'; // Assuming you'll create this

export default function StudentForm({
                                        parents = [],
                                        errors = {},
                                        formData,
                                        onFormChange,
                                        onParentChange,
                                        isEdit = false,
                                    }) {
    const setFuncs = {
        setFirstName: (val) => onFormChange('firstName', val),
        setLastName: (val) => onFormChange('lastName', val),
        setEmail: (val) => onFormChange('email', val),
        setPassword: (val) => onFormChange('password', val),
        setRepeatPassword: (val) => onFormChange('repeatPassword', val),
    };

    return (
        <>
            <UserFields
                values={formData}
                errors={errors}
                setFuncs={setFuncs}
                isEdit={isEdit}
            />

            <div className="mb-2">
                <label className="fieldset-label" htmlFor="grade">Grade</label>
                <input
                    type="number"
                    className={`input w-full ${errors?.grade ? `input-error` : ``}`}
                    placeholder="Enter grade..."
                    name="grade"
                    value={formData.grade}
                    onChange={(e) => onFormChange('grade', e.target.value)}
                    id="grade"
                    min="1"
                    max="12"
                />
                {errors?.grade &&
                    <p className="text-error text-xs mt-1">{errors.grade}</p>}
            </div>

            <div className="mb-2">
                <label className="fieldset-label" htmlFor="classId">Class ID</label>
                <input
                    type="number"
                    className={`input w-full ${errors?.classId ? `input-error` : ``}`}
                    placeholder="Enter class ID..."
                    name="classId"
                    value={formData.classId}
                    onChange={(e) => onFormChange('classId', e.target.value)}
                    id="classId"
                />
                {errors?.classId &&
                    <p className="text-error text-xs mt-1">{errors.classId}</p>}
            </div>

            <ParentSelect
                parents={parents}
                selectedIds={formData.parentIds}
                onSelectionChange={onParentChange}
                errors={errors}
            />

            <button type="submit" className="btn btn-neutral mt-4 w-full">
                {isEdit ? 'Update' : 'Create'} Student
            </button>
        </>
    );
}
