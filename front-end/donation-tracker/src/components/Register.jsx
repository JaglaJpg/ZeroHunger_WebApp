import React, { useState } from "react";

const calculateAge = (dobString) => {
  const today = new Date();
  const dob = new Date(dobString);

  let age = today.getFullYear() - dob.getFullYear();
  const hasHadBirthdayThisYear =
    today.getMonth() > dob.getMonth() ||
    (today.getMonth() === dob.getMonth() && today.getDate() >= dob.getDate());

  if (!hasHadBirthdayThisYear) {
    age--;
  }

  return age;
};

const Register = () => {
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    dob: "",
    email: "",
    password: "",
    confirmPassword: "",
    address: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const age = calculateAge(form.dob);
    if (age < 16) {
      alert("You must be at least 16 years old to register.");
      window.scrollTo(0, 0);
      return;
    }

    if (form.password !== form.confirmPassword) {
      alert("Passwords do not match.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/auth/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(form),
      });

      if (response.ok) {
        alert("Registration successful!");
        setForm({
          firstName: "",
          lastName: "",
          dob: "",
          email: "",
          password: "",
          confirmPassword: "",
          address: "",
        });
      } else {
        const text = await response.text();
        alert(`Registration failed: ${text}`);
      }
    } catch (error) {
      console.error("Error:", error);
      alert("Something went wrong during registration.");
    }
  };

  return (
    <div className="min-h-screen w-full bg-gradient-to-t from-[#cd5757] to-[#dfb7c3] flex items-center justify-center px-4">
      <div className="w-full max-w-xl bg-white rounded-2xl shadow-xl p-10">
        <h2 className="text-3xl font-bold text-center text-[#cd5757] mb-8">
          Register
        </h2>

        <form onSubmit={handleSubmit} className="space-y-4">
          {[
            { label: "First Name", name: "firstName", type: "text" },
            { label: "Last Name", name: "lastName", type: "text" },
            { label: "Date of Birth", name: "dob", type: "date", extraClass: "text-gray-500" },
            { label: "Email", name: "email", type: "email" },
            { label: "Password", name: "password", type: "password" },
            { label: "Confirm Password", name: "confirmPassword", type: "password" },
            { label: "Address", name: "address", type: "text" },
          ].map((input) => (
            <div key={input.name}>
              <label
                htmlFor={input.name}
                className="block text-base font-medium text-gray-700 mb-1"
              >
                {input.label}
              </label>
              <input
                type={input.type}
                name={input.name}
                id={input.name}
                required
                value={form[input.name]}
                onChange={handleChange}
                className={`w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#cd5757] ${input.extraClass || ""}`}
                style={input.type === "date" ? { colorScheme: "light" } : {}}
              />
            </div>
          ))}

          <button
            type="submit"
            className="w-full bg-[#cd5757] text-white py-3 px-4 rounded-lg hover:bg-[#b84b4b] transition duration-200"
          >
            Register
          </button>
        </form>
      </div>
    </div>
  );
};

export default Register;
