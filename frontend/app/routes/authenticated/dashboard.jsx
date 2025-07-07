import { useOutletContext } from 'react-router';

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

export default function Dashboard() {

  const { user } = useOutletContext();
  return (
    <div className="container mx-auto py-6 lg:py-10">
      <div className="mb-4">
        <h2 className="text-2xl font-bold">Welcome, {user.firstName}! You're a {user.role} in {user.school.name}.</h2>
      </div>
    </div>
  );
}
