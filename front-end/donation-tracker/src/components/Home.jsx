import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div className="font-[Poppins] bg-gradient-to-t from-[#cd5757] to-[#dfb7c3] min-h-screen">
      {/* MAIN CONTENT */}
      <main className="w-[92%] mx-auto pt-10">
        <h2 className="text-3xl font-bold text-center text-white mb-8">
          Find or Donate Essentials
        </h2>

        {/* Scrollable Container */}
        <div className="flex gap-6 overflow-x-auto pb-4 scrollbar-hide">
          {/* Food Donations */}
          <Link
            to="/food"
            className="min-w-[300px] md:min-w-[400px] bg-white shadow-lg rounded-lg overflow-hidden hover:shadow-2xl transition"
          >
            <img
              src="food d.jpg"
              alt="Food Donations"
              className="w-full h-56 object-cover"
            />
            <div className="p-5">
              <h2 className="text-xl font-bold">Food Donations</h2>
              <p className="text-gray-600 mt-2">
                Help provide meals for those in need. Donate or collect fresh
                and packaged food items.
              </p>
            </div>
          </Link>

          {/* Clothing Donations */}
          <Link
            to="/clothing"
            className="min-w-[300px] md:min-w-[400px] bg-white shadow-lg rounded-lg overflow-hidden hover:shadow-2xl transition"
          >
            <img
              src="clothes d.jpg"
              alt="Clothing Donations"
              className="w-full h-56 object-cover"
            />
            <div className="p-5">
              <h2 className="text-xl font-bold">Clothing Donations</h2>
              <p className="text-gray-600 mt-2">
                Donate wearable clothes to help those in need. Search for
                available items and contribute.
              </p>
            </div>
          </Link>

          {/* Electrical Appliances */}
          <Link
            to="/appliances"
            className="min-w-[300px] md:min-w-[400px] bg-white shadow-lg rounded-lg overflow-hidden hover:shadow-2xl transition"
          >
            <img
              src="applianes d.jpg"
              alt="Electrical Appliances"
              className="w-full h-56 object-cover"
            />
            <div className="p-5">
              <h2 className="text-xl font-bold">Electrical Appliances</h2>
              <p className="text-gray-600 mt-2">
                Find or donate working electrical appliances like fans, heaters,
                and kitchen tools.
              </p>
            </div>
          </Link>

          {/* Dashboard Card (Tracker Page) */}
          <Link
            to="/tracker"
            className="min-w-[300px] md:min-w-[400px] bg-white shadow-lg rounded-lg overflow-hidden hover:shadow-2xl transition"
          >
            <img
              src="dashboard.jpg"
              alt="Dashboard"
              className="w-full h-56 object-cover"
            />
            <div className="p-5">
              <h2 className="text-xl font-bold">Your Dashboard</h2>
              <p className="text-gray-600 mt-2">
                Access your donation history, manage listings, and track impact
                all in one place.
              </p>
            </div>
          </Link>
        </div>
      </main>

      {/* Inline style for hiding scrollbar */}
      <style>{`
        .scrollbar-hide::-webkit-scrollbar { display: none; }
        .scrollbar-hide { -ms-overflow-style: none; scrollbar-width: none; }
      `}</style>
    </div>
  );
};

export default Home;
