import React from 'react';
import apiConfig from '../../../api.config';
import { Link } from 'react-router';

export function meta() {
    return [
        { title: 'Teachers - Grade Center' },
        { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
    ];
}

export async function loader({ request }) {
    const page = (new URL(request.url)).searchParams.get('page') || 0;
    const response = await fetch(`${apiConfig.baseUrl}/teachers`);
    return await response.json();
}

export function HydrateFallback() {
    return <div>Loading...</div>;
}

export default function Index({ loaderData }) {
    return (
        <div className="container mx-auto">
            <div className="py-8 breadcrumbs text-sm">
                <ul>
                    <li><a>Home</a></li>
                    <li><a>Teachers</a></li>
                </ul>
            </div>
            <div className="card bg-base-100 shadow-sm mb-12">
                <div className="card-body">
                    <div className="flex justify-between items-center">

                        <h2 className="card-title">Teachers</h2>
                        <Link to={'/teachers/create'} className="btn btn-primary btn-sm">Add Teacher</Link>
                    </div>
                    <div className="bg-base-100 text-base-content">
                        <div className="overflow-x-auto">
                            <table className="table">
                                {/* head */}
                                <thead>
                                <tr>
                                    <th>Teacher</th>
                                    <th>Subjects</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                {loaderData.content.length === 0 && (
                                    <tr>
                                        <td colSpan={3} className="text-center text-sm opacity-50">
                                            No teachers found.
                                        </td>
                                    </tr>
                                )}
                                {
                                    loaderData.content.map(teacher => (
                                        <tr key={teacher.id}>
                                            <td>
                                                <div className="flex items-center gap-3">
                                                    <div>
                                                        <div className="font-bold">
                                                            {teacher.user.firstName} {teacher.user.lastName}
                                                        </div>
                                                        <div className="text-sm opacity-50">
                                                            {teacher.user.email}
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                {teacher.subjects.map(subject => (
                                                    <span key={subject.id} className="badge badge-ghost badge-sm">{subject.name}</span>
                                                ))}
                                            </td>
                                            <th className="text-right">
                                                <Link to={`/teachers/${teacher.id}/edit`} className="btn btn-xs">edit</Link>
                                            </th>
                                        </tr>
                                    ))
                                }
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div className="justify-start card-actions border-t border-base-300/80 pt-4">
                        <div className="join">
                            {
                                Array.from(Array(loaderData.totalPages).keys())
                                    .map((_, index) =>
                                        (
                                            <Link to={`/teachers?page=${index}`} key={index} className="join-item btn btn-sm">
                                                {index + 1}
                                            </Link>
                                        ))
                            }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

