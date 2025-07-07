import {
  isRouteErrorResponse,
  Links,
  Meta,
  Outlet,
  redirect,
  Scripts,
  ScrollRestoration,
  useLoaderData,
} from 'react-router';

import stylesheet from './app.css?url';
import Footer from './layout/Footer';
import Header from './layout/Header';
import apiConfig from './api.config';
import { extractJwtToken } from './utilities/user';
import React from 'react';

export const links = () => [
  { rel: 'preconnect', href: 'https://fonts.googleapis.com' },
  {
    rel: 'preconnect',
    href: 'https://fonts.gstatic.com',
    crossOrigin: 'anonymous',
  },
  {
    rel: 'stylesheet',
    href: 'https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap',
  },
  { rel: 'stylesheet', href: stylesheet },
];

export async function loader({ request }) {
  if (request.url.endsWith('/login')) {
    return;
  }

  const cookie = request.headers.get('cookie');
  const token = extractJwtToken(cookie);

  if (!token) {
    return redirect('/login');
  }

  const response = await fetch(`${apiConfig.baseUrl}/users/me`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    return redirect('/login');
  }

  const user = await response.json();

  return { user };
}

export function Layout({ children }) {
  const { user } = useLoaderData() || {};

  return (
    <html lang="en" data-theme="lofi">
    <head>
      <meta charSet="utf-8"/>
      <meta name="viewport" content="width=device-width, initial-scale=1"/>
      <Meta/>
      <Links/>
    </head>
    <body>
    <Header/>
    {children}
    <ScrollRestoration/>
    <Scripts/>
    <Footer/>
    </body>
    </html>
  );
}

export default function App() {
  return <Outlet/>;
}

export function ErrorBoundary({ error }) {
  let message = 'Oops!';
  let details = 'An unexpected error occurred.';
  let stack;

  if (isRouteErrorResponse(error)) {
    message = error.status === 404 ? '404' : 'Error';
    details =
      error.status === 404
        ? 'The requested page could not be found.'
        : error.statusText || details;
  } else if (import.meta.env.DEV && error && error instanceof Error) {
    details = error.message;
    stack = error.stack;
  }

  return (
    <main className="pt-16 p-4 container mx-auto">
      <h1>{message}</h1>
      <p>{details}</p>
      {stack && (
        <pre className="w-full p-4 overflow-x-auto">
          <code>{stack}</code>
        </pre>
      )}
    </main>
  );
}
