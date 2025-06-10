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
    // route(':id/edit', 'routes/authenticated/teachers/edit.jsx'),
  ]),
];
