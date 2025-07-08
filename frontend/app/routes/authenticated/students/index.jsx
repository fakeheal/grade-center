import {
    Form,
    Link,
    redirect,
    useLoaderData,
    useNavigation,
    useSearchParams,
    useOutletContext,
} from 'react-router';
import apiConfig from '../../../api.config';
import { Trash2, Edit, BookOpen } from 'lucide-react';
import { useState } from 'react';
import { getJwt } from '../../../utilities/auth';

/* ──────────────── <SEO meta> ─────────────── */
export function meta() {
    return [
        { title: 'Students - Grade Center' },
        { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
    ];
}

/* ──────────────── Loader ─────────────── */
export async function loader({ request }) {
    const token = getJwt(request.headers.get('cookie'));
    const url   = new URL(request.url);
    const page  = url.searchParams.get('page') || 0;

    const res = await fetch(
        `${apiConfig.baseUrl}/students?page=${page}&size=20`,
        { headers: { Authorization: `Bearer ${token}` } }
    );
    if (!res.ok) throw new Response('Failed to load students', { status: 500 });

    return await res.json();   // Spring-Data Page<StudentResponseDto>
}

/* ──────────────── Action ─────────────── */
export async function action({ request }) {
    const formData = await request.formData();
    const id = formData.get('deleteId');

    const token = getJwt(request.headers.get('cookie'));

    const res = await fetch(`${apiConfig.baseUrl}/students/${id}`, {
        method : 'DELETE',
        headers: { Authorization: `Bearer ${token}` },
    });

    if (!res.ok)
        return { error: 'Delete failed. Maybe student has grades linked.' };

    return redirect('/students');
}

/* ──────────────── Component ─────────────── */
export default function StudentsIndex() {
    const { content: students = [], totalPages = 1 } = useLoaderData();
    const navigation = useNavigation();
    const [searchParams] = useSearchParams();
    const page = Number(searchParams.get('page') || 0);

    /* token is available from layout _or_ from cookie as fallback */
    const { token: ctxToken } = useOutletContext() ?? {};
    const token = ctxToken || getJwt(document?.cookie);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedStudentGrades, setSelectedStudentGrades] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    /* View-grades modal ---------------------------------------------------- */
    const handleViewGrades = async (studentId) => {
        setIsModalOpen(true);
        setIsLoading(true);
        try {
            const res = await fetch(
                `${apiConfig.baseUrl}/grades?studentId=${studentId}`,
                { headers: { Authorization: `Bearer ${token}` } }
            );
            if (!res.ok) {
                throw new Error('Failed to fetch grades');
            }
            const grades = await res.json();
            setSelectedStudentGrades(grades);
        } catch (error) {
            console.error(error);
            // TODO: display an error toast / message
        } finally {
            setIsLoading(false);
        }
    };

    /* Render --------------------------------------------------------------- */
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
                                {/* View grades */}
                                <button
                                    onClick={() => handleViewGrades(s.id)}
                                    className="btn btn-sm btn-outline"
                                    title="View Grades"
                                >
                                    <BookOpen size={16} />
                                </button>

                                {/* Edit */}
                                <Link
                                    to={`${s.id}/edit`}
                                    className="btn btn-sm btn-outline"
                                    title="Edit"
                                >
                                    <Edit size={16} />
                                </Link>

                                {/* Delete */}
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

            {/* Simple pager */}
            {totalPages > 1 && (
                <div className="join mt-4 flex justify-center">
                    {Array.from({ length: totalPages }).map((_, i) => (
                        <Link
                            key={i}
                            to={`/students?page=${i}`}
                            className={`join-item btn btn-xs ${i === page ? 'btn-active' : ''}`}
                        >
                            {i + 1}
                        </Link>
                    ))}
                </div>
            )}

            {/* Grades modal */}
            {isModalOpen && (
                <div className="modal modal-open">
                    <div className="modal-box">
                        <h3 className="font-bold text-lg">Grades</h3>
                        {isLoading ? (
                            <p>Loading...</p>
                        ) : selectedStudentGrades.length > 0 ? (
                            <table className="table w-full">
                                <thead>
                                <tr>
                                    <th>Subject</th>
                                    <th>Value</th>
                                    <th>Teacher</th>
                                    <th>Date</th>
                                </tr>
                                </thead>
                                <tbody>
                                {selectedStudentGrades.map((grade) => (
                                    <tr key={grade.id}>
                                        <td>{grade.subject.name}</td>
                                        <td>{grade.value}</td>
                                        <td>
                                            {grade.teacher.firstName}{' '}
                                            {grade.teacher.lastName}
                                        </td>
                                        <td>{grade.date}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        ) : (
                            <p>No grades found for this student.</p>
                        )}
                        <div className="modal-action">
                            <button
                                onClick={() => setIsModalOpen(false)}
                                className="btn"
                            >
                                Close
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
