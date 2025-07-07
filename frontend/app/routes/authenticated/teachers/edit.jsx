import TeacherForm from '../../../layout/forms/TeacherForm';
import ErrorIcon from '../../../layout/icons/ErrorIcon';
import apiConfig from '../../../api.config';
import { TEACHER_MODE, validateTeacher } from '../../../utilities/validation';
import { Link, redirect, useNavigate, useOutletContext } from 'react-router';
import TeacherDelete from '../../../layout/forms/TeacherDelete';
import { extractJwtToken, USER_ROLES } from '../../../utilities/user';
import React from 'react';

export function meta() {
  return [
    { title: 'Edit Teacher - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export async function loader({ request, params }) {
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
  const getSubjectsRequest = await fetch(`${apiConfig.baseUrl}/subjects?schoolId=${userResponse.school.id}`, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });
  const subjects = await getSubjectsRequest.json();

  const getTeacherRequest = await fetch(`${apiConfig.baseUrl}/teachers/${params.id}`, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });
  const teacher = await getTeacherRequest.json();
  return { subjects, teacher };
}

export async function action({ request, params }) {

  const formData = await request.formData();
  const firstName = formData.get('firstName');
  const lastName = formData.get('lastName');
  const email = formData.get('email');
  const password = formData.get('password');
  const repeatPassword = formData.get('repeatPassword');
  const subjectIds = formData.getAll('subjectIds');
  const schoolId = formData.get('schoolId');
  const token = formData.get('token');

  const errors = validateTeacher(
    firstName,
    lastName,
    email,
    password,
    repeatPassword,
    subjectIds,
    TEACHER_MODE.EDIT,
  );

  if (Object.keys(errors).length > 0) {
    return { errors };
  }

  const response = await fetch(`${apiConfig.baseUrl}/teachers/${params.id}`, {
    method: 'PUT',
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

  return {
    success: 'Teacher updated successfully',
  }
}

export default function Edit({ loaderData, actionData }) {
  const { errors, success } = actionData || {};
  const { teacher, subjects } = loaderData || {};
  const { user, token } = useOutletContext();
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
            <h2 className="text-3xl font-bold">Edit Teacher</h2>
            <Link to={'/teachers'} className="link">&laquo; Teachers</Link>
          </div>
          {errors?.general && (
            <div role="alert" className="alert alert-error">
              <ErrorIcon/>
              <span>{errors.general}</span>
            </div>
          )}
          {success && (
            <div role="alert" className="alert alert-success">
              <span>{success}</span>
            </div>
          )}
          <TeacherForm teacher={teacher} errors={errors} subjects={subjects} token={token} schoolId={user.school.id}/>
        </div>
      </div>
      <TeacherDelete teacherId={teacher.userId} onSuccess={() => redirect('/teachers')}/>
    </div>
  );
}
