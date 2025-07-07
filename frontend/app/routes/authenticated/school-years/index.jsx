import React from 'react';
import apiConfig from '../../../api.config';
import { Link, useNavigate, useOutletContext } from 'react-router';
import { USER_ROLES } from '../../../utilities/user';

export function meta() {
  return [
    { title: 'School Years - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export default function Index({}) {
  const { user, token } = useOutletContext();
  let navigate = useNavigate();

  const [isLoading, setIsLoading] = React.useState(false);
  const [schoolYears, setSchoolYears] = React.useState([]);

  React.useEffect(() => {
    if (user.role !== USER_ROLES.ADMINISTRATOR) {
      navigate('/dashboard');
    }

    setIsLoading(true);

    fetch(`${apiConfig.baseUrl}/school-years?schoolId=${user.school.id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    }).then(response => response.json())
      .then(data => {
        setSchoolYears(data.content);
      })
      .finally(() => setIsLoading(false));
  }, []);

  return (
    <div className="container mx-auto">
      <div className="py-8 breadcrumbs text-sm">
        <ul>
          <li><a>Home</a></li>
          <li><a>School Years</a></li>
        </ul>
      </div>
      <div className="card bg-base-100 shadow-sm mb-12">
        <div className="card-body">
          <div className="flex justify-between items-center">

            <h2 className="card-title">School Years</h2>
            <Link to={'/school-years/create'} className="btn btn-primary btn-sm">Add School Year</Link>
          </div>
          <div className="bg-base-100 text-base-content">
            <div className="overflow-x-auto">
              <table className="table">
                {/* head */}
                <thead>
                <tr>
                  <th>Year</th>
                  <th>Term</th>
                </tr>
                </thead>
                <tbody>
                {isLoading && (
                  <tr>
                    <td colSpan={2} className="text-center text-sm opacity-50">
                      Loading school years...
                    </td>
                  </tr>
                )}
                {schoolYears.length === 0 && (
                  <tr>
                    <td colSpan={2} className="text-center text-sm opacity-50">
                      No school years found. Add new school years to get started.
                    </td>
                  </tr>
                )}
                {
                  schoolYears.map(schoolYear => (
                    <tr key={schoolYear.id}>
                      <td>
                        <div className="flex items-center gap-3">
                          <div>
                            <div className="font-bold">
                              {schoolYear.year}
                            </div>
                          </div>
                        </div>
                      </td>
                      <td>

                        {schoolYear.term === 1 ? 'First' : 'Second'}
                      </td>
                    </tr>
                  ))
                }
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

