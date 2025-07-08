import React from 'react';

export default function AbsenceStudentSelect({ students, selectedId, onSelectionChange, error }) {
  return (
    <div className="mb-2">
      <label className="fieldset-label" htmlFor="studentId">Student</label>
      <select
        name="studentId"
        className={`select w-full ${error ? 'select-error' : ''}`}
        value={selectedId || ''}
        onChange={(e) => onSelectionChange(e.target.value)}
      >
        <option value="">Select student</option>
        {students.map(student => (
          <option key={student.id} value={student.id}>
            {`${student.firstName} ${student.lastName}`}
          </option>
        ))}
      </select>
      {error && <p className="text-error text-xs mt-1">{error}</p>}
    </div>
  );
}
