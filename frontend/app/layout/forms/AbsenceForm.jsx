import React from 'react';
import AbsenceStudentSelect from './components/AbsenceStudentSelect';
import ClassSelect from './components/ClassSelect';
import SubjectSelect from './components/SubjectSelect';

export default function AbsenceForm({
                                       students = [],
                                       classes = [],
                                       subjects = [],
                                       errors = {},
                                       formData,
                                       onFormChange,
                                   }) {
    return (
        <>
            <AbsenceStudentSelect
                students={students}
                selectedId={formData.studentId}
                onSelectionChange={(id) => onFormChange('studentId', id)}
                error={errors?.studentId}
            />

            <div className="mb-2">
                <label className="fieldset-label" htmlFor="classId">Class</label>
                <ClassSelect
                    classes={classes}
                    onClassChanged={(id) => onFormChange('classId', id)}
                />
                {errors?.classId &&
                    <p className="text-error text-xs mt-1">{errors.classId}</p>}
            </div>

            <div className="mb-2">
                <label className="fieldset-label" htmlFor="subjectIds">Subject</label>
                <SubjectSelect
                    subjects={subjects}
                    selectedIds={formData.subjectIds}
                    onSelectionChange={(ids) => onFormChange('subjectIds', ids)}
                    errors={errors}
                    singleSelect={false}
                />
                {errors?.subjectIds &&
                    <p className="text-error text-xs mt-1">{errors.subjectIds}</p>}
            </div>

            <div className="mb-2">
                <label className="fieldset-label" htmlFor="date">Date</label>
                <input
                    type="date"
                    className={`input w-full ${errors?.date ? `input-error` : ``}`}
                    name="date"
                    value={formData.date}
                    onChange={(e) => onFormChange('date', e.target.value)}
                    id="date"
                />
                {errors?.date &&
                    <p className="text-error text-xs mt-1">{errors.date}</p>}
            </div>

            <div className="mb-2">
                <label className="fieldset-label" htmlFor="hour">Hour</label>
                <input
                    type="number"
                    className={`input w-full ${errors?.hour ? `input-error` : ``}`}
                    placeholder="Enter hour (1-8)"
                    name="hour"
                    value={formData.hour}
                    onChange={(e) => onFormChange('hour', e.target.value)}
                    id="hour"
                    min="1"
                    max="8"
                />
                {errors?.hour &&
                    <p className="text-error text-xs mt-1">{errors.hour}</p>}
            </div>

            <div className="form-control mb-2">
                <label className="label cursor-pointer">
                    <span className="label-text">Excused</span>
                    <input
                        type="checkbox"
                        className="checkbox checkbox-primary"
                        name="excused"
                        checked={formData.excused}
                        onChange={(e) => onFormChange('excused', e.target.checked)}
                    />
                </label>
            </div>

            <div className="mb-2">
                <label className="fieldset-label" htmlFor="reason">Reason (Optional)</label>
                <textarea
                    className="textarea textarea-bordered w-full"
                    placeholder="Enter reason for absence..."
                    name="reason"
                    value={formData.reason}
                    onChange={(e) => onFormChange('reason', e.target.value)}
                    id="reason"
                ></textarea>
                {errors?.reason &&
                    <p className="text-error text-xs mt-1">{errors.reason}</p>}
            </div>

            <button type="submit" className="btn btn-neutral mt-4 w-full">
                Add Absence
            </button>
        </>
    );
}