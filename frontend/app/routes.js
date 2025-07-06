import { index, prefix, route } from '@react-router/dev/routes';

export default [
  index('routes/home.jsx'),
  route('login', 'routes/login.jsx'),
  route('logout', 'routes/authenticated/logout.jsx'),

  route('dashboard', 'routes/authenticated/dashboard.jsx'),
  route('school/edit', 'routes/authenticated/school/edit.jsx'),
  ...prefix('students', [
    index('routes/authenticated/students/index.jsx'),
    route('create', 'routes/authenticated/students/create.jsx'),
    route(':id/edit', 'routes/authenticated/students/edit.jsx'),
  ]),

  /**
   * Teachers routes
   */
  ...prefix('teachers', [
    index('routes/authenticated/teachers/index.jsx'),
    route('create', 'routes/authenticated/teachers/create.jsx'),
    route(':id/edit', 'routes/authenticated/teachers/edit.jsx'),
  ]),

  ...prefix('school-years', [
    index('routes/authenticated/school-years/index.jsx'),
    route('create', 'routes/authenticated/school-years/create.jsx'),
  ]),

  /**
   * Timetable routes
   */
  ...prefix('timetables', [
    index('routes/authenticated/timetables/create.jsx')
  ]),

  ...prefix('class', [
    index('routes/authenticated/class/index.jsx'),
    route(':id/edit', 'routes/authenticated/class/edit.jsx'),
  ]),
  ...prefix('directors', [
    route(':id/edit', 'routes/authenticated/director/edit.jsx'),
  ]),
  ...prefix('parents', [
    index('routes/authenticated/parents/index.jsx'),       // List all parents
    route('create', 'routes/authenticated/parents/create.jsx'), // Add new parent
    route(':id/edit', 'routes/authenticated/parents/edit.jsx'), // Edit parent
  ]),
  ...prefix('subjects', [
    route(':id/edit', 'routes/authenticated/subjects/edit.jsx'),
  ]),
  ...prefix('grades', [
    index('routes/authenticated/grades/index.jsx'),
    route('create', 'routes/authenticated/grades/create.jsx'),
  ]),
];
