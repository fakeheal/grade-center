import React from "react";

export default function SchoolSelect({schools,schoolId, errors }) {
    return (
        <select className="select w-full" name="schoolId" >
        {schools.map(school => (
            <option key={school.id} value={school.id} selected={school.id === schoolId} >
                {school.name}
            </option>
        ))}
        </select>
    )
}