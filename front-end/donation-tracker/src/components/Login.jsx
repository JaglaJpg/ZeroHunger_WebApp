import React, { useState } from "react";

const Login = () => {
  const [form, setForm] = useState({ email: "", password: "" });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const validateEmail = (email) => {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(email);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateEmail(form.email)) {
      alert("Please enter a valid email address.");
      return;
    }

    if (form.password.length < 8) {
      alert("Password must be at least 8 characters long.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/auth/signin", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(form),
      });

      const result = await response.text();

      if (response.ok) {
        localStorage.setItem("userEmail", form.email);
        alert("✅ Login successful!");
        window.location.href = "http://127.0.0.1:5502/front-end/home.html";
      } else {
        alert("❌ " + result);
      }
    } catch (err) {
      console.error("Login error:", err);
      alert("⚠️ An error occurred. Please try again later.");
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-t from-[#cd5757] to-[#dfb7c3] font-[Poppins]">
      <header className="bg-white border-b-2 border-black py-4">
        <nav className="flex justify-between items-center w-[92%] mx-auto">
          <div className="flex items-center gap-3 text-2xl font-bold">
            <img className="w-16" src="planet-earth.png" alt="..." />
            <h1>Hand To Heart</h1>
          </div>
          <ul className="flex items-center gap-[4vw]">
            <li><a className="hover:text-gray-500" href="#">HOME</a></li>
            <li><a className="hover:text-gray-500" href="#">CONTACT US</a></li>
            <li><a className="hover:text-gray-500" href="#">ABOUT</a></li>
            <li><a className="hover:text-gray-500" href="#">VISION</a></li>
            <li><a className="hover:text-gray-500" href="#">LOGIN</a></li>
          </ul>
          <button className="bg-[#cd5757] text-white px-5 py-2 rounded hover:bg-[#797171]">REGISTER</button>
        </nav>
      </header>

      <div className="flex items-center justify-center min-h-[calc(100vh-5rem)] px-4">
        <div className="bg-white shadow-md rounded-xl p-8 w-full max-w-md">
          <h2 className="text-3xl font-bold text-center text-[#cd5757] mb-6">Login</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-2">
                E-mail
              </label>
              <input
                type="email"
                id="email"
                name="email"
                required
                placeholder="Enter your E-mail"
                value={form.email}
                onChange={handleChange}
                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-[#cd5757]"
              />
            </div>

            <div className="mb-6">
              <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-2">
                Password
              </label>
              <input
                type="password"
                id="password"
                name="password"
                required
                placeholder="Enter your password"
                value={form.password}
                onChange={handleChange}
                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-[#cd5757]"
              />
            </div>

            <div className="flex justify-center">
              <button
                type="submit"
                className="bg-[#cd5757] text-white px-6 py-2 rounded-lg hover:bg-[#797171] transition duration-200"
              >
                Login
              </button>
            </div>
          </form>

          <div className="mt-4 text-center">
            <p className="text-sm text-gray-600">
              Don't have an account?{" "}
              <a href="#" className="text-[#cd5757] underline">
                Register
              </a>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
