import { useNavigate } from 'react-router';
import React from 'react';

export default function Logout() {
  const navigate = useNavigate();

  React.useEffect(() => {
    document.cookie = '';
    navigate('/login');
  }, [navigate]);

  return null;
}
