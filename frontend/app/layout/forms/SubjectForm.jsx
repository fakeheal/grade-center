import React from "react";
import {Form} from "react-router";
import SchoolSelect from "./components/SchoolSelect.jsx";

export function meta() {
    return [
        { title: 'Edit CLass - Grade Center' },
        { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
    ];
}

export default function SubjectForm({ subject, schools, errors }) {
    const [name, setName] = React.useState(subject.name);
    const [schoolId, setSchoolId] = React.useState(subject.schoolId);

    return (
        <Form method="post">
            <div className="bg-base-100 text-base-content py-20 lg:py-60">
                <div className="card mx-auto bg-base-100 w-full max-w-sm shrink-0 shadow-2xl">
                    <div className="card-body">
                        <h2 className="text-3xl font-bold">Edit Class</h2>
                        <fieldset className="fieldset">
                            <label className="fieldset-label">Name</label>
                            <input type="text" className="input w-full" name="name" placeholder="Name" value={name}
                                   onChange={e => setName(e.target.value)}/>
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