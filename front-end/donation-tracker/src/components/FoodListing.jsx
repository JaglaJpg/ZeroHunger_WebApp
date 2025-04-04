import React, { useState, useEffect } from "react";

const FoodListing = () => {
  const [foodItems, setFoodItems] = useState([]);
  const [foodName, setFoodName] = useState("");
  const [expirationDate, setExpirationDate] = useState("");
  const [foodTypes, setFoodTypes] = useState([]);
  const [showForm, setShowForm] = useState(true);

  // New state for bank options and selected bank
  const [bankOptions, setBankOptions] = useState([]);
  const [selectedBank, setSelectedBank] = useState("");

  // Fetch food items from backend
  const fetchListings = () => {
    fetch("http://localhost:8080/listings")
      .then((res) => res.json())
      .then((data) => setFoodItems(data))
      .catch((error) => console.error("Error fetching food items:", error));
  };

  // Fetch bank options from backend
  const fetchBankOptions = () => {
    fetch("http://localhost:8080/donations/bankOptions")
      .then((res) => res.json())
      .then((data) => {
        setBankOptions(data);
        // Optionally, preselect the first option if available
        if (data.length > 0) {
          setSelectedBank(data[0].bankID);
        }
      })
      .catch((error) => console.error("Error fetching bank options:", error));
  };

  useEffect(() => {
    fetchListings();
    fetchBankOptions();
  }, []);

  // Handle form submission (POST request)
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Build new food item with the selected bank option as foodBankID
    const newFoodItem = {
      foodName,
      expirationDate,
      foodType: foodTypes.map((ft) => ft.toUpperCase()),
      foodBankID: selectedBank, // send the bank ID
    };

    try {
      const response = await fetch("http://localhost:8080/listings/AddFood", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newFoodItem),
      });

      if (!response.ok) throw new Error("Failed to add food");

      alert("Food added successfully!");
      setFoodName("");
      setExpirationDate("");
      setFoodTypes([]);
      setShowForm(false);
      fetchListings(); // Refresh list
    } catch (error) {
      console.error("Add food error:", error);
      alert("Error: Failed to add food");
    }
  };

  // Handle claim food item (PUT request)
  const handleClaim = async (id) => {
    try {
      const response = await fetch(
        `http://localhost:8080/listings/claim/${id}`,
        {
          method: "PUT",
        }
      );

      if (!response.ok) throw new Error("Failed to claim food");

      alert("Food claimed!");
      fetchListings(); // Refresh after claim
    } catch (error) {
      console.error("Claim error:", error);
      alert("Error claiming food item.");
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 text-gray-900 flex flex-col items-center p-6">
      <h1 className="text-5xl font-bold text-center mb-8">🍽️ Food Listings</h1>

      {/* Form to Add Food */}
      {showForm && (
        <div className="bg-white p-8 rounded-xl shadow-md w-full max-w-lg mx-auto mb-10 transition-transform transform hover:scale-105 hover:shadow-xl">
          <h2 className="text-3xl font-bold mb-5">➕ Add Food Item</h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block font-semibold mb-1">Food Name:</label>
              <input
                type="text"
                value={foodName}
                onChange={(e) => setFoodName(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                required
              />
            </div>
            <div>
              <label className="block font-semibold mb-1">
                Expiration Date:
              </label>
              <input
                type="date"
                value={expirationDate}
                onChange={(e) => setExpirationDate(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                required
              />
            </div>
            <div>
              <label className="block font-semibold mb-1">
                Food Type (Allergens):
              </label>
              <select
                multiple
                value={foodTypes}
                onChange={(e) =>
                  setFoodTypes(
                    [...e.target.selectedOptions].map((o) => o.value)
                  )
                }
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
              >
                <option value="Vegan">Vegan</option>
                <option value="Halal">Halal</option>
                <option value="Vegetarian">Vegetarian</option>
                <option value="Nut_Free">Nut Free</option>
                <option value="EGG_FREE">Egg Free</option>
              </select>
            </div>
            <div>
              <label className="block font-semibold mb-1">
                Pickup Location:
              </label>
              <select
                value={selectedBank}
                onChange={(e) => setSelectedBank(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                required
              >
                {bankOptions.map((bank) => (
                  <option key={bank.bankID} value={bank.bankID}>
                    {bank.name} ({Math.round(bank.distance)} km)
                  </option>
                ))}
              </select>
            </div>
            <button
              type="submit"
              className="w-full bg-blue-500 text-white py-3 rounded-lg font-semibold hover:bg-blue-600 transition"
            >
              ➕ Add Food
            </button>
          </form>
        </div>
      )}

      {!showForm && (
        <button
          onClick={() => setShowForm(true)}
          className="mb-10 bg-green-500 text-white px-6 py-3 rounded-lg font-semibold hover:bg-green-600 transition"
        >
          ➕ Add Another Food Item
        </button>
      )}

      {/* Food Items Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 w-full max-w-6xl">
        {foodItems.length > 0 ? (
          foodItems.map((food) => (
            <div
              key={food.id}
              className="bg-white border rounded-xl shadow-sm p-6 transition hover:shadow-lg"
            >
              <h3 className="text-xl font-semibold mb-2">{food.foodName}</h3>
              <p className="text-sm text-gray-700 mb-2">
                <strong>Expiration:</strong> {food.expirationDate}
              </p>
              <p className="text-sm text-gray-700 mb-4">
                <strong>Types:</strong>{" "}
                {food.foodTypes && food.foodTypes.length > 0
                  ? food.foodTypes.join(", ")
                  : "None"}
              </p>
              <button
                onClick={() => handleClaim(food.id)}
                disabled={food.claimed}
                className={`w-full py-2 px-4 rounded-lg font-semibold transition ${
                  food.claimed
                    ? "bg-gray-300 cursor-not-allowed"
                    : "bg-green-500 hover:bg-green-600 text-white"
                }`}
              >
                {food.claimed ? "✅ Claimed" : "🤝 Claim Donation"}
              </button>
            </div>
          ))
        ) : (
          <p className="text-center text-lg text-gray-500">
            ❌ No food available.
          </p>
        )}
      </div>
    </div>
  );
};

export default FoodListing;
