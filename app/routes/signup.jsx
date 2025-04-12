export function meta() {
  return [
    { title: 'Signup - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export default function Signup() {
  return (
    <div className="bg-base-100 text-base-content">
      <div className="hero bg-base-200 py-20 lg:py-60">
        <div className="hero-content flex-col lg:flex-row-reverse">
          <div className="text-center lg:text-left">
            <h1 className="text-5xl font-bold">Join now!</h1>
            <p className="py-6">
              Join Grade Center and simplify how you manage school life.
            </p>
            <p className="pb-6">
              Whether you're a student, teacher, parent, director, or administrator — Grade Center gives you secure and
              personalized access to everything you need. Track progress, manage grades, and stay connected.
            </p>
            <ul className="italic">
              <li className="py-2">
                👩‍🏫 Role-based dashboards
              </li>
              <li className="py-2">
                📊 Real-time grade access
              </li>
              <li className="py-2">
                ✅ Easy-to-use and mobile friendly
              </li>
              <li className="py-2">
                🔐 Secure and private
              </li>
            </ul>
          </div>
          <div className="card bg-base-100 w-full max-w-sm shrink-0 shadow-2xl mr-0 lg:mr-20">
            <div className="card-body">
              <fieldset className="fieldset">
                <label className="fieldset-label">I am a </label>
                <select defaultValue="Pick a color" className="select">
                  <option disabled={true}>Pick a role</option>
                  <option>Student</option>
                  <option>Parent</option>
                </select>
                <label className="fieldset-label">First Name</label>
                <input type="text" className="input" placeholder="First Name"/>
                <label className="fieldset-label">Last Name</label>
                <input type="text" className="input" placeholder="Last Name"/>
                <label className="fieldset-label">Email</label>
                <input type="email" className="input" placeholder="Email"/>
                <label className="fieldset-label">Password</label>
                <input type="password" className="input" placeholder="Password"/>
                <label className="fieldset-label">Password (Confirm)</label>
                <input type="password" className="input" placeholder="Password"/>
                <button className="btn btn-neutral mt-4">Signup</button>
              </fieldset>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
