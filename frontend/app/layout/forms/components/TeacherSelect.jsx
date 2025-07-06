import React from 'react';

export default function TeacherSelect({ teachers, selectedIds, errors, size }) {
  return (
    <select
      name="subjectIds"
      id="subjects"
      className={`mt-0.5 select w-full ${errors?.subjects ? `input-error` : ``} ${size}`}>
      <option>Choose teacher</option>
      {teachers.map(teacher => (
        <option key={teacher.id} value={teacher.id} selected={selectedIds.includes(teacher.id)}>
          {`${teacher.user.firstName} ${teacher.user.lastName}`}
        </option>
      ))}
    </select>
  )
}
