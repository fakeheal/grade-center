import React from 'react';
import UserFields from './components/UserFields';
import StudentSelect from './components/StudentSelect';

export default function ParentForm({
                                       students = [],
                                       errors = {},
                                       formData,
                                       onFormChange,
                                       onStudentChange,
                                       isEdit = false,
                                   }) {
    // Create the setFuncs object for UserFields
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

            <StudentSelect
                students={students}
                selectedIds={formData.studentIds}
                onSelectionChange={onStudentChange}
                errors={errors}
            />

            {/* Hidden inputs for studentIds are now in StudentSelect */}

            <button type="submit" className="btn btn-neutral mt-4 w-full">
                {isEdit ? 'Update' : 'Create'} Parent
            </button>
        </>
    );
}