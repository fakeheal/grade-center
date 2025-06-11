import { Form } from 'react-router';
import React from 'react';

export default function TeacherForm({ teacher, subjects, errors }) {
  const [firstName, setFirstName] = React.useState(teacher?.firstName || '');
  const [lastName, setLastName] = React.useState(teacher?.lastName || '');
  const [email, setEmail] = React.useState(teacher?.email || '');
  const [password, setPassword] = React.useState('');
  const [repeatPassword, setRepeatPassword] = React.useState('');
  const [subjectIds, setSubjectIds] = React.useState(teacher?.subjects?.map(subject => subject.id) || []);


  return (
    <Form method="post">
      <fieldset className="fieldset">
        <div className="mb-2">
          <label className="fieldset-label" htmlFor="firstName">First Name</label>
          <input type="text" className={`input w-full ${errors?.firstName ? `input-error` : ``}`}
                 placeholder="Enter first name..." name="firstName"
                 value={firstName}
                 onChange={(e) => setFirstName(e.target.value)}
                 id="firstName"/>
          {errors?.firstName &&
            <p className="text-error text-xs mt-1">{errors.firstName}</p>}
        </div>
        <div className="mb-2">
          <label className="fieldset-label" htmlFor="lastName">Last Name</label>
          <input type="text" className={`input w-full ${errors?.lastName ? `input-error` : ``}`}
                 placeholder="Enter last name..." name="lastName"
                 onChange={(e) => setLastName(e.target.value)}
                 value={lastName}
                 id="lastName"/>
          {errors?.lastName &&
            <p className="text-error text-xs mt-1">{errors.lastName}</p>}
        </div>
        <div className="mb-2">
          <label className="fieldset-label" htmlFor="email">Email</label>
          <input type="text" className={`input w-full ${errors?.email ? `input-error` : ``}`}
                 placeholder="Enter email..." name="email"
                 value={email}
                 onChange={(e) => setEmail(e.target.value)}
                 id="email"/>
          {errors?.email &&
            <p className="text-error text-xs mt-1">{errors.email}</p>}
        </div>
        <div className="mb-2">
          <label className="fieldset-label" htmlFor="password">Password</label>
          <input type="password" className={`input w-full ${errors?.password ? `input-error` : ``}`}
                 placeholder="Enter password..." name="password"
                 value={password}
                 onChange={(e) => setPassword(e.target.value)}
                 id="password"/>
          {errors?.password &&
            <p className="text-error text-xs mt-1">{errors.password}</p>}
        </div>
        <div className="mb-2">
          <label className="fieldset-label" htmlFor="repeatPassword">Password (repeat)</label>
          <input type="password" className={`input w-full ${errors?.repeatPassword ? `input-error` : ``}`}
                 placeholder="Repeat password..."
                 name="repeatPassword"
                 value={repeatPassword}
                 onChange={(e) => setRepeatPassword(e.target.value)}
                 id="repeatPassword"/>
          {errors?.repeatPassword &&
            <p className="text-error text-xs mt-1">{errors.repeatPassword}</p>}
        </div>
        <div className="mb-2">
          <label className="fieldset-label" htmlFor="subjects">Subjects</label>
          <select name="subjectIds" id="subjects" multiple={true}
                  className={`mt-0.5 select w-full h-24 ${errors?.subjects ? `input-error` : ``}`}>
            {subjects.map(subject => (
              <option key={subject.id} value={subject.id} selected={teacher?.subjects?.find(s => s.id === subject.id)}>
                {subject.name}
              </option>
            ))}
          </select>
          {errors?.subjects &&
            <p className="text-error text-xs mt-1">{errors.subjects}</p>}
        </div>
        <button className="btn btn-neutral mt-4" type="submit">
          {teacher ? 'Update' : 'Create'}
        </button>
      </fieldset>
    </Form>
  )
}
