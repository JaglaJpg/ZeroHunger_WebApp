import React, { useState, useEffect } from "react";

const FoodListing = () => {
  const [foodItems, setFoodItems] = useState([]);
  const [foodName, setFoodName] = useState("");
  const [expirationDate, setExpirationDate] = useState("");
  const [foodTypes, setFoodTypes] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [bankOptions, setBankOptions] = useState([]);
  const [selectedBank, setSelectedBank] = useState("");
  const [selectedBankInfo, setSelectedBankInfo] = useState(null);

  const fetchListings = () => {
    fetch("http://localhost:8080/listings", {
      credentials: "include",
    })
      .then((res) => res.json())
      .then((data) => setFoodItems(data))
      .catch((error) => console.error("Error fetching food items:", error));
  };

  const fetchBankOptions = () => {
    fetch("http://localhost:8080/donations/bankOptions", {
      credentials: "include",
    })
      .then((res) => res.json())
      .then((data) => {
        setBankOptions(data);
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

  const handleSubmit = async (e) => {
    e.preventDefault();
    const newFoodItem = {
      foodName,
      expirationDate,
      foodType: foodTypes.map((ft) => ft.toUpperCase()),
      foodBankID: selectedBank,
    };

    try {
      const response = await fetch("http://localhost:8080/listings/AddFood", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(newFoodItem),
      });

      if (!response.ok) throw new Error("Failed to add food");
      alert("Food added successfully!");

      setFoodName("");
      setExpirationDate("");
      setFoodTypes([]);
      setShowForm(false);
      fetchListings();
    } catch (error) {
      console.error("Add food error:", error);
      alert("Error: Failed to add food");
    }
  };

  const handleClaim = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/listings/claim/${id}`, {
        method: "PUT",
        credentials: "include",
      });

      if (!response.ok) throw new Error("Failed to claim food");
      alert("Food claimed!");
      fetchListings();
    } catch (error) {
      console.error("Claim error:", error);
      alert("Error claiming food item.");
    }
  };

  const openBankModal = (bank) => setSelectedBankInfo(bank);
  const closeBankModal = () => setSelectedBankInfo(null);

  return (
    <div className="min-h-screen bg-gradient-to-t from-[#cd5757] to-[#dfb7c3] text-gray-900 flex flex-col items-center p-6 font-[Poppins]">
      <h1 className="text-5xl font-bold text-center mb-8 text-white">Food Listings</h1>

      {showForm && (
        <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-lg mx-auto mb-10 transition-transform transform hover:scale-105 hover:shadow-xl">
          <h2 className="text-3xl font-bold mb-5 text-[#cd5757]">Add Food Item</h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block font-semibold mb-1 text-[#cd5757]">Food Name:</label>
              <input
                type="text"
                value={foodName}
                onChange={(e) => setFoodName(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#cd5757]"
                required
              />
            </div>

            <div>
              <label className="block font-semibold mb-1 text-[#cd5757]">Expiration Date:</label>
              <input
                type="date"
                value={expirationDate}
                onChange={(e) => setExpirationDate(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#cd5757]"
                required
              />
            </div>

            <div>
              <label className="block font-semibold mb-1 text-[#cd5757]">Food Type:</label>
              <select
                multiple
                value={foodTypes}
                onChange={(e) =>
                  setFoodTypes([...e.target.selectedOptions].map((o) => o.value))
                }
                className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#cd5757]"
              >
                <option value="Vegan">Vegan</option>
                <option value="Halal">Halal</option>
                <option value="Vegetarian">Vegetarian</option>
                <option value="Nut_Free">Nut Free</option>
                <option value="EGG_FREE">Egg Free</option>
              </select>
            </div>

            <div>
              <label className="block font-semibold mb-1 text-[#cd5757]">Pickup Location:</label>
              <select
                value={selectedBank}
                onChange={(e) => setSelectedBank(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#cd5757]"
                required
              >
                {bankOptions.map((bank) => (
                  <option key={bank.bankID} value={bank.bankID}>
                    {bank.name.length > 30 ? `${bank.name.slice(0, 30)}...` : bank.name} ({Math.round(bank.distance)} km)
                  </option>
                ))}
              </select>
            </div>

            <button
              type="submit"
              className="w-full bg-[#cd5757] text-white py-3 rounded-md font-semibold hover:bg-[#b84b4b] transition"
            >
              Submit Donation
            </button>
          </form>
        </div>
      )}

      {!showForm && (
        <button
          onClick={() => setShowForm(true)}
          className="mb-10 bg-white text-[#cd5757] px-6 py-3 rounded-md font-semibold hover:bg-gray-100 transition"
        >
          Add Another Food Item
        </button>
      )}

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 w-full max-w-6xl">
        {foodItems.length > 0 ? (
          foodItems.map((food) => (
            <div key={food.id} className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition">
              <h3 className="text-xl font-bold text-[#cd5757] mb-2">{food.foodName}</h3>
              <p className="text-sm text-gray-700 mb-1">
                <strong>Expiration:</strong> {food.expirationDate}
              </p>
              <p className="text-sm text-gray-700 mb-1">
                <strong>Types:</strong>{" "}
                {food.foodTypes && food.foodTypes.length > 0
                  ? food.foodTypes.join(", ")
                  : "None"}
              </p>

              {food.foodBank && (
                <>
                  <p className="text-sm text-blue-700 mb-1">
                    <strong>Pickup:</strong>{" "}
                    <span
                      className="underline cursor-pointer"
                      title={food.foodBank.name}
                      onClick={() => openBankModal(food.foodBank)}
                    >
                      {food.foodBank.name}
                    </span>
                  </p>
                  <p className="text-sm text-gray-600 mb-3">
                    <strong>Distance:</strong> {Math.round(food.foodBank.distance)} km
                  </p>
                </>
              )}

              {!food.belongs && (
                <button
                  onClick={() => handleClaim(food.id)}
                  disabled={food.claimed}
                  className={`w-full mt-3 py-2 rounded-md font-semibold transition ${food.claimed
                      ? "bg-gray-400 cursor-not-allowed text-white"
                      : "bg-[#cd5757] hover:bg-[#b84b4b] text-white"
                    }`}
                >
                  {food.claimed ? "Claimed" : "Claim Donation"}
                </button>

              )}
            </div>
          ))
        ) : (
          <p className="text-center text-white text-lg">No food available.</p>
        )}
      </div>

      {selectedBankInfo && (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex justify-center items-center z-50">
          <div className="bg-white rounded-lg p-6 shadow-xl max-w-md w-full relative">
            <button
              onClick={closeBankModal}
              className="absolute top-2 right-2 text-gray-600 hover:text-black text-xl"
            >
              &times;
            </button>
            <h2 className="text-2xl font-bold mb-4">{selectedBankInfo.name}</h2>
            <p>
              <strong>Address:</strong> {selectedBankInfo.address}
            </p>
            <p>
              <strong>Distance:</strong> {Math.round(selectedBankInfo.distance)} km
            </p>
          </div>
        </div>
      )}
    </div>
  );
};

export default FoodListing;
