import { Form } from 'react-router';
import React from 'react';
import UserFields from "./components/UserFields.jsx";
import SchoolSelect from "./components/SchoolSelect.jsx";
import SubjectSelect from "./components/SubjectSelect.jsx";

export default function TeacherForm({ teacher, subjects, schools, errors }) {
  const [firstName, setFirstName] = React.useState(teacher?.firstName || '');
  const [lastName, setLastName] = React.useState(teacher?.lastName || '');
  const [email, setEmail] = React.useState(teacher?.email || '');
  const [password, setPassword] = React.useState('');
  const [repeatPassword, setRepeatPassword] = React.useState('');
  const [subjectIds, setSubjectIds] = React.useState(teacher?.subjects?.map(subject => subject.id) || []);
  const [schoolId, setSchoolId] = React.useState(teacher?.schoolId || '');


  return (
    <Form method="post">
      <fieldset className="fieldset">
        <UserFields
          values={{ firstName, lastName, email, password, repeatPassword }}
          errors={errors}
          setFuncs={{ setFirstName, setLastName, setEmail, setPassword, setRepeatPassword }}
        />

        <div className="mb-2">
          <label className="fieldset-label" htmlFor="schoolId">School</label>
          <SchoolSelect schools={schools} schoolId={schoolId} errors={errors}/>
          {errors?.schoolId &&
            <p className="text-error text-xs mt-1">{errors.schoolId}</p>}
        </div>
        <div className="mb-2">
          <label className="fieldset-label" htmlFor="subjects">Subjects</label>
          <SubjectSelect
            subjects={subjects}
            errors={errors}
            selectedIds={subjectIds}
            onSelectionChange={setSubjectIds}
            singleSelect={false}
          />
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
