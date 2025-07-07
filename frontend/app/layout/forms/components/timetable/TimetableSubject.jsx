const formatKey = (subjectWithTeacher) => {
  return `${subjectWithTeacher.teacherId}-${subjectWithTeacher.subjectId}`;
}

export default function TimetableSubject({ day, time, subjectsWithTeachers, selected }) {
  return (
    <>
      <select
        name={`subjectWithTeacher[${day}][${time}]`}
        id="subjectWithTeacher"
        className="mt-0.5 select select-sm w-full">
        <option>Select subject</option>
        {subjectsWithTeachers.map(subjectWithTeacher => {
          const key = formatKey(subjectWithTeacher);
          return (
            <option
              key={key}
              value={key}
              selected={selected === key}>
              {`${subjectWithTeacher.subjectName} (${subjectWithTeacher.teacherName})`}
            </option>
          );
        })}
      </select>
    </>
  )
}
