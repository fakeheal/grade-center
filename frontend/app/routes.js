import { index, prefix, route } from '@react-router/dev/routes';

export default [
  index('routes/home.jsx'),
  route('login', 'routes/login.jsx'),
  route('signup', 'routes/signup.jsx'),

  route('dashboard', 'routes/authenticated/dashboard.jsx'),
  route('school/edit', 'routes/authenticated/school/edit.jsx'),
  route('students', 'routes/authenticated/students/index.jsx'),

  /**
   * Teachers routes
   */
  ...prefix('teachers', [
    index('routes/authenticated/teachers/index.jsx'),
    route('create', 'routes/authenticated/teachers/create.jsx'),
    route(':id/edit', 'routes/authenticated/teachers/edit.jsx'),
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
];
