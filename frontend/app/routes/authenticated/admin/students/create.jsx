import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useOutletContext } from 'react-router';
import apiConfig from '../../../../api.config';
import settings from '../../../../settings';
import StudentForm from '../../../../app/layout/forms/StudentForm';
import { validateStudent } from '../../../../app/utilities/validation';
import { USER_ROLES } from '../../../../app/utilities/user';

export function meta() {
  return [
    { title: 'Create Student (Admin) - Grade Center' },
    { name: 'description', content: 'Admin view for creating new students.' },
  ];
}

export default function AdminCreateStudent() {
  const { user, token } = useOutletContext();
  const navigate = useNavigate();

  const [parents, setParents] = useState([]);
  const [isLoadingParents, setIsLoadingParents] = useState(true);
  const [fetchError, setFetchError] = useState(null);

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    repeatPassword: '',
    grade: '',
    classId: '',
    parentIds: [],
  });

  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    if (user.role !== USER_ROLES.ADMINISTRATOR) {
      navigate('/dashboard');
      return;
    }

    const fetchParents = async () => {
      try {
        const parentsRes = await fetch(
          `${apiConfig.baseUrl}/parents?schoolId=${settings.schoolId}&size=1000`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        if (!parentsRes.ok) {
          throw new Error('Failed to fetch parents');
        }
        const parentsRaw = (await parentsRes.json()).content ?? [];
        setParents(parentsRaw);
      } catch (err) {
        setFetchError(err.message);
      } finally {
        setIsLoadingParents(false);
      }
    };

    fetchParents();
  }, [user, token, navigate]);

  const handleFormChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleParentSelectionChange = (newParentIds) => {
    setFormData(prev => ({ ...prev, parentIds: newParentIds }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setErrors({});
    setFetchError(null);

    const payload = {
      firstName: formData.firstName?.trim(),
      lastName: formData.lastName?.trim(),
      email: formData.email?.trim(),
      password: formData.password,
      repeatPassword: formData.repeatPassword,
      schoolId: settings.schoolId,
      grade: Number(formData.grade),
      classId: Number(formData.classId),
      parentIds: formData.parentIds.map(Number),
    };

    const validationErrors = validateStudent(payload, 'CREATE');
    if (Object.keys(validationErrors).length) {
      setErrors(validationErrors);
      setIsSubmitting(false);
      return;
    }

    try {
      const res = await fetch(`${apiConfig.baseUrl}/students`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        const msg = (await res.json())?.message ?? 'Failed to create student.';
        setErrors({ general: msg });
        return;
      }

      navigate('/admin/students');
    } catch (err) {
      setFetchError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  if (isLoadingParents) {
    return <div className="p-6">Loading form data...</div>;
  }

  if (fetchError) {
    return <div className="p-6 text-error">Error: {fetchError}</div>;
  }

  return (
    <div className="p-6 max-w-xl mx-auto">
      <h1 className="text-2xl font-bold mb-4">Add Student (Admin)</h1>

      {errors.general && (
        <div className="alert alert-error mb-4">{errors.general}</div>
      )}

      <form onSubmit={handleSubmit}>
        <StudentForm
          parents={parents}
          errors={errors}
          formData={formData}
          onFormChange={handleFormChange}
          onParentChange={handleParentSelectionChange}
          isEdit={false}
        />
        <div className="mt-4">
          <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
            {isSubmitting ? (
              <span className="loading loading-spinner loading-sm"></span>
            ) : (
              'Create Student'
            )}
          </button>
          <Link to="/admin/students" className="btn btn-ghost ml-2">Cancel</Link>
        </div>
      </form>
    </div>
  );
}