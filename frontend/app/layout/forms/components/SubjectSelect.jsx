import React from "react";

export default function SubjectSelect({subjects,user, errors }) {
    return (
        <select name="subjectIds" id="subjects" multiple={true}
                className={`mt-0.5 select w-full h-24 ${errors?.subjects ? `input-error` : ``}`}>
            {subjects.map(subject => (
                <option key={subject.id} value={subject.id} selected={user?.subjects?.find(s => s.id === subject.id)}>
                    {subject.name}
                </option>
            ))}
        </select>
    )
}