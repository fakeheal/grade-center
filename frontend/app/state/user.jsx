import { createContext, useState } from 'react';

export const USER_DEFAULT_STATE = { loggedIn: null, user: null };

export const UserContext = createContext(null);

const UserProvider = ({ children }) => {
  const [user, setUser] = useState(USER_DEFAULT_STATE);

  const updateUser = (newUser) => {
    if (newUser.id) {
      setUser({ loggedIn: true, user: newUser });
    } else {
      setUser({ loggedIn: false, user: null });
    }
  }

  return (
    <UserContext.Provider
      value={{ user, updateUser }}>
      {children}
    </UserContext.Provider>
  );
}

export default UserProvider;
