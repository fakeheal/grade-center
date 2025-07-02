import apiConfig from "../../../api.config.js";
import settings from "../../../settings.js";
import ClassForm from "../../../layout/forms/ClassForm.jsx";
import {useLoaderData} from "react-router";
import {TEACHER_MODE, validateClass, validateTeacher} from "../../../utilities/validation.js";
import SubjectForm from "../../../layout/forms/SubjectForm.jsx";

export function meta() {
    return [
        { title: 'Edit CLass - Grade Center' },
        { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
    ];
}
export async function loader({ request, params }) {
    const getSchoolsRequest = await fetch(`${apiConfig.baseUrl}/schools`);
    const schools = await getSchoolsRequest.json();

    const getClsRequest = await fetch(`${apiConfig.baseUrl}/subjects/${params.id}`);
    const cls = await getClsRequest.json();
    return { schools, cls };
}

export async function action({ request, params }) {
    const formData = await request.formData();
    const name = formData.get('name');
    const schoolId = formData.get('schoolId');
    const id = params.id;

    const errors = name !== ''

    if (Object.keys(errors).length > 0) {
        return {errors};
    }

    const response = await fetch(`${apiConfig.baseUrl}/subjects/${params.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name,
            schoolId,
        }),
    });

    const data = await response.json();

    if (!response.ok) {
        return {errors: {general: data.message}};
    }

    return {
        success: 'Subject updated successfully',
    }
}
export default function Edit({ loaderData, actionData }) {
    const { errors, success } = actionData || {};
    const { schools, cls } = loaderData || {};
    return (
        <SubjectForm subject={cls} schools={schools.content} errors={errors}></SubjectForm>
    );
}
