import React from 'react';
import ErrorIcon from '../icons/ErrorIcon';
import { useNavigate, useOutletContext } from 'react-router';
import apiConfig from '../../api.config';


export default function TeacherDelete({ teacherId, onSuccess }) {
  const [errors, setErrors] = React.useState({});
  const { user, token } = useOutletContext('root');

  const navigate = useNavigate();
  const _onDeleteButtonClicked = async (e) => {
    e.preventDefault();
    if (!confirm('Are you sure you want to delete this teacher? This action cannot be undone.')) {
      return;
    }
    const response = await fetch(`${apiConfig.baseUrl}/teachers/${teacherId}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });

    if (!response.ok) {
      const data = await response.json();
      setErrors({ delete: data.message });
      return;
    }
    navigate(`/teachers`);
  }

  return (
    <div className="mt-20 card mx-auto card-dash bg-red-100 w-96">
      <div className="card-body">
        <h2 className="card-title">Danger Zone</h2>
        <p>Delete this teacher and all its related grades, subjects, leave of absence, etc.</p>
        <div className="card-actions justify-end">
          <button type="submit" className="btn btn-primary" onClick={_onDeleteButtonClicked}>DELETE</button>
        </div>
        {errors?.delete && (
          <div role="alert" className="alert alert-error">
            <ErrorIcon/>
            <span>{errors.delete}</span>
          </div>
        )}
      </div>
    </div>
  );
}
