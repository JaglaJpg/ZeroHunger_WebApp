'use client';

import { useEffect, useState } from 'react';

function MyFridge({ isOpen, setIsOpen }) {
  const [foodItems, setFoodItems] = useState([]);
  const [selectedFood, setSelectedFood] = useState(null);
  const [showAddForm, setShowAddForm] = useState(false);
  const [newFood, setNewFood] = useState({ foodName: '', expiration: '' });
  const [refreshTrigger, setRefreshTrigger] = useState(false);

  useEffect(() => {
    if (isOpen) {
      fetchFoodData();
    }
  }, [isOpen, refreshTrigger]);

  useEffect(() => {
    if (!isOpen) {
      setSelectedFood(null);
    }
  }, [isOpen]);

  const fetchFoodData = () => {
    fetch('http://localhost:8080/user/getFood', {
      method: 'GET',
      credentials: 'include',
    })
      .then((response) => response.json())
      .then((data) => {
        setFoodItems([...data]);
      })
      .catch((error) => console.error('Error fetching food:', error));
  };

  const addFood = () => {
    fetch('http://localhost:8080/user/addFood', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify(newFood),
    })
      .then((response) => {
        if (!response.ok) throw new Error('Failed to add food');
        return response.json();
      })
      .then(() => {
        setNewFood({ foodName: '', expiration: '' });
        setShowAddForm(false);
        setRefreshTrigger((prev) => !prev);
      })
      .catch((error) => console.error('Error adding food:', error));
  };

  const markFood = (foodID, status) => {
    fetch('http://localhost:8080/user/markFood', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({ foodID, status }),
    })
      .then((response) => {
        if (!response.ok) return response.text().then((text) => { throw new Error(text); });
        return response.json().catch(() => null);
      })
      .then(() => {
        setSelectedFood(null);
        fetchFoodData();
      })
      .catch((error) => console.error('Error marking food:', error));
  };

  if (!isOpen) return null;

  return (
    <div
  className='fixed inset-0 flex items-center justify-center z-50'
  style={{
    backgroundColor: 'rgba(204, 111, 5, 0.3)',
    backdropFilter: 'blur(8px)',
    WebkitBackdropFilter: 'blur(8px)',
  }}
>

      <div className='w-[320px] h-[460px] bg-white rounded-xl shadow-lg p-4 flex flex-col items-center justify-between relative border-2 border-emerald-500'>
        <button
          onClick={() => setIsOpen(false)}
          className='absolute top-2 right-3 text-xl text-gray-500 hover:text-red-500'
        >
          ❌
        </button>

        <h2 className='text-lg font-bold text-emerald-800'>My Fridge</h2>

        <div className='w-full h-[70%] overflow-y-auto border-t border-b border-gray-300 mt-2 pt-2 grid grid-cols-2 gap-3'>
          {foodItems.map((food) => {
            const today = new Date();
            today.setHours(0, 0, 0, 0);
            const expirationDate = new Date(food.expiration);
            expirationDate.setHours(0, 0, 0, 0);
            const timeDiff = expirationDate - today;
            const daysRemaining = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
            const isExpiringSoon = daysRemaining <= 1 && daysRemaining >= 0;
            const isExpired = daysRemaining < 0;

            return (
              <div
                key={food.foodID}
                onClick={() => setSelectedFood(food)}
                className={`p-2 rounded-lg text-xs text-center cursor-pointer shadow-md transition-transform duration-200 ${
                  isExpired
                    ? 'bg-gray-300 text-gray-600'
                    : isExpiringSoon
                    ? 'bg-red-500 text-white'
                    : 'bg-white text-gray-800'
                }`}
              >
                <strong>{food.foodName}</strong>
                <br />
                <span className='text-[10px] text-gray-600'>Exp: {food.expiration}</span>
              </div>
            );
          })}
        </div>

        {selectedFood && (
          <div className='absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-64 bg-white rounded-lg shadow-xl p-4 z-50 border'>
            <button
              onClick={() => setSelectedFood(null)}
              className='absolute top-2 right-3 text-red-500 text-xl'
            >
              ❌
            </button>
            <h3 className='text-lg font-semibold'>{selectedFood.foodName}</h3>
            <p className='text-sm text-gray-600'>Expiration Date: {selectedFood.expiration}</p>
            <div className='flex gap-2 mt-4'>
              <button
                onClick={() => markFood(selectedFood.foodID, 'USED')}
                className='bg-emerald-600 text-white px-3 py-1 rounded-md text-sm'
              >
                ✅ Used
              </button>
              <button
                onClick={() => markFood(selectedFood.foodID, 'WASTED')}
                className='bg-red-600 text-white px-3 py-1 rounded-md text-sm'
              >
                ❌ Wasted
              </button>
            </div>
          </div>
        )}

        <button
          onClick={() => setShowAddForm(true)}
          className='mt-2 bg-emerald-500 hover:bg-emerald-600 text-white px-4 py-1 rounded-md text-sm'
        >
          ➕ Add Food
        </button>
      </div>

      {showAddForm && (
        <div
        className='fixed inset-0 flex items-center justify-center z-50'
        style={{
          backgroundColor: 'rgba(204, 111, 5, 0.3)',
          backdropFilter: 'blur(8px)',
          WebkitBackdropFilter: 'blur(8px)',
        }}
      >
      
          <div className='bg-white p-4 rounded-lg shadow-lg w-72'>
            <h3 className='text-lg font-semibold mb-2'>Add Food</h3>
            <input
              type='text'
              placeholder='Food Name'
              value={newFood.foodName}
              onChange={(e) => setNewFood({ ...newFood, foodName: e.target.value })}
              className='w-full mb-2 px-2 py-1 border border-gray-300 rounded'
            />
            <input
              type='date'
              value={newFood.expiration}
              onChange={(e) => setNewFood({ ...newFood, expiration: e.target.value })}
              className='w-full mb-3 px-2 py-1 border border-gray-300 rounded'
            />
            <div className='flex gap-2'>
              <button
                onClick={addFood}
                className='flex-1 bg-blue-600 text-white px-3 py-1 rounded'
              >
                ✅ Add
              </button>
              <button
                onClick={() => setShowAddForm(false)}
                className='flex-1 bg-gray-400 text-white px-3 py-1 rounded'
              >
                ❌ Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default MyFridge;
