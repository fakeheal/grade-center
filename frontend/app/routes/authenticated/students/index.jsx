import {
    Form,
    Link,
    redirect,
    useLoaderData,
    useNavigation,
    useSearchParams,
} from 'react-router';
import apiConfig from '../../../api.config';
import { Trash2, Edit } from 'lucide-react';

export function meta() {
  return [
    { title: 'Students - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export async function loader({ request }) {
    const url = new URL(request.url);
    const page = url.searchParams.get('page') || 0;

    const res = await fetch(
        `${apiConfig.baseUrl}/students?page=${page}&size=20`
    );
    if (!res.ok) throw new Response('Failed to load students', { status: 500 });

    return await res.json();
}

export async function action({ request }) {
    const formData = await request.formData();
    const id = formData.get('deleteId');

    const res = await fetch(`${apiConfig.baseUrl}/students/${id}`, {
        method: 'DELETE',
    });

    if (!res.ok)
        return { error: 'Delete failed. Maybe student has grades linked.' };

    return redirect('/students');
}

export default function StudentsIndex() {
    const { content: students = [], totalPages = 1 } = useLoaderData();
    const navigation = useNavigation();
    const [searchParams] = useSearchParams();
    const page = Number(searchParams.get('page') || 0);

    return (
        <div className="p-6">
            <div className="flex justify-between items-center mb-4">
                <h1 className="text-2xl font-bold">Students</h1>
                <Link to="create" className="btn btn-primary">
                    + Add Student
                </Link>
            </div>

            <div className="overflow-x-auto shadow rounded">
                <table className="table w-full">
                    <thead>
                        <tr className="bg-base-200">
                            <th>#</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th className="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {students.map((s, idx) => (
                            <tr key={s.id} className="hover">
                                <td>{idx + 1 + page * 20}</td>
                                <td>{s.firstName}</td>
                                <td>{s.lastName}</td>
                                <td>{s.email}</td>
                                <td className="flex gap-2 justify-center">
                                    <Link
                                        to={`${s.id}/edit`}
                                        className="btn btn-sm btn-outline"
                                        title="Edit"
                                    >
                                        <Edit size={16} />
                                    </Link>
                                    <Form method="post" className="inline">
                                        <input type="hidden" name="deleteId" value={s.id} />
                                        <button
                                            type="submit"
                                            className="btn btn-sm btn-error"
                                            title="Delete"
                                            disabled={navigation.state === 'submitting'}
                                            onClick={(e) => {
                                                if (
                                                    !window.confirm(
                                                        `Delete ${s.firstName} ${s.lastName}?`
                                                    )
                                                ) {
                                                    e.preventDefault();
                                                }
                                            }}
                                        >
                                            <Trash2 size={16} />
                                        </button>
                                    </Form>
                                </td>
                            </tr>
                        ))}
                        {students.length === 0 && (
                            <tr>
                                <td colSpan={5} className="text-center py-8">
                                    No students found.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {totalPages > 1 && (
                <div className="join mt-4 flex justify-center">
                    {Array.from({ length: totalPages }).map((_, i) => (
                        <Link
                            key={i}
                            to={`/students?page=${i}`}
                            className={`join-item btn btn-xs ${
                                i === page ? 'btn-active' : ''
                            }`}
                        >
                            {i + 1}
                        </Link>
                    ))}
                </div>
            )}
        </div>
    );
}
