import React from 'react';
import { NavLink, useRouteLoaderData } from 'react-router';
import { USER_ROLES } from '../utilities/user';

export default function Header() {
    const { user } = useRouteLoaderData('root') || {};

    return (
        <div className="navbar bg-base-100 border-b border-base-300/70">
            <div className="flex-1">
                <NavLink className="btn btn-ghost text-xl" to="/">
                    Grade Center
                </NavLink>
            </div>

            <div className="flex-none">
                <ul className="menu menu-horizontal px-1">
                    {/* ---------- when logged in ---------- */}
                    {user?.id ? (
                        <>
                            <li>
                                <NavLink to="/dashboard">Dashboard</NavLink>
                            </li>

                            {/* ADMINISTRATOR menu */}
                            {user.role === USER_ROLES.ADMINISTRATOR && (
                                <>
                                    <li><NavLink to="/school-years">School Years</NavLink></li>
                                    <li><NavLink to="/students">Students</NavLink></li>
                                    <li><NavLink to="/teachers">Teachers</NavLink></li>
                                    <li><NavLink to="/parents">Parents</NavLink></li>
                                    <li><NavLink to="/grades">Grades</NavLink></li>
                                    <li><NavLink to="/absences">Absences</NavLink></li>
                                    <li><NavLink to="/timetables">Timetable</NavLink></li>{/* 🔒 */}
                                </>
                            )}

                            {/* TEACHER menu */}
                            {user.role === USER_ROLES.TEACHER && (
                                <>
                                    <li><NavLink to="/students">Students</NavLink></li>
                                    <li><NavLink to="/grades">Grades</NavLink></li>
                                    <li><NavLink to="/absences">Absences</NavLink></li>
                                </>
                            )}

                            {/* PARENT menu */}
                            {user.role === USER_ROLES.PARENT && (
                                <li><NavLink to="/students">Students</NavLink></li>
                            )}

                            {/* STUDENT menu */}
                            {user.role === USER_ROLES.STUDENT && (
                                <>
                                    <li><NavLink to="/grades">Grades</NavLink></li>
                                    <li><NavLink to="/absences">Absences</NavLink></li>
                                </>
                            )}

                            {/* DIRECTOR menu */}
                            {user.role === USER_ROLES.DIRECTOR && (
                                <>
                                    <li><NavLink to="/timetables">Timetable</NavLink></li>{/* 🔒 */}
                                    <li><NavLink to="/school/edit">School</NavLink></li>
                                    <li><NavLink to="/teachers">Teachers</NavLink></li>
                                    <li><NavLink to="/school-years">School Years</NavLink></li>
                                </>
                            )}

                            <li>
                                <NavLink to="/logout">Logout</NavLink>
                            </li>
                        </>
                    ) : (
                        /* ---------- public / not logged in ---------- */
                        <>
                            <li><NavLink to="/" end>Home</NavLink></li>
                            <li><NavLink to="/login" end>Login</NavLink></li>
                        </>
                    )}
                </ul>
            </div>
        </div>
    );
}
