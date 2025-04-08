import { Link, Outlet, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

const Layout = ({ isLoggedIn, setIsLoggedIn }) => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    await fetch("http://localhost:8080/auth/logout", {
      method: "POST",
      credentials: "include",
    });
    setIsLoggedIn(false);
    navigate("/login");
  };

  return (
    <div className="font-[Poppins] bg-gradient-to-t from-[#cd5757] to-[#dfb7c3] min-h-screen">
      <header className="bg-white border-b-2 border-black">
        <nav className="flex justify-between items-center max-w-[1200px] w-full mx-auto px-4 py-6">
          <div className="flex items-center">
            {/* Shrink the logo so it doesn't dominate the navbar */}
            <img className="w-24 h-24" src="/planet-earth.png" alt="Earth Logo" />
            {/* Make the heading a bit smaller if desired */}
            <h1 className="ml-2 text-xl font-bold text-black">ZERO HUNGER</h1>
          </div>

          {/* Reduce gap to something smaller than 4vw, like gap-4 or gap-6 */}
          <ul className="flex items-center gap-4 text-blue-600 font-semibold">
            <li><Link to="/">HOME</Link></li>
            <li><Link to="/clothing">Clothing</Link></li>
            <li><Link to="/appliances">Appliances</Link></li>
            <li><Link to="/food">Food</Link></li>
            <li><Link to="/donation-tracker">Donation Tracker</Link></li>

            {!isLoggedIn ? (
              <>
                <li><Link to="/login">Login</Link></li>
                <li><Link to="/register">Register</Link></li>
              </>
            ) : (
              <li>
                <button
                  onClick={handleLogout}
                  className="text-red-600 hover:text-gray-500"
                >
                  Logout
                </button>
              </li>
            )}
          </ul>
        </nav>
      </header>



      <Outlet />
    </div>
  );
};


export default Layout;
