import React from 'react';
import SubjectSelect from '../forms/components/SubjectSelect';
import StudentSelect from '../forms/components/StudentSelect';
import TeacherSelect from '../forms/components/TeacherSelect'; // Assuming you'll create this

export default function GradeForm({
                                      students = [],
                                      teachers = [],
                                      subjects = [],
                                      errors = {},
                                      formData,
                                      onFormChange,
                                  }) {
    return (
        <>
            <div className="mb-2">
                <label className="fieldset-label" htmlFor="studentId">Student</label>
                <StudentSelect
                    students={students}
                    selectedIds={formData.studentId ? [formData.studentId] : []}
                    onSelectionChange={(ids) => onFormChange('studentId', ids[0] || '')}
                    errors={errors}
                    singleSelect={true}
                />
                {errors?.studentId &&
                    <p className="text-error text-xs mt-1">{errors.studentId}</p>}
            </div>

            <div className="mb-2">
                <label className="fieldset-label" htmlFor="teacherId">Teacher</label>
                <TeacherSelect
                    teachers={teachers}
                    selectedIds={formData.teacherId ? [formData.teacherId] : []}
                    onSelectionChange={(ids) => onFormChange('teacherId', ids[0] || '')}
                    errors={errors}
                    singleSelect={true}
                />
                {errors?.teacherId &&
                    <p className="text-error text-xs mt-1">{errors.teacherId}</p>}
            </div>

            <div className="mb-2">
                <label className="fieldset-label" htmlFor="subjectId">Subject</label>
                <SubjectSelect
                    subjects={subjects}
                    selectedIds={formData.subjectId ? [formData.subjectId] : []}
                    onSelectionChange={(ids) => onFormChange('subjectId', ids[0] || '')}
                    errors={errors}
                    singleSelect={true}
                />
                {errors?.subjectId &&
                    <p className="text-error text-xs mt-1">{errors.subjectId}</p>}
            </div>

            <div className="mb-2">
                <label className="fieldset-label" htmlFor="value">Grade Value</label>
                <input
                    type="number"
                    className={`input w-full ${errors?.value ? `input-error` : ``}`}
                    placeholder="Enter grade value (1-6)"
                    name="value"
                    value={formData.value}
                    onChange={(e) => onFormChange('value', e.target.value)}
                    id="value"
                    min="1"
                    max="6"
                    step="0.01"
                />
                {errors?.value &&
                    <p className="text-error text-xs mt-1">{errors.value}</p>}
            </div>

            <button type="submit" className="btn btn-neutral mt-4 w-full">
                Add Grade
            </button>
        </>
    );
}
