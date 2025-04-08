import "./App.css";
import React, { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

import Layout from "./components/Layout";
import Home from "./components/Home";
import Login from "./components/Login";
import Register from "./components/Register";
import ApplianceListing from "./components/ApplianceListing";
import ClothingListing from "./components/ClothingListing";
import FoodListing from "./components/FoodListing";
import DonationLanding from "./components/DonationLanding";
import PrivateRoute from "./components/PrivateRoute";
import DashboardPage from "./components/dashboardComponents/DashboardPage";
const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(null); // null = unknown
  const [loading, setLoading] = useState(true);       // add loading state

  useEffect(() => {
    fetch("http://localhost:8080/auth/validate", {
      method: "GET",
      credentials: "include", // important for cookies
    })
      .then((res) => {
        if (!res.ok) throw new Error("Invalid session");
        setIsLoggedIn(true);
      })
      .catch(() => {
        setIsLoggedIn(false);
      })
      .finally(() => setLoading(false));
  }, []);
  

  const handleForbiddenRedirect = async () => {
    setIsLoggedIn(false);
    window.location.href = "/login";
  };

  window.addEventListener("unhandledrejection", (event) => {
    if (event.reason?.status === 403) handleForbiddenRedirect();
  });

  // 🧠 Hold off rendering until we know login state
  if (loading) return <div>Loading...</div>;

  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={
            isLoggedIn ? <Navigate to="/home" replace /> : <Navigate to="/login" replace />
          }
        />
        <Route element={<Layout isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />}>
          {/* Public */}
          <Route path="/login" element={<Login setIsLoggedIn={setIsLoggedIn} />} />
          <Route path="/register" element={<Register />} />
          <Route path="/home" element={<Home />} />

          {/* Protected */}
          <Route element={<PrivateRoute isLoggedIn={isLoggedIn} />}>
            <Route path="/appliances" element={<ApplianceListing />} />
            <Route path="/clothing" element={<ClothingListing />} />
            <Route path="/food" element={<FoodListing />} />
            <Route path="/donation-tracker" element={<DonationLanding />} />
            <Route path="/tracker" element={<DashboardPage />} />
          </Route>
        </Route>
      </Routes>
    </Router>
  );
};


export default App;