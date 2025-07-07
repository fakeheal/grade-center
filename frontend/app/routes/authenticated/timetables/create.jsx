import { Form } from 'react-router';
import apiConfig from '../../../api.config';
import settings from '../../../settings';
import ClassSelect from '../../../layout/forms/components/ClassSelect';
import Week from '../../../layout/forms/components/timetable/Week';
import { parseFormDataIntoTimetable } from '../../../utilities/timetable';
import ErrorIcon from '../../../layout/icons/ErrorIcon';
import React from 'react';
import { extractJwtToken } from '../../../utilities/user';


export function meta() {
  return [
    { title: 'Timetables - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export async function loader({ request }) {
  const cookie = request.headers.get('cookie');
  const token = extractJwtToken(cookie);
  const userRequest = await fetch(`${apiConfig.baseUrl}/users/me`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });
  const userResponse = await userRequest.json();

  const classesRequest = await fetch(`${apiConfig.baseUrl}/classes?schoolId=${userResponse.school.id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });
  const classesResponse = await classesRequest.json();

  const subjectsWithTeachersRequest = await fetch(`${apiConfig.baseUrl}/subjects/teachers/${userResponse.school.id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });

  const subjectsWithTeachersResponse = await subjectsWithTeachersRequest.json();
  return {
    classes: classesResponse.content,
    subjectsWithTeachers: subjectsWithTeachersResponse,
  }
}

export async function clientAction({ request }) {
  const formData = await request.formData();
  const timetable = parseFormDataIntoTimetable(Object.fromEntries(formData));

  const response = await fetch(`${apiConfig.baseUrl}/timetables`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      classId: formData.get('classId'),
      schoolYearId: settings.schoolYearId,
      subjects: timetable,
    }),
  });

  const data = await response.json();

  if (!response.ok) {
    return { errors: { general: data.message } };
  }
  return { success: true };
}

export default function CreateTimetable({ loaderData, actionData }) {
  const { subjectsWithTeachers, classes } = loaderData;
  const { errors, success } = actionData || {};

  const [classId, setClassId] = React.useState(null);
  const [timetable, setTimetable] = React.useState({});


  React.useEffect(() => {
    if (!classId) {
      return;
    }

    setTimetable({});

    fetch(`${apiConfig.baseUrl}/timetables/${settings.schoolYearId}/${classId}`)
      .then(response => {
        if (response.ok && response.status !== 204) {
          return response.json();
        }
      })
      .then(data => {
        if (data) {
          setTimetable(data.subjects);
        }
      });

  }, [classId]);

  return (
    <div className="bg-base-100 text-base-content py-10 lg:py-20">
      <div className="container mx-auto px-4">
        <div className="card mx-auto bg-base-100 w-full shrink-0 shadow-2xl">
          <div className="card-body">
            <div className="flex justify-between items-center">
              <h2 className="text-3xl font-bold">Manage Timetable</h2>
            </div>
            {errors?.general && (
              <div role="alert" className="alert alert-error">
                <ErrorIcon/>
                <span>{errors.general}</span>
              </div>
            )}
            <Form method="post">
              <div className="mb-2">
                <label className="fieldset-label" htmlFor="subjects">Class</label>
                <ClassSelect classes={classes} onClassChanged={setClassId}/>
              </div>
              {classId && (
                <div>
                  <div className="overflow-x">
                    <Week subjectsWithTeachers={subjectsWithTeachers} existing={timetable}/>
                  </div>
                  <button className="btn btn-primary mt-4 w-full" type="submit">
                    Save Timetable
                  </button>
                  {success && (
                    <div role="alert"
                         className="alert alert-success mt-2">
                      <span>Timetable successfully updated!</span>
                    </div>
                  )}
                </div>
              )}
            </Form>
          </div>
        </div>
      </div>
    </div>
  );
}