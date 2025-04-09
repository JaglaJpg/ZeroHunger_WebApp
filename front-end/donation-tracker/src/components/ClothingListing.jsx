import React, { useState, useEffect } from "react";

const ClothingListing = () => {
  const [clothes, setClothes] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [bankOptions, setBankOptions] = useState([]);
  const [selectedBank, setSelectedBank] = useState("");

  const [form, setForm] = useState({
    clothName: "",
    clothSize: "",
    clothGender: "",
    category: "",
    brandName: "",
    condition: "",
    image: null
  });

  useEffect(() => {
    fetch("http://localhost:8080/cloth/listings", {
      credentials: "include",
    })
      .then((res) => res.json())
      .then((data) => setClothes(data))
      .catch((err) => console.error("Fetch clothes error:", err));
  
    fetch("http://localhost:8080/donations/bankOptions", {
      credentials: "include",
    })
      .then((res) => res.json())
      .then((data) => {
        setBankOptions(data);
        if (data.length > 0) setSelectedBank(data[0].bankID);
      })
      .catch((err) => console.error("Bank fetch error:", err));
  }, []);
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    const clothData = { ...form, foodBankID: selectedBank };
  
    const payload = new FormData();
    payload.append(
      "clothData",
      new Blob([JSON.stringify(clothData)], { type: "application/json" })
    );
    if (form.image) payload.append("image", form.image);
  
    try {
      const res = await fetch("http://localhost:8080/cloth/donate", {
        method: "POST",
        body: payload,
        credentials: "include",
      });
      if (!res.ok) throw new Error("Submit failed");
      alert("Clothing donation submitted!");
      setShowForm(false);
      setForm({
        clothName: "",
        clothSize: "",
        clothGender: "",
        category: "",
        brandName: "",
        condition: "",
        image: null,
      });
      const refresh = await fetch("http://localhost:8080/cloth/listings", {
        credentials: "include",
      });
      setClothes(await refresh.json());
    } catch (err) {
      console.error("Submit error:", err);
      alert("Could not submit clothing donation.");
    }
  };
  
  const handleClaim = async (id) => {
    try {
      const res = await fetch(`http://localhost:8080/cloth/claim/${id}`, {
        method: "POST",
        credentials: "include",
      });
      if (!res.ok) throw new Error("Claim failed");
      alert("Clothing item claimed!");
      const refresh = await fetch("http://localhost:8080/cloth/listings", {
        credentials: "include",
      });
      setClothes(await refresh.json());
    } catch (err) {
      console.error("Claim error:", err);
      alert("Failed to claim this item.");
    }
  };
  

  return (
    <div className="min-h-screen bg-gradient-to-t from-[#cd5757] to-[#dfb7c3] p-6 font-[Poppins]">
      {/* <header className="bg-white border-b-2 border-black mb-6 p-4 flex justify-between items-center">
        <div className="flex items-center gap-4 text-xl font-bold text-black">
          <img src="planet-earth.png" alt="Logo" className="w-10" />
          ZERO HUNGER
        </div>
        <nav className="space-x-6 text-black">
          <a href="home.html" className="hover:text-[#cd5757]">Home</a>
          <a href="#" className="hover:text-[#cd5757]">Contact</a>
          <a href="#" className="hover:text-[#cd5757]">About</a>
          <a href="#" className="hover:text-[#cd5757]">Vision</a>
        </nav>
      </header> */}

      <main className="max-w-6xl mx-auto">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl text-black font-bold">Available Cloth Donations</h1>
          <button
  onClick={() => setShowForm(true)}
  className="border border-black text-white px-4 py-2 rounded shadow font-semibold hover:bg-black hover:text-white transition duration-300"
  style={{ border: '1px solid black !important', color: 'black !important' }}>Donate Clothes</button>
        </div>

        {showForm && (
          <form onSubmit={handleSubmit} className="bg-white rounded-lg p-6 shadow mb-10 space-y-4 text-black">
            <input type="text" placeholder="Cloth Name" required value={form.clothName} onChange={(e) => setForm({ ...form, clothName: e.target.value })} className="w-full p-2 border rounded" />
            <input type="text" placeholder="Size (S, M, L, etc)" required value={form.clothSize} onChange={(e) => setForm({ ...form, clothSize: e.target.value })} className="w-full p-2 border rounded" />
            <select required value={form.clothGender} onChange={(e) => setForm({ ...form, clothGender: e.target.value })} className="w-full p-2 border rounded">
              <option value="">Select Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Unisex">Unisex</option>
              <option value="Kids">Kids</option>
            </select>
            <input type="text" placeholder="Category (e.g. Shirt, Pants)" required value={form.category} onChange={(e) => setForm({ ...form, category: e.target.value })} className="w-full p-2 border rounded" />
            <input type="text" placeholder="Brand Name" required value={form.brandName} onChange={(e) => setForm({ ...form, brandName: e.target.value })} className="w-full p-2 border rounded" />
            <select required value={form.condition} onChange={(e) => setForm({ ...form, condition: e.target.value })} className="w-full p-2 border rounded">
              <option value="">Select Condition</option>
              {[1, 2, 3, 4, 5].map(val => (
                <option key={val} value={val}>{val} - {['Poor', 'Fair', 'Good', 'Very Good', 'Like New'][val - 1]}</option>
              ))}
            </select>
            <select value={selectedBank} onChange={(e) => setSelectedBank(e.target.value)} className="w-full p-2 border rounded">
              {bankOptions.map(bank => (
                <option key={bank.bankID} value={bank.bankID} title={bank.name}>
                  {(bank.name.length > 30 ? bank.name.slice(0, 30) + '...' : bank.name)} ({Math.round(bank.distance)} km)
                </option>
              ))}
            </select>
            <input type="file" accept="image/*" onChange={(e) => setForm({ ...form, image: e.target.files[0] })} className="w-full" />
            <button type="submit" className="w-full bg-[#cd5757] text-white py-2 rounded hover:bg-[#b84b4b]">Submit</button>
          </form>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {clothes.length > 0 ? clothes.map((donation) => (
            <div key={donation.id} className="bg-white text-black rounded-lg shadow-md overflow-hidden">
              <img src={donation.imageUrl || "default-cloth.jpg"} alt={donation.clothName} className="w-full h-48 object-cover" />
              <div className="p-4">
                <h3 className="text-xl font-bold mb-2">{donation.clothName}</h3>
                <p className="text-sm text-gray-800"><strong>Size:</strong> {donation.clothSize}</p>
                <p className="text-sm text-gray-800"><strong>Category:</strong> {donation.category}</p>
                {donation.foodBank && (
                  <>
                    <p
                      className="text-sm text-blue-600 underline cursor-help"
                      title={donation.foodBank.address}
                    >
                      {donation.foodBank.name.length > 30
                        ? donation.foodBank.name.slice(0, 30) + "..."
                        : donation.foodBank.name}
                    </p>
                    <p className="text-sm text-gray-700"><strong>Distance:</strong> {Math.round(donation.foodBank.distance)} km</p>
                  </>
                )}
                <button
                onClick={() => handleClaim(donation.id)}
                className="mt-3 w-full border border-black py-2 rounded shadow font-semibold hover:bg-black hover:text-white transition duration-300"
                style={{ border: '1px solid black !important', color: 'black !important' }}>
                  Claim This Donation
                </button>
              </div>
            </div>
          )) : (
            <p className="col-span-full text-black text-xl text-center">No clothing donations available at the moment.</p>
          )}
        </div>
      </main>
    </div>
  );
};

export default ClothingListing;
