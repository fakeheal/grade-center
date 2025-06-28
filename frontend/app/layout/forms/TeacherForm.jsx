import { Form } from 'react-router';
import React from 'react';
import UserFields from "./components/UserFields.jsx";
import SubjectSelect from "./components/SubjectSelect.jsx";

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
        <UserFields errors={errors} setFuncs={{setFirstName,setLastName,setEmail,setPassword,setRepeatPassword}} firstName={firstName} lastName={lastName} email={email} password={password} repeatPassword={repeatPassword} />
        <div className="mb-2">
          <label className="fieldset-label" htmlFor="subjects">Subjects</label>
          {/*<select name="subjectIds" id="subjects" multiple={true}*/}
          {/*        className={`mt-0.5 select w-full h-24 ${errors?.subjects ? `input-error` : ``}`}>*/}
          {/*  {subjects.map(subject => (*/}
          {/*    <option key={subject.id} value={subject.id} selected={teacher?.subjects?.find(s => s.id === subject.id)}>*/}
          {/*      {subject.name}*/}
          {/*    </option>*/}
          {/*  ))}*/}
          {/*</select>*/}
          <SubjectSelect subjects={subjects} errors={errors} user={teacher}/>
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
