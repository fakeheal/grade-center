import React from 'react';
import { NavLink } from 'react-router';
import { UserContext } from '../state/user';

export default () => {
  const { user: { loggedIn } } = React.useContext(UserContext);


  return (
    <div className="navbar bg-base-100 border-b border-base-300/70">
      <div className="flex-1">
        <NavLink className="btn btn-ghost text-xl" to="/">Grade Center</NavLink>
      </div>

      <div className="flex-none">
        <ul className="menu menu-horizontal px-1">
          {loggedIn ? (
            <>
              <li>
                <NavLink to="/dashboard">Dashboard</NavLink>
              </li>
              <li>
                <NavLink to="/timetables">Timetables</NavLink>
              </li>
              <li>
                <NavLink to="/students">Students</NavLink>
              </li>
              <li>
                <NavLink to="/teachers">Teachers</NavLink>
              </li>

              {/* 🆕 Parents link */}

              <li>
                <NavLink to="/parents">Parents</NavLink>
              </li>

              <li>
                <NavLink to="/school/edit">School</NavLink>
              </li>
              <li>
                <NavLink to="/">Settings</NavLink>
              </li>
            </>
          ) : (
            <>
              <li>
                <NavLink to="/" end>Home</NavLink>
              </li>
              <li>
                <NavLink to="/login" end>Login</NavLink>
              </li>
            </>
          )}
        </ul>
      </div>
    </div>
  );
};
