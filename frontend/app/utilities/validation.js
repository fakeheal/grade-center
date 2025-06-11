function validateEmail(email) {
  return /\S+@\S+\.\S+/.test(email);
}

export const TEACHER_MODE = {
  CREATE: 'create',
  EDIT: 'edit',
};

export function validateTeacher(
  firstName,
  lastName,
  email,
  password,
  repeatPassword,
  subjectIds,
  mode = TEACHER_MODE.CREATE
) {

  const errors = {};

  if (!firstName) {
    errors.firstName = 'First name is required';
  }
  if (!lastName) {
    errors.lastName = 'Last name is required';
  }
  if (!email) {
    errors.email = 'Email is required';
  } else if (!validateEmail(email)) {
    errors.email = 'Email is invalid';
  }
  if (!password && mode === TEACHER_MODE.CREATE) {
    errors.password = 'Password is required';
  } else if (password !== repeatPassword) {
    errors.repeatPassword = 'Passwords do not match';
  }
  if (subjectIds.length === 0) {
    errors.subjects = 'At least one subject must be selected';
  }

  return errors;
}
