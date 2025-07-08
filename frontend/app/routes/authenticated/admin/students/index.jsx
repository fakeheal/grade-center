import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useOutletContext } from 'react-router';
import { USER_ROLES } from '../../../../app/utilities/user';
import apiConfig from '../../../../api.config';
import { BookOpen, Edit, Trash2 } from 'lucide-react';

export function meta() {
  return [
    { title: 'Admin Students - Grade Center' },
    { name: 'description', content: 'Admin view for managing students.' },
  ];
}

export default function AdminStudentsIndex() {
  const { user, token } = useOutletContext();
  const navigate = useNavigate();

  const [students, setStudents] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedStudentGrades, setSelectedStudentGrades] = useState([]);
  const [isLoadingGrades, setIsLoadingGrades] = useState(false);

  useEffect(() => {
    if (user.role !== USER_ROLES.ADMINISTRATOR) {
      navigate('/dashboard');
      return;
    }

    const fetchStudents = async () => {
      try {
        const res = await fetch(`${apiConfig.baseUrl}/students`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (!res.ok) {
          throw new Error('Failed to fetch students');
        }
        const data = await res.json();
        setStudents(data.content || []);
      } catch (err) {
        setError(err.message);
      } finally {
        setIsLoading(false);
      }
    };

    fetchStudents();
  }, [user, token, navigate]);

  const handleDelete = async (studentId) => {
    if (!window.confirm('Are you sure you want to delete this student?')) {
      return;
    }
    try {
      const res = await fetch(`${apiConfig.baseUrl}/students/${studentId}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!res.ok) {
        throw new Error('Failed to delete student. Maybe student has grades linked.');
      }
      setStudents(prevStudents => prevStudents.filter(s => s.id !== studentId));
    } catch (err) {
      setError(err.message);
    }
  };

  const handleViewGrades = async (studentId) => {
    setIsModalOpen(true);
    setIsLoadingGrades(true);
    try {
      const res = await fetch(`${apiConfig.baseUrl}/grades?studentId=${studentId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!res.ok) {
        throw new Error('Failed to fetch grades');
      }
      const grades = await res.json();
      setSelectedStudentGrades(grades);
    } catch (err) {
      console.error(err);
      // Handle error display to the user
    } finally {
      setIsLoadingGrades(false);
    }
  };

  if (isLoading) {
    return <div className="p-6">Loading students...</div>;
  }

  if (error) {
    return <div className="p-6 text-error">Error: {error}</div>;
  }

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-2xl font-bold">All Students (Admin View)</h1>
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
            {students.length === 0 ? (
              <tr>
                <td colSpan={5} className="text-center py-8">
                  No students found.
                </td>
              </tr>
            ) : (
              students.map((s, idx) => (
                <tr key={s.id} className="hover">
                  <td>{idx + 1}</td>
                  <td>{s.firstName}</td>
                  <td>{s.lastName}</td>
                  <td>{s.email}</td>
                  <td className="flex gap-2 justify-center">
                    <button
                      onClick={() => handleViewGrades(s.id)}
                      className="btn btn-sm btn-outline"
                      title="View Grades"
                    >
                      <BookOpen size={16} />
                    </button>
                    <Link
                      to={`/students/${s.id}/edit`}
                      className="btn btn-sm btn-outline"
                      title="Edit"
                    >
                      <Edit size={16} />
                    </Link>
                    <button
                      onClick={() => handleDelete(s.id)}
                      className="btn btn-sm btn-error"
                      title="Delete"
                    >
                      <Trash2 size={16} />
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {isModalOpen && (
        <div className="modal modal-open">
          <div className="modal-box">
            <h3 className="font-bold text-lg">Grades</h3>
            {isLoadingGrades ? (
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
                      <td>{grade.teacher.firstName} {grade.teacher.lastName}</td>
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