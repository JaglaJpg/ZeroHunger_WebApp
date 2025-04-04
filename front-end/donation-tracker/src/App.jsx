import { useState } from 'react'
import './App.css'
import React from "react";
import FoodListing from "./components/FoodListing";

function App() {
  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <FoodListing />
    </div>
  );
}

export default App