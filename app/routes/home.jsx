export function meta() {
  return [
    { title: 'Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export default function Home() {
  return (
    <div className="min-h-screen bg-base-100 text-base-content">
      <div className="hero min-h-screen bg-gradient-to-br from-primary to-secondary">
        <div className="hero-content text-center text-neutral-content">
          <div className="max-w-xl">
            <h1 className="text-5xl font-bold">Welcome to Grade Center</h1>
            <p className="py-6 text-lg">
              A modern web-based school grade book for teachers, students, and parents. Effortless grade management,
              real-time performance insights, and secure access for everyone.
            </p>
            <div className="flex flex-col sm:flex-row justify-center gap-4">
              <button className="btn btn-accent btn-lg">Get Started</button>
              <button className="btn btn-outline btn-lg">Learn More</button>
            </div>
          </div>
        </div>
      </div>

      <section className="py-16 px-6 bg-base-200">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold">Features</h2>
          <p className="text-lg text-base-content/70">Everything you need in one place.</p>
        </div>
        <div className="grid gap-8 md:grid-cols-3 max-w-6xl mx-auto">
          <div className="card bg-base-100 shadow-xl">
            <div className="card-body">
              <h3 className="card-title">Role-Based Dashboards</h3>
              <p>Custom dashboards for admins, directors, teachers, parents, and students.</p>
            </div>
          </div>
          <div className="card bg-base-100 shadow-xl">
            <div className="card-body">
              <h3 className="card-title">Easy Grade Management</h3>
              <p>Teachers can enter, edit, and manage grades effortlessly with real-time feedback.</p>
            </div>
          </div>
          <div className="card bg-base-100 shadow-xl">
            <div className="card-body">
              <h3 className="card-title">Real-Time Access</h3>
              <p>Parents and students see grades and attendance instantly through secure login.</p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
