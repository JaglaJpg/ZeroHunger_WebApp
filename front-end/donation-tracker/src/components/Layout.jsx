// src/components/Layout.jsx
import React from "react";
import { Link, Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <div className="font-[Poppins] bg-gradient-to-t from-[#cd5757] to-[#dfb7c3] min-h-screen">
      <header className="bg-white border-b-2 border-black">
        <nav className="flex justify-between items-center w-[92%] mx-auto py-4">
          <div className="flex items-center text-2xl font-bold">
            <img className="w-16" src="/planet-earth.png" alt="Earth Logo" />
            <h1 className="ml-2">ZERO HUNGER</h1>
          </div>
          <ul className="flex flex-wrap items-center gap-6 text-blue-600 font-semibold text-sm md:text-base">
            <li><Link className="hover:text-gray-500" to="/">HOME</Link></li>
            <li><Link className="hover:text-gray-500" to="/clothing">Clothing</Link></li>
            <li><Link className="hover:text-gray-500" to="/appliances">Appliances</Link></li>
            <li><Link className="hover:text-gray-500" to="/food">Food</Link></li>
            <li><Link className="hover:text-gray-500" to="/donation-tracker">Donation Tracker</Link></li>
            <li><Link className="hover:text-gray-500" to="/login">Login</Link></li>
            <li><Link className="hover:text-gray-500" to="/register">Register</Link></li>
          </ul>
        </nav>
      </header>

      <Outlet /> {/* Render nested pages here */}
    </div>
  );
};

export default Layout;
