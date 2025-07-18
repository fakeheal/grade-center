import {
    Link,
    useLoaderData,
    Form,
    redirect,
    useNavigation,
    useOutletContext
} from 'react-router';
import apiConfig from '../../../api.config';
import { Trash2, Edit } from 'lucide-react';
import { getJwt } from '../../../utilities/auth';

/* ------------ action (Delete grade) ------------ */
export async function action({ request }) {
    const formData = await request.formData();
    const id = formData.get('deleteId');

    const token = getJwt(request.headers.get('cookie'));

    const res = await fetch(`${apiConfig.baseUrl}/grades/${id}`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${token}` }
    });

    if (!res.ok) return { error: 'Delete failed.' };

    return redirect('/grades');
}

/* ------------ meta ------------ */
export function meta() {
    return [
        { title: 'Grades - Grade Center' },
        { name: 'description', content: 'Manage student grades.' }
    ];
}

/* ------------ loader (List grades) ------------ */
export async function loader({ request }) {
    const token = getJwt(request.headers.get('cookie'));
    const res = await fetch(`${apiConfig.baseUrl}/grades`, {
        headers: { Authorization: `Bearer ${token}` }
    });
    if (!res.ok) throw new Response('Failed to load grades', { status: 500 });
    return await res.json();
}

/* ------------ component ------------ */
export default function GradesIndex() {
    const grades = useLoaderData();
    const navigation = useNavigation();
    const { user } = useOutletContext();  // current JWT user (has .role)

    return (
        <div className="p-6">
            {/* header row */}
            <div className="flex justify-between items-center mb-4">
                <h1 className="text-2xl font-bold">Grades</h1>

                {/* 🔒 “Add Grade” hidden for students */}
                {user?.role !== 'STUDENT' && (
                    <Link to="/grades/create" className="btn btn-primary">
                        + Add Grade
                    </Link>
                )}
            </div>

            {/* grades table */}
            <div className="overflow-x-auto shadow rounded">
                <table className="table w-full">
                    <thead>
                    <tr className="bg-base-200">
                        <th>Student</th>
                        <th>Subject</th>
                        <th>Grade</th>
                        <th>Date</th>
                        <th>Teacher</th>
                        <th>Actions</th>
                    </tr>
                    </thead>

                    <tbody>
                    {grades.length === 0 ? (
                        <tr>
                            <td colSpan="6" className="text-center py-8">
                                No grades found.
                            </td>
                        </tr>
                    ) : (
                        grades.map((grade) => (
                            <tr key={grade.id} className="hover">
                                <td>
                                    {grade.student.firstName} {grade.student.lastName}
                                </td>
                                <td>{grade.subject.name}</td>
                                <td>{grade.value}</td>
                                <td>{grade.date}</td>
                                <td>
                                    {grade.teacher.firstName} {grade.teacher.lastName}
                                </td>
                                <td>
                                    {/* 🔒 Edit/Delete hidden for students */}
                                    {user?.role !== 'STUDENT' && (
                                        <div className="flex gap-2 justify-start">
                                            <Link
                                                to={`/grades/${grade.id}/edit`}
                                                className="btn btn-sm btn-outline"
                                                title="Edit"
                                            >
                                                <Edit size={16} />
                                            </Link>

                                            <Form method="post" className="inline">
                                                <input
                                                    type="hidden"
                                                    name="deleteId"
                                                    value={grade.id}
                                                />
                                                <button
                                                    type="submit"
                                                    className="btn btn-sm btn-error"
                                                    title="Delete"
                                                    disabled={navigation.state === 'submitting'}
                                                    onClick={(e) => {
                                                        if (
                                                            !window.confirm(
                                                                `Delete grade for ${grade.student.firstName} ${grade.student.lastName} on ${grade.subject.name}?`
                                                            )
                                                        ) {
                                                            e.preventDefault();
                                                        }
                                                    }}
                                                >
                                                    <Trash2 size={16} />
                                                </button>
                                            </Form>
                                        </div>
                                    )}
                                </td>
                            </tr>
                        ))
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
