import apiConfig from '../../../api.config';
import settings from '../../../settings';
import { Form, redirect } from 'react-router';

export function meta() {
  return [
    { title: 'Create Teacher - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export async function loader() {
  const response = await fetch(`${apiConfig.baseUrl}/subjects?schoolId=${settings.schoolId}`);
  return await response.json();
}

export async function clientAction({ request }) {
  const schoolId = settings.schoolId;

  const formData = await request.formData();

  const firstName = formData.get('firstName');
  const lastName = formData.get('lastName');
  const email = formData.get('email');
  const password = formData.get('password');
  const repeatPassword = formData.get('repeatPassword');
  const subjectIds = formData.getAll('subjectIds');

  const errors = {};

  if (!firstName) {
    errors.firstName = 'First name is required';
  }
  if (!lastName) {
    errors.lastName = 'Last name is required';
  }
  if (!email) {
    errors.email = 'Email is required';
  } else if (!/\S+@\S+\.\S+/.test(email)) {
    errors.email = 'Email is invalid';
  }
  if (!password) {
    errors.password = 'Password is required';
  } else if (password !== repeatPassword) {
    errors.repeatPassword = 'Passwords do not match';
  }
  if (subjectIds.length === 0) {
    errors.subjects = 'At least one subject must be selected';
  }
  if (Object.keys(errors).length > 0) {
    return { errors };
  }

  const response = await fetch(`${apiConfig.baseUrl}/teachers`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
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

export default function Edit({ loaderData, actionData }) {
  const { errors } = actionData || {};
  return (
    <div className="bg-base-100 text-base-content py-20 lg:py-60">
      <div className="card mx-auto bg-base-100 w-full max-w-sm shrink-0 shadow-2xl">
        <div className="card-body">
          <h2 className="text-3xl font-bold">New Teacher</h2>
          {errors?.general && (
            <div role="alert" className="alert alert-error">
              <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 shrink-0 stroke-current" fill="none"
                   viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                      d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
              <span>{errors.general}</span>
            </div>
          )}
          <Form method="post">
            <fieldset className="fieldset">
              <div className="mb-2">
                <label className="fieldset-label" htmlFor="firstName">First Name</label>
                <input type="text" className={`input w-full ${errors?.firstName ? `input-error` : ``}`}
                       placeholder="Enter first name..." name="firstName"
                       id="firstName"/>
                {errors?.firstName &&
                  <p className="text-error text-xs mt-1">{errors.firstName}</p>}
              </div>
              <div className="mb-2">
                <label className="fieldset-label" htmlFor="lastName">Last Name</label>
                <input type="text" className={`input w-full ${errors?.lastName ? `input-error` : ``}`}
                       placeholder="Enter last name..." name="lastName"
                       id="lastName"/>
                {errors?.lastName &&
                  <p className="text-error text-xs mt-1">{errors.lastName}</p>}
              </div>
              <div className="mb-2">
                <label className="fieldset-label" htmlFor="email">Email</label>
                <input type="text" className={`input w-full ${errors?.email ? `input-error` : ``}`}
                       placeholder="Enter email..." name="email"
                       id="email"/>
                {errors?.email &&
                  <p className="text-error text-xs mt-1">{errors.email}</p>}
              </div>
              <div className="mb-2">
                <label className="fieldset-label" htmlFor="password">Password</label>
                <input type="password" className={`input w-full ${errors?.password ? `input-error` : ``}`}
                       placeholder="Enter password..." name="password"
                       id="password"/>
                {errors?.password &&
                  <p className="text-error text-xs mt-1">{errors.password}</p>}
              </div>
              <div className="mb-2">
                <label className="fieldset-label" htmlFor="repeatPassword">Password (repeat)</label>
                <input type="password" className={`input w-full ${errors?.repeatPassword ? `input-error` : ``}`}
                       placeholder="Repeat password..."
                       name="repeatPassword"
                       id="repeatPassword"/>
                {errors?.repeatPassword &&
                  <p className="text-error text-xs mt-1">{errors.repeatPassword}</p>}
              </div>
              <div className="mb-2">
                <label className="fieldset-label" htmlFor="subjects">Subjects</label>
                <select name="subjectIds" id="subjects" multiple={true}
                        className={`mt-0.5 select w-full h-24 ${errors?.subjects ? `input-error` : ``}`}>
                  {loaderData.map(subject => (
                    <option key={subject.id} value={subject.id}>
                      {subject.name}
                    </option>
                  ))}
                </select>
                {errors?.subjects &&
                  <p className="text-error text-xs mt-1">{errors.subjects}</p>}
              </div>
              <button className="btn btn-neutral mt-4" type="submit">Create</button>
            </fieldset>
          </Form>
        </div>
      </div>
    </div>
  );
}
