import { Link, useLoaderData } from 'react-router';
import apiConfig from '../../../api.config';

export function meta() {
    return [
        { title: 'Grades - Grade Center' },
        { name: 'description', content: 'Manage student grades.' },
    ];
}

export async function loader() {
    const res = await fetch(`${apiConfig.baseUrl}/grades`);
    if (!res.ok) throw new Response('Failed to load grades', { status: 500 });
    return await res.json();
}

export default function GradesIndex() {
    const grades = useLoaderData();

    return (
        <div className="p-6">
            <div className="flex justify-between items-center mb-4">
                <h1 className="text-2xl font-bold">Grades</h1>
                <Link to="/grades/create" className="btn btn-primary">
                    + Add Grade
                </Link>
            </div>

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
                                    <td>{grade.student.firstName} {grade.student.lastName}</td>
                                    <td>{grade.subject.name}</td>
                                    <td>{grade.value}</td>
                                    <td>{grade.date}</td>
                                    <td>{grade.teacher.firstName} {grade.teacher.lastName}</td>
                                    <td>
                                        {/* Add Edit/Delete buttons here if needed */}
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
