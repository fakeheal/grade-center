import apiConfig from '../../../api.config';
import settings from '../../../settings';
import { Link, redirect } from 'react-router';
import { TEACHER_MODE, validateTeacher } from '../../../utilities/validation';
import TeacherForm from '../../../layout/forms/TeacherForm';
import ErrorIcon from '../../../layout/icons/ErrorIcon';
import { getJwt } from '../../../utilities/auth';

export function meta() {
  return [
    { title: 'Create Teacher - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export async function loader({ request }) {
  const token = getJwt(request.headers.get('cookie') ?? '');
  const subjectsRes = await fetch(`${apiConfig.baseUrl}/subjects?schoolId=${settings.schoolId}`, { headers: { Authorization: `Bearer ${token}` } });
  const subjects = await subjectsRes.json();

  const schoolsRes = await fetch(`${apiConfig.baseUrl}/schools`, { headers: { Authorization: `Bearer ${token}` } });
  const schools = await schoolsRes.json();

  return { subjects, schools: schools.content };
}

export async function action({ request }) {
  const token = getJwt(request.headers.get('cookie') ?? '');
  const formData = await request.formData();
  const firstName = formData.get('firstName');
  const lastName = formData.get('lastName');
  const email = formData.get('email');
  const password = formData.get('password');
  const repeatPassword = formData.get('repeatPassword');
  const subjectIds = formData.getAll('subjectIds');
  const schoolId = formData.get('schoolId');
  const grade = formData.get('grade');

  const errors = validateTeacher(
    firstName,
    lastName,
    email,
    password,
    repeatPassword,
    subjectIds,
    schoolId,
    grade,
    TEACHER_MODE.CREATE
  );

  if (Object.keys(errors).length > 0) {
    return { errors };
  }

  const response = await fetch(`${apiConfig.baseUrl}/teachers`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({
      firstName,
      lastName,
      email,
      password,
      subjects: subjectIds.map(id => ({ id })),
      schoolId: Number(schoolId),
      grade: Number(grade),
    }),
  });

  const data = await response.json();

  if (!response.ok) {
    return { errors: { general: data.message } };
  }

  return redirect('/teachers');
}

export default function Create({ loaderData, actionData }) {
  const { errors } = actionData || {};
  const { subjects, schools } = loaderData || {};
  return (
    <div className="bg-base-100 text-base-content py-10 lg:py-20">
      <div className="card mx-auto bg-base-100 w-full max-w-sm shrink-0 shadow-2xl">
        <div className="card-body">
          <div className="flex justify-between items-center">
            <h2 className="text-3xl font-bold">New Teacher</h2>
            <Link to={'/teachers'} className="link">&laquo; Teachers</Link>
          </div>
          {errors?.general && (
            <div role="alert" className="alert alert-error">
              <ErrorIcon/>
              <span>{errors.general}</span>
            </div>
          )}
          <TeacherForm teacher={null} errors={errors} subjects={subjects} schools={schools}/>
        </div>
      </div>
    </div>
  );
}
