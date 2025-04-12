export default ({ isAuthenticated }) => {

  return (
    <div className="navbar bg-base-100 shadow-sm">
      <div className="flex-1">
        <a className="btn btn-ghost text-xl">Grade Center</a>
      </div>
      <div className="flex-none">
        <ul className="menu menu-horizontal px-1">
          {isAuthenticated ? (
            <>
              <li><a>Dashboard</a></li>
              <li><a>Grades</a></li>
              <li><a>Attendance</a></li>
              <li><a>Settings</a></li>
            </>
          ) : (
            <>
              <li><a>Login</a></li>
              <li><a>Sign Up</a></li>
            </>
          )}
        </ul>
      </div>
    </div>
  );
}
