import React from 'react';
import { NavLink, useLoaderData, useRouteLoaderData } from 'react-router';
import { USER_ROLES } from '../utilities/user';

export default () => {
  const { user } = useRouteLoaderData('root') || {};

  return (
    <div className="navbar bg-base-100 border-b border-base-300/70">
      <div className="flex-1">
        <NavLink className="btn btn-ghost text-xl" to="/">Grade Center</NavLink>
      </div>

      <div className="flex-none">
        <ul className="menu menu-horizontal px-1">
          {user?.id ? (
            <>
              <li>
                <NavLink to="/dashboard">Dashboard</NavLink>
              </li>
              {
                user.role === USER_ROLES.ADMINISTRATOR && (
                  <>
                    <li>
                      <NavLink to="/">School Years</NavLink>
                    </li>

                    <li>
                      <NavLink to="/students">Students</NavLink>
                    </li>
                    <li>
                      <NavLink to="/teachers">Teachers</NavLink>
                    </li>

                    <li>
                      <NavLink to="/parents">Parents</NavLink>
                    </li>

                  </>
                )
              }
              <li>
                <NavLink to="/timetables">Timetable</NavLink>
              </li>
              {user.role === USER_ROLES.DIRECTOR && (
                <li>
                  <NavLink to="/school/edit">School</NavLink>
                </li>
              )}
              <li>
                <NavLink to="/logout">Logout</NavLink>
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
