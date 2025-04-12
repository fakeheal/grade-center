import { NavLink } from 'react-router';

export default ({ isAuthenticated = true }) => {
  console.log(isAuthenticated);
  return (
    <div className="navbar bg-base-100 border-b border-base-300/70">
      <div className="flex-1">
        <a className="btn btn-ghost text-xl">Grade Center</a>
      </div>
      <div className="flex-none">
        <ul className="menu menu-horizontal px-1">
          {true ? (
            <>
              <li>
                <NavLink to="/">
                  Dashboard
                </NavLink>
              </li>
              <li>
                <NavLink to="/students">
                  Students
                </NavLink>
              </li>
              <li>
                <NavLink to="/school/edit">
                  School
                </NavLink>
              </li>
              <li>
                <NavLink to="/">
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
