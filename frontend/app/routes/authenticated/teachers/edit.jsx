import TeacherForm from '../../../layout/forms/TeacherForm';
import ErrorIcon from '../../../layout/icons/ErrorIcon';
import apiConfig from '../../../api.config';
import settings from '../../../settings';
import { TEACHER_MODE, validateTeacher } from '../../../utilities/validation';
import { Link, redirect } from 'react-router';
import TeacherDelete from '../../../layout/forms/TeacherDelete';

export function meta() {
  return [
    { title: 'Edit Teacher - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export async function loader({ request, params }) {
  const getSubjectsRequest = await fetch(`${apiConfig.baseUrl}/subjects?schoolId=${settings.schoolId}`);
  const subjects = await getSubjectsRequest.json();

  const getTeacherRequest = await fetch(`${apiConfig.baseUrl}/teachers/${params.id}`);
  const teacher = await getTeacherRequest.json();

  const getSchoolsRequest = await fetch(`${apiConfig.baseUrl}/schools`);
  const schools = await getSchoolsRequest.json();

  return { subjects, teacher, schools: schools.content };
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
    TEACHER_MODE.EDIT,
  );

  if (Object.keys(errors).length > 0) {
    return { errors };
  }

  const response = await fetch(`${apiConfig.baseUrl}/teachers/${params.id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
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

  return {
    success: 'Teacher updated successfully',
  }
}

export default function Edit({ loaderData, actionData }) {
  const { errors, success } = actionData || {};
  const { teacher, subjects, schools } = loaderData || {};

  return (
    <div className="bg-base-100 text-base-content py-10 lg:py-20">
      <div className="card mx-auto bg-base-100 w-full max-w-sm shrink-0 shadow-2xl">
        <div className="card-body">
          <div className="flex justify-between items-center">
            <h2 className="text-3xl font-bold">Edit Teacher 1</h2>
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
          <TeacherForm teacher={teacher} errors={errors} subjects={subjects} schools={schools}/>
        </div>
      </div>
      <TeacherDelete teacherId={teacher.id} onSuccess={() => redirect('/teachers')}/>
    </div>
  );
}
