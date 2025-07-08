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

/* ------------ meta ------------ */
export function meta() {
    return [
        { title: 'Absences - Grade Center' },
        { name: 'description', content: 'Manage student absences.' }
    ];
}

/* ------------ loader (list absences) ------------ */
export async function loader({ request }) {
    const token = getJwt(request.headers.get('cookie'));
    const res = await fetch(`${apiConfig.baseUrl}/absences`, {
        headers: { Authorization: `Bearer ${token}` }
    });
    if (!res.ok) throw new Response('Failed to load absences', { status: 500 });
    return await res.json();
}

/* ------------ action (delete absence) ------------ */
export async function action({ request }) {
    const formData = await request.formData();
    const id = formData.get('deleteId');

    const token = getJwt(request.headers.get('cookie'));
    const res = await fetch(`${apiConfig.baseUrl}/absences/${id}`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${token}` }
    });

    if (!res.ok) throw new Response('Failed to delete absence', { status: 500 });
    return redirect('/absences');
}

/* ------------ component ------------ */
export default function AbsencesIndex() {
    const absences = useLoaderData();
    const navigation = useNavigation();
    const { user } = useOutletContext(); // current JWT user

    return (
        <div className="p-6">
            {/* header */}
            <div className="flex justify-between items-center mb-4">
                <h1 className="text-2xl font-bold">Absences</h1>

                {/* 🔒 “Add Absence” hidden for students */}
                {user?.role !== 'STUDENT' && (
                    <Link to="/absences/create" className="btn btn-primary">
                        + Add Absence
                    </Link>
                )}
            </div>

            {/* table */}
            <div className="overflow-x-auto shadow rounded">
                <table className="table w-full">
                    <thead>
                    <tr className="bg-base-200">
                        <th>Student</th>
                        <th>Class</th>
                        <th>Subject</th>
                        <th>Date</th>
                        <th>Hour</th>
                        <th>Excused</th>
                        <th>Reason</th>
                        <th>Actions</th>
                    </tr>
                    </thead>

                    <tbody>
                    {absences.length === 0 ? (
                        <tr>
                            <td colSpan="8" className="text-center py-8">
                                No absences found.
                            </td>
                        </tr>
                    ) : (
                        absences.map((absence) => (
                            <tr key={absence.id} className="hover">
                                <td>
                                    {absence.student.firstName} {absence.student.lastName}
                                </td>
                                <td>
                                    {absence.classEntity.name} - {absence.classEntity.grade}
                                </td>
                                <td>{absence.subject.name}</td>
                                <td>{absence.date}</td>
                                <td>{absence.hour}</td>
                                <td>{absence.excused ? 'Yes' : 'No'}</td>
                                <td>{absence.reason || '-'}</td>
                                <td>
                                    {/* 🔒 Edit / Delete hidden for students */}
                                    {user?.role !== 'STUDENT' && (
                                        <>
                                            <Link
                                                to={`${absence.id}/edit`}
                                                className="btn btn-xs btn-outline"
                                                title="Edit"
                                            >
                                                <Edit size={14} />
                                            </Link>

                                            <Form method="post" className="inline-block ml-2">
                                                <input
                                                    type="hidden"
                                                    name="deleteId"
                                                    value={absence.id}
                                                />
                                                <button
                                                    type="submit"
                                                    className="btn btn-xs btn-outline btn-error"
                                                    title="Delete"
                                                    disabled={navigation.state === 'submitting'}
                                                    onClick={(e) => {
                                                        if (
                                                            !window.confirm(
                                                                `Delete absence for ${absence.student.firstName} ${absence.student.lastName} on ${absence.date}?`
                                                            )
                                                        ) {
                                                            e.preventDefault();
                                                        }
                                                    }}
                                                >
                                                    <Trash2 size={14} />
                                                </button>
                                            </Form>
                                        </>
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
