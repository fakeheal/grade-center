export function meta() {
  return [
    { title: 'Students - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

export default function Index() {
  return (
    <div className="container mx-auto">
      <div className="py-8 breadcrumbs text-sm">
        <ul>
          <li><a>Home</a></li>
          <li><a>Students</a></li>
        </ul>
      </div>
      <div className="card bg-base-100 shadow-sm mb-12">
        <div className="card-body">
          <div className="flex justify-between items-center">

            <h2 className="card-title">Students</h2>
            <a href="#" className="btn btn-primary btn-sm">Add Student</a>
          </div>
          <div className="bg-base-100 text-base-content">
            <div className="overflow-x-auto">
              <table className="table">
                {/* head */}
                <thead>
                <tr>
                  <th>Student</th>
                  <th>Class</th>
                  <th>Avg. Grade</th>
                  <th></th>
                </tr>
                </thead>
                <tbody>
                {/* row 1 */}
                <tr>
                  <td>
                    <div className="flex items-center gap-3">
                      <div className="avatar">
                        <div className="mask mask-squircle h-12 w-12">
                          <img
                            src="https://img.daisyui.com/images/profile/demo/2@94.webp"
                            alt="Avatar Tailwind CSS Component"/>
                        </div>
                      </div>
                      <div>
                        <div className="font-bold">Hart Hagerty</div>
                        <div className="text-sm opacity-50">United States</div>
                      </div>
                    </div>
                  </td>
                  <td>
                    12th A Grade
                    <br/>
                    <span className="badge badge-ghost badge-sm">Nadezhda Dobreva</span>
                  </td>
                  <td>4.87</td>
                  <th className="text-right">
                    <button className="btn btn-xs">details</button>
                  </th>
                </tr>
                <tr>
                  <td>
                    <div className="flex items-center gap-3">
                      <div className="avatar">
                        <div className="mask mask-squircle h-12 w-12">
                          <img
                            src="https://img.daisyui.com/images/profile/demo/2@94.webp"
                            alt="Avatar Tailwind CSS Component"/>
                        </div>
                      </div>
                      <div>
                        <div className="font-bold">Hart Hagerty</div>
                        <div className="text-sm opacity-50">United States</div>
                      </div>
                    </div>
                  </td>
                  <td>
                    12th A Grade
                    <br/>
                    <span className="badge badge-ghost badge-sm">Nadezhda Dobreva</span>
                  </td>
                  <td>4.87</td>
                  <th className="text-right">
                    <button className="btn btn-xs">details</button>
                  </th>
                </tr>
                <tr>
                  <td>
                    <div className="flex items-center gap-3">
                      <div className="avatar">
                        <div className="mask mask-squircle h-12 w-12">
                          <img
                            src="https://img.daisyui.com/images/profile/demo/2@94.webp"
                            alt="Avatar Tailwind CSS Component"/>
                        </div>
                      </div>
                      <div>
                        <div className="font-bold">Hart Hagerty</div>
                        <div className="text-sm opacity-50">United States</div>
                      </div>
                    </div>
                  </td>
                  <td>
                    12th A Grade
                    <br/>
                    <span className="badge badge-ghost badge-sm">Nadezhda Dobreva</span>
                  </td>
                  <td>4.87</td>
                  <th className="text-right">
                    <button className="btn btn-xs">details</button>
                  </th>
                </tr>
                <tr>
                  <td>
                    <div className="flex items-center gap-3">
                      <div className="avatar">
                        <div className="mask mask-squircle h-12 w-12">
                          <img
                            src="https://img.daisyui.com/images/profile/demo/2@94.webp"
                            alt="Avatar Tailwind CSS Component"/>
                        </div>
                      </div>
                      <div>
                        <div className="font-bold">Hart Hagerty</div>
                        <div className="text-sm opacity-50">United States</div>
                      </div>
                    </div>
                  </td>
                  <td>
                    12th A Grade
                    <br/>
                    <span className="badge badge-ghost badge-sm">Nadezhda Dobreva</span>
                  </td>
                  <td>4.87</td>
                  <th className="text-right">
                    <button className="btn btn-xs">details</button>
                  </th>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div className="justify-start card-actions border-t border-base-300/80 pt-4">
            <div className="join">
              <button className="join-item btn btn-sm">1</button>
              <button className="join-item btn btn-sm btn-active">2</button>
              <button className="join-item btn btn-sm">3</button>
              <button className="join-item btn btn-sm">4</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
