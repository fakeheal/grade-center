export function meta() {
  return [
    { title: 'Edit School - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export default function Edit() {
  return (
    <div className="bg-base-100 text-base-content py-20 lg:py-60">
      <div className="card mx-auto bg-base-100 w-full max-w-sm shrink-0 shadow-2xl">
        <div className="card-body">
          <h2 className="text-3xl font-bold">Edit School</h2>
          <fieldset className="fieldset">
            <label className="fieldset-label">Name</label>
            <input type="text" className="input w-full" placeholder="Name"/>
            <label className="fieldset-label">Address</label>
            <textarea className="textarea w-full" placeholder="Address"></textarea>
            <button className="btn btn-neutral mt-4">Edit</button>
          </fieldset>
        </div>
      </div>
    </div>
  );
}
