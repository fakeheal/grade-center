export const WEEKDAYS = {
  MONDAY: 1,
  TUESDAY: 2,
  WEDNESDAY: 3,
  THURSDAY: 4,
  FRIDAY: 5,
};
export const SHORT_BREAK_DURATION = 10;
export const LONG_BREAK_DURATION = 20;
export const LONG_BREAK_AFTER = 3;

export function generateTimes() {
  const earliestTime = 7.5 * 60;
  const latestTime = 18 * 60;
  const times = [];

  let i = 1;

  for (let time = earliestTime; time <= latestTime; time += 45) {
    const hours = Math.floor(time / 60);
    const minutes = time % 60;
    const formattedTime = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
    times.push(formattedTime);
    time += (i === LONG_BREAK_AFTER ? LONG_BREAK_DURATION : SHORT_BREAK_DURATION);
    i++;
  }

  return times;
}

export function parseFormDataIntoTimetable(formData) {
  const subjects = [];

  Object.entries(formData).forEach(([key, value]) => {
    const match = key.match(/^subjectWithTeacher\[(.*?)\]\[(.*?)\]$/);
    if (match) {
      const day = match[1];
      const time = match[2];

      const subjectWithTeacher = value.split('-');

      if (subjectWithTeacher.length !== 2) {
        return;
      }

      subjects.push({
        dayOfWeek: WEEKDAYS[day.toUpperCase()],
        startTime: parseTime(time),
        teacherId: parseInt(subjectWithTeacher[0]),
        subjectId: parseInt(subjectWithTeacher[1]),
      });
    }
  });

  return subjects;
}


export function parseTime(time) {
  const [hours, minutes] = time.split(':').map(Number);
  return (hours * 60 + minutes) * 60;
}


function formatTime(time) {
  const totalMinutes = Math.floor(time / 60);
  const hours = Math.floor(totalMinutes / 60);
  const minutes = totalMinutes % 60;
  return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
}


export function parseTimetableIntoFormdata(subjects) {
  const formData = {};

  subjects.forEach(subject => {
    const day = Object.keys(WEEKDAYS).find(key => WEEKDAYS[key] === subject.dayOfWeek);
    const time = formatTime(subject.startTime);

    formData[`subjectWithTeacher[${day}][${time}]`] = `${subject.teacherId}-${subject.subjectId}`;
  });

  return formData;
}
