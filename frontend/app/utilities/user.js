export const USER_ROLES = {
  ADMINISTRATOR: 'ADMINISTRATOR',
  TEACHER: 'TEACHER',
  STUDENT: 'STUDENT',
  PARENT: 'PARENT',
  DIRECTOR: 'DIRECTOR',
};
export const extractJwtToken = (cookieString) => {
  if (!cookieString) {
    return null;
  }
  const cookies = cookieString
    .split(';')
    .map(cookie => cookie.trim())
    .map(cookie => {
      const [key, ...val] = cookie.split('=');
      return [key, val.join('=')];
    })
    .reduce((acc, [key, value]) => {
      acc[key] = decodeURIComponent(value);
      return acc;
    }, {});

  return cookies.jwt || null;
}
