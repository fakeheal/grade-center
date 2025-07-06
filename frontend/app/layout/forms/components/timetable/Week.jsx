import React from 'react';
import { generateTimes, parseTime, WEEKDAYS } from '../../../../utilities/timetable';
import TimetableSubject from './TimetableSubject';

const times = generateTimes();

export default function Week({ subjectsWithTeachers, existing }) {
  return (
    <table className="table">
      <thead>
      <tr>
        {
          Object.keys(WEEKDAYS).map((day, index) => (
            <th key={index} className="text-center" colSpan="2">
              {day}
            </th>
          ))
        }
      </tr>
      </thead>
      <tbody>
      {times.map((time, index) => (
        <tr key={index}>
          {
            Object.keys(WEEKDAYS).map((day, dayIndex) => {
              let selected = '';
              if (Array.isArray(existing)) {
                const existingSubjectForDayAndTime = existing
                  .find(subject => subject.dayOfWeek === WEEKDAYS[day] && subject.startTime === parseTime(time));
                if (existingSubjectForDayAndTime) {
                  selected = `${existingSubjectForDayAndTime.subject.id}-${existingSubjectForDayAndTime.teacher.id}`;
                }
              }

              return (
                <React.Fragment key={`col-${index}-${dayIndex}`}>
                  <td key={`time-${index}-${dayIndex}`} className="text-right">
                    {time}
                  </td>
                  <td className="p-2" key={`subject-${index}-${dayIndex}`}>
                    <TimetableSubject
                      subjectsWithTeachers={subjectsWithTeachers}
                      selected={selected}
                      day={day}
                      time={time}
                    />
                  </td>
                </React.Fragment>
              );
            })
          }
        </tr>
      ))}
      </tbody>
    </table>
  );
}
