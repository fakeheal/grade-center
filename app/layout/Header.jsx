import { NavLink } from 'react-router';

export default ({ isAuthenticated }) => {

  return (
    <div className="navbar bg-base-100 border-b border-base-300/70">
      <div className="flex-1">
        <a className="btn btn-ghost text-xl">Grade Center</a>
      </div>
      <div className="flex-none">
        <ul className="menu menu-horizontal px-1">
          {isAuthenticated ? (
            <>
              <li>
                <NavLink to="/" end>
                  Dashboard
                </NavLink>
              </li>
              <li>
                <NavLink to="/" end>
                  Grades
                </NavLink>
              </li>
              <li>
                <NavLink to="/" end>
                  Attendance
                </NavLink>
              </li>
              <li>
                <NavLink to="/" end>
                  Settings
                </NavLink>
              </li>
            </>
          ) : (
            <>
              <li>
                <NavLink to="/" end>
                  Home
                </NavLink>
              </li>
              <li>
                <NavLink to="/login" end>
                  Login
                </NavLink>
              </li>
              <li>
                <NavLink to="/signup" end>
                  Sign Up
                </NavLink>
              </li>
            </>
          )}
        </ul>
      </div>
    </div>
  );
}
