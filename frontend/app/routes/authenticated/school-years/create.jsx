import { Link, useNavigate, useOutletContext } from 'react-router';
import ErrorIcon from '../../../layout/icons/ErrorIcon';
import apiConfig from '../../../api.config';
import React from 'react';

export function meta() {
  return [
    { title: 'Create School Year - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

const YEARS = [2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 2034, 2033, 2035, 2036];
const TERMS = [1, 2];

export default function Create({}) {

  const { user, token } = useOutletContext();
  let navigate = useNavigate();

  const [year, setYear] = React.useState('');
  const [term, setTerm] = React.useState('');

  const [isLoading, setIsLoading] = React.useState(false);

  const [error, setError] = React.useState(null);

  const createSchoolYear = () => {
    if (isLoading) return;

    if (!year || !term) {
      setError('Please select both year and term.');
      return;
    }

    setIsLoading(true);
    setError(null);

    fetch(`${apiConfig.baseUrl}/school-years`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({
        schoolId: user.school.id,
        year,
        term,
      }),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to create school year. Check your permissions.');
        }
        return response.json();
      })
      .then(() => {
        navigate('/school-years');
      })
      .catch(err => {
        setError(err.message);
      })
      .finally(() => setIsLoading(false));
  }

  return (
    <div className="bg-base-100 text-base-content py-10 lg:py-20">
      <div className="card mx-auto bg-base-100 w-full max-w-lg shrink-0 shadow-2xl">
        <div className="card-body">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-3xl font-bold">New School Year</h2>
            <Link to={'/school-years'} className="link">&laquo; School Year</Link>
          </div>
          {error && (
            <div role="alert" className="alert alert-error">
              <ErrorIcon/>
              <span>{error}</span>
            </div>
          )}
          <div className="mb-2">
            <label className="fieldset-label" htmlFor="year">Year</label>
            <select onChange={e => setYear(e.target.value)} className="select w-full">
              <option>Select Year</option>
              {YEARS.map(year => (
                <option key={year} value={year}>{year}</option>
              ))}
            </select>
          </div>
          <div className="mb-2">
            <label className="fieldset-label" htmlFor="term">Term</label>
            <select onChange={e => setTerm(e.target.value)} className="select w-full">
              <option>Select Term</option>
              {TERMS.map(term => (
                <option key={term} value={term}>{term}</option>
              ))}
            </select>
          </div>
          <button className="btn btn-primary w-full" onClick={createSchoolYear}>
            {isLoading ? (
              <span className="loading loading-spinner loading-sm"></span>
            ) : (
              'Create School Year'
            )}
          </button>
        </div>
      </div>
    </div>
  );
}
