import { Form, redirect } from 'react-router';
import apiConfig from '../api.config';
import ErrorIcon from '../layout/icons/ErrorIcon';

export function meta() {
  return [
    { title: 'Login - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export async function clientAction({ request }) {
  const formData = await request.formData();

  const email = formData.get('email');
  const password = formData.get('password');

  const loginResponse = await fetch(`${apiConfig.baseUrl}/users/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      email,
      password
    }),
  });

  if (loginResponse.ok) {
    const token = (await loginResponse.json()).token;
    document.cookie = `jwt=${token}; Path=/; SameSite=Strict;`;
    return redirect('/dashboard');
  }

  return {
    errors: {
      general: 'Login failed. Please check your email and password.',
    },
  }
}

export default function Login({ actionData }) {
  const { errors } = actionData || {};
  return (
    <div className="bg-base-100 text-base-content">
      <div className="hero bg-base-200 py-20 lg:py-60">
        <div className="hero-content flex-col lg:flex-row-reverse">
          <div className="text-center lg:text-left">
            <h1 className="text-5xl font-bold">Login now!</h1>
            <p className="py-6">
              Provident cupiditate voluptatem et in. Quaerat fugiat ut assumenda excepturi exercitationem
              quasi. In deleniti eaque aut repudiandae et a id nisi.
            </p>
          </div>
          <div className="card bg-base-100 w-full max-w-sm shrink-0 shadow-2xl lg:mr-20">
            <div className="card-body">
              {errors?.general && (
                <div role="alert" className="alert alert-error">
                  <ErrorIcon/>
                  <span>{errors.general}</span>
                </div>
              )}
              <Form method="post">
                <fieldset className="fieldset">
                  <label className="fieldset-label">Email</label>
                  <input type="email" name="email" className="input w-full" placeholder="Email" required/>
                  <label className="fieldset-label">Password</label>
                  <input type="password" name="password" className="input w-full" placeholder="Password" required/>
                  <div><a className="link link-hover">Forgot password?</a></div>
                  <button type="submit" className="btn btn-neutral mt-4">Login</button>
                </fieldset>
              </Form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
