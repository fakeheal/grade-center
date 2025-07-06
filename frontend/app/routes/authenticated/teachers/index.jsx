import apiConfig from '../../../api.config';
import React from 'react';
import { Link, redirect, Form, useNavigation } from 'react-router';
import { Trash2 } from 'lucide-react';

export async function action({ request }) {
  const formData = await request.formData();
  const id = formData.get('deleteId');

  const res = await fetch(`${apiConfig.baseUrl}/teachers/${id}`, {
    method: 'DELETE',
  });

  if (!res.ok)
    return { error: 'Delete failed. Maybe teacher has grades linked.' };

  return redirect('/teachers');
}

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
                  <th className="text-center">Actions</th>
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
                      <td className="flex gap-2 justify-center">
                        <Link
                          to={`/teachers/${teacher.id}/edit`}
                          className="btn btn-sm btn-outline"
                          title="Edit"
                        >
                          Edit
                        </Link>
                        <Form method="post" className="inline">
                          <input type="hidden" name="deleteId" value={teacher.user.id} />
                          <button
                            type="submit"
                            className="btn btn-sm btn-error"
                            title="Delete"
                            disabled={navigation.state === 'submitting'}
                            onClick={(e) => {
                              if (
                                !window.confirm(
                                  `Delete ${teacher.user.firstName} ${teacher.user.lastName}?`
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

