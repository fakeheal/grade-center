import { index, route } from '@react-router/dev/routes';

export default [
  index('routes/home.jsx'),
  route('login', 'routes/login.jsx'),
  route('signup', 'routes/signup.jsx'),

  route('school/edit', 'routes/authenticated/school/edit.jsx'),
];
