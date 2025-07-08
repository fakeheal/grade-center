export const getJwt = (cookieString = document?.cookie ?? '') =>
    cookieString
        .split(';')
        .map(c => c.trim())
        .find(c => c.startsWith('jwt='))?.split('=')[1] ?? null;