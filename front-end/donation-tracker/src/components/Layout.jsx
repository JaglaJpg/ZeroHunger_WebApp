import { Link, Outlet, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

const Layout = () => {
  const [loggedIn, setLoggedIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const res = await fetch("http://localhost:8080/donations/fetchDonations", {
          credentials: "include",
        });
        if (res.ok) setLoggedIn(true);
        else setLoggedIn(false);
      } catch {
        setLoggedIn(false);
      }
    };

    checkAuth();
  }, []);

  const handleLogout = async () => {
    await fetch("http://localhost:8080/auth/logout", {
      method: "POST",
      credentials: "include",
    });
    setLoggedIn(false);
    navigate("/login");
  };

  return (
    <div className="font-[Poppins] bg-gradient-to-t from-[#cd5757] to-[#dfb7c3] min-h-screen">
      <header className="bg-white border-b-2 border-black">
        <nav className="flex justify-between items-center w-[92%] mx-auto py-4">
          <div className="flex items-center text-2xl font-bold">
            <img className="w-16" src="/planet-earth.png" alt="Earth Logo" />
            <h1 className="ml-2">ZERO HUNGER</h1>
          </div>
          <ul className="flex items-center gap-[4vw] text-blue-600 font-semibold">
            <li><Link to="/">HOME</Link></li>
            <li><Link to="/clothing">Clothing</Link></li>
            <li><Link to="/appliances">Appliances</Link></li>
            <li><Link to="/food">Food</Link></li>
            <li><Link to="/donation-tracker">Donation Tracker</Link></li>
            {!loggedIn ? (
              <>
                <li><Link to="/login">Login</Link></li>
                <li><Link to="/register">Register</Link></li>
              </>
            ) : (
              <li>
                <button onClick={handleLogout} className="text-red-600 hover:text-gray-500">
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
