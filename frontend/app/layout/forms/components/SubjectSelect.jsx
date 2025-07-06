import React from 'react';

export default function SubjectSelect({ multiselect, subjects, selectedIds, errors, size }) {
  return (
    <select
      name="subjectIds"
      id="subjects"
      multiple={multiselect}
      className={`mt-0.5 select w-full ${errors?.subjects ? `input-error` : ``} ${size}`}>
      <option>Select subject</option>
      {subjects.map(subject => (
        <option key={subject.id} value={subject.id} selected={selectedIds.includes(subject.id)}>
          {subject.name}
        </option>
      ))}
    </select>
  )
}
