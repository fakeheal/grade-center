import React from 'react';

export default function TimetableSubjectSelect({
                                          timetableSubjects = [],
                                          selectedIds = [],
                                          onSelectionChange,
                                          errors = {},
                                          singleSelect = false,
                                      }) {
    return (
        <select name={singleSelect ? "timetableSubjectId" : "timetableSubjectIds"} id="timetableSubject" className="mt-0.5 select w-full">
            <option value="">Select Timetable Subject</option>
            {timetableSubjects.map(ts => (
                <option key={ts.id} value={ts.id}>
                    {`${ts.subject.name} (${ts.teacher.user.firstName} ${ts.teacher.user.lastName}) - ${ts.dayOfWeek} ${ts.hour}:00`}
                </option>
            ))}
        </select>
    );
}
