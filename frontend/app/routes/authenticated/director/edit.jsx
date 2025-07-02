import apiConfig from "../../../api.config.js";
import settings from "../../../settings.js";
import ClassForm from "../../../layout/forms/ClassForm.jsx";
import {useLoaderData} from "react-router";
import {FORM_MODE, TEACHER_MODE, validateClass, validateTeacher} from "../../../utilities/validation.js";
import DirectorForm from "../../../layout/forms/DirectorForm.jsx";
import ErrorIcon from "../../../layout/icons/ErrorIcon.jsx";

export function meta() {
    return [
        { title: 'Edit CLass - Grade Center' },
        { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
    ];
}
export async function loader({ request, params }) {
    const getSchoolsRequest = await fetch(`${apiConfig.baseUrl}/schools`);
    const schools = await getSchoolsRequest.json();

    const getDirectorRequest = await fetch(`${apiConfig.baseUrl}/directors/${params.id}`);
    const director = await getDirectorRequest.json();
    return { schools, director };
}

export async function action({ request, params }){
    const formData = await request.formData();
    const firstName = formData.get('firstName');
    const lastName = formData.get('lastName');
    const email = formData.get('email');
    const password = formData.get('password');
    const repeatPassword = formData.get('repeatPassword');
    const schoolId = formData.get('schoolId');
    const id = params.id;

    const errors = validateTeacher(
        firstName,
        lastName,
        email,
        password,
        repeatPassword,
        FORM_MODE.EDIT,
    );

    if (Object.keys(errors).length > 0) {
        return {errors};
    }
    console.log(firstName)
    const response = await fetch(`${apiConfig.baseUrl}/directors/${params.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            firstName,
            lastName,
            email,
            password,
            schoolId,
        }),
    });

    const data = await response.json();

    if (!response.ok) {
        return {errors: {general: data.message}};
    }

    return {
        success: 'Director updated successfully',
    }
}
export default function Edit({ loaderData, actionData }) {
    const { errors, success } = actionData || {};
    const { schools, director } = loaderData || {};
    return (
        <div>
            <DirectorForm director={director} schools={schools.content} errors={errors}></DirectorForm>
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
        </div>

    );
}