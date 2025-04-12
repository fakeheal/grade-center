import { VictoryBoxPlot, VictoryChart, VictoryLine, VictoryPie, VictoryTheme } from 'victory';

const data = [
  { quarter: 1, earnings: 13000 },
  { quarter: 2, earnings: 16500 },
  { quarter: 3, earnings: 14250 },
  { quarter: 4, earnings: 19000 }
];

export function meta() {
  return [
    { title: 'Dashboard - Grade Center' },
    { name: 'description', content: 'A modern web-based school grade book for teachers, students, and parents.' },
  ];
}

const series = [
  {
    name: 'Canada',
    data: [
      3.9670002, 5.2650003, 6.201,
      7.8010006, 9.694, 11.214001,
      11.973001, 12.250001, 12.816001,
      13.413001, 13.626961, 14.30356,
      15.295461,
    ],
  },
];


export default function Dashboard() {
  return (
    <div className="container mx-auto py-6 lg:py-10">
      <div className="mb-4">
        <h2 className="text-2xl font-bold">Welcome, John!</h2>
      </div>
      <div className="bg-base-100 text-base-content">
        <div className="stats shadow">
          <div className="stat">
            <div className="stat-figure text-primary">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                   stroke="currentColor" className="size-6">
                <path strokeLinecap="round" strokeLinejoin="round"
                      d="M18 18.72a9.094 9.094 0 0 0 3.741-.479 3 3 0 0 0-4.682-2.72m.94 3.198.001.031c0 .225-.012.447-.037.666A11.944 11.944 0 0 1 12 21c-2.17 0-4.207-.576-5.963-1.584A6.062 6.062 0 0 1 6 18.719m12 0a5.971 5.971 0 0 0-.941-3.197m0 0A5.995 5.995 0 0 0 12 12.75a5.995 5.995 0 0 0-5.058 2.772m0 0a3 3 0 0 0-4.681 2.72 8.986 8.986 0 0 0 3.74.477m.94-3.197a5.971 5.971 0 0 0-.94 3.197M15 6.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Zm6 3a2.25 2.25 0 1 1-4.5 0 2.25 2.25 0 0 1 4.5 0Zm-13.5 0a2.25 2.25 0 1 1-4.5 0 2.25 2.25 0 0 1 4.5 0Z"/>
              </svg>
            </div>
            <div className="stat-title">Total Students</div>
            <div className="stat-value text-primary">842</div>
            <div className="stat-desc">21% more than last year</div>
          </div>

          <div className="stat">
            <div className="stat-figure text-secondary">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                   stroke="currentColor" className="size-6">
                <path strokeLinecap="round" strokeLinejoin="round"
                      d="M11.48 3.499a.562.562 0 0 1 1.04 0l2.125 5.111a.563.563 0 0 0 .475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 0 0-.182.557l1.285 5.385a.562.562 0 0 1-.84.61l-4.725-2.885a.562.562 0 0 0-.586 0L6.982 20.54a.562.562 0 0 1-.84-.61l1.285-5.386a.562.562 0 0 0-.182-.557l-4.204-3.602a.562.562 0 0 1 .321-.988l5.518-.442a.563.563 0 0 0 .475-.345L11.48 3.5Z"/>
              </svg>

            </div>
            <div className="stat-title">Grades</div>
            <div className="stat-value text-secondary">4228</div>
            <div className="stat-desc">42% more than last year</div>
          </div>

          <div className="stat">
            <div className="stat-figure text-secondary">
              <div className="avatar online">
                <div className="w-16 rounded-full">
                  <img src="https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp"/>
                </div>
              </div>
            </div>
            <div className="stat-value text-green-600">94%</div>
            <div className="stat-title">Star Teacher</div>
            <div className="stat-desc text-secondary">Sarah Cohen</div>
          </div>
        </div>
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-4 mt-4">
          <VictoryChart
            theme={VictoryTheme.grayscale}
          >
            <VictoryLine
              data={series[0].data.map(
                (d, i) => ({
                  x: i + 2010,
                  y: d,
                }),
              )}
            />
          </VictoryChart>
          <VictoryChart
            domainPadding={20}
            theme={VictoryTheme.clean}
          >
            <VictoryBoxPlot
              boxWidth={15}
              data={[
                { x: 1, y: ["2", "3", "5", "8"] },
                { x: 2, y: ["1", "3", "5", "8"] },
                { x: 3, y: ["2", "5", "7", "9"] },
                { x: 4, y: ["4", "6", "7", "9"] },
                { x: 5, y: ["1", "2", "4", "5"] },
                { x: 6, y: ["1", "2", "6", "8"] },
                { x: 7, y: ["2", "4", "5", "8"] },
                { x: 8, y: ["1", "4", "4", "7"] },
                { x: 9, y: ["2", "5", "7", "9"] },
              ]}
            />
          </VictoryChart>
          <VictoryPie
            data={[
              { x: "Cats", y: 35 },
              { x: "Dogs", y: 40 },
              { x: "Birds", y: 55 },
            ]}
            theme={VictoryTheme.clean}
          />
        </div>
      </div>
    </div>
  );
}
