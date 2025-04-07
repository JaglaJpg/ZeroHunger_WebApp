// src/context/AuthContext.jsx
import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(null); // null = unknown
  const [message, setMessage] = useState("");

  const checkAuth = async () => {
    try {
      const res = await fetch("http://localhost:8080/donations/fetchDonations", {
        credentials: "include"
      });
      if (res.status === 403) {
        setIsLoggedIn(false);
      } else if (res.ok) {
        setIsLoggedIn(true);
      }
    } catch (err) {
      setIsLoggedIn(false);
    }
  };

  useEffect(() => {
    checkAuth();
  }, []);

  return (
    <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn, message, setMessage }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
