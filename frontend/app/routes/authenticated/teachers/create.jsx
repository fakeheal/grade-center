import apiConfig from '../../../api.config';
import { Link, redirect, useNavigate, useOutletContext } from 'react-router';
import { TEACHER_MODE, validateTeacher } from '../../../utilities/validation';
import TeacherForm from '../../../layout/forms/TeacherForm';
import ErrorIcon from '../../../layout/icons/ErrorIcon';
import { extractJwtToken, USER_ROLES } from '../../../utilities/user';
import React from 'react';

export function meta() {
  return [
    { title: 'Create Teacher - Grade Center' },
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
  const response = await fetch(`${apiConfig.baseUrl}/subjects?schoolId=${userResponse.school.id}`, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });
  return await response.json();
}

export async function clientAction({ request }) {
  const formData = await request.formData();
  const firstName = formData.get('firstName');
  const lastName = formData.get('lastName');
  const email = formData.get('email');
  const password = formData.get('password');
  const repeatPassword = formData.get('repeatPassword');
  const subjectIds = formData.getAll('subjectIds');
  const token = formData.get('token');
  const schoolId = formData.get('schoolId');

  const errors = validateTeacher(
    firstName,
    lastName,
    email,
    password,
    repeatPassword,
    subjectIds,
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
      schoolId,
    }),
  });

  const data = await response.json();

  if (!response.ok) {
    return { errors: { general: data.message } };
  }

  return redirect('/teachers');
}

export default function Create({ loaderData, actionData }) {
  const { user, token } = useOutletContext();
  const { errors } = actionData || {};
  let navigate = useNavigate();

  React.useEffect(() => {
    if (user.role !== USER_ROLES.ADMINISTRATOR && user.role !== USER_ROLES.DIRECTOR) {
      navigate('/dashboard');
    }
  }, []);

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
          <TeacherForm teacher={null} errors={errors} subjects={loaderData} token={token} schoolId={user.school.id}/>
        </div>
      </div>
    </div>
  );
}
