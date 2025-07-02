import { Form } from 'react-router';
import React from 'react';
import SchoolSelect from "./components/SchoolSelect.jsx";
import UserFields from "./components/UserFields.jsx";

export function meta() {
    return [
        { title: 'Edit CLass - Grade Center' },
        { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
    ];
}

export default function DirectorForm({ director, schools, errors }) {
    const [firstName, setFirstName] = React.useState(director?.firstName || '');
    const [lastName, setLastName] = React.useState(director?.lastName || '');
    const [email, setEmail] = React.useState(director?.email || '');
    const [password, setPassword] = React.useState('');
    const [repeatPassword, setRepeatPassword] = React.useState('');
    const [schoolId, setSchoolId] = React.useState(director.schoolId);

    return (
        <Form method="post">
            <div className="bg-base-100 text-base-content py-20 lg:py-60">
                <div className="card mx-auto bg-base-100 w-full max-w-sm shrink-0 shadow-2xl">
                    <div className="card-body">
                        <h2 className="text-3xl font-bold">Edit Director</h2>
                        <fieldset className="fieldset">
                            <UserFields firstName={firstName} lastName={lastName} email={email} password={password}
                                        repeatPassword={repeatPassword}
                                        setFuncs={{setFirstName,setLastName,setEmail,setPassword,setRepeatPassword}} />
                            <label className="fieldset-label">School</label>
                            <SchoolSelect schools={schools} schoolId={schoolId} errors={errors} />
                            <button className="btn btn-neutral mt-4" type="submit">Edit</button>
                        </fieldset>
                    </div>
                </div>
            </div>
        </Form>

    );
}