import React, { useState, useEffect } from 'react';
import './DonationLanding.css';

function DonationLanding() {
  const [donations, setDonations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [userType, setUserType] = useState('donor');

  useEffect(() => {
    const fetchDonations = async () => {
      try {
        setLoading(true);
        const response = await fetch('http://localhost:8080/donations/fetchDonations', {
          method: 'GET',
          credentials: 'include',
        });

        if (!response.ok) {
          throw new Error(`Fetching donations failed with status: ${response.status}`);
        }

        const data = await response.json();
        setDonations(data);
        setError(null);
      } catch (err) {
        console.error('Error fetching donations:', err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchDonations();
  }, []);

  const updateDonationStatus = async (id, newStatus) => {
    try {
      const response = await fetch('http://localhost:8080/donations/updateStatus', {
        method: 'PATCH',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          donationId: id,
          status: newStatus
        })
      });

      if (!response.ok) {
        throw new Error(`Status update failed with status: ${response.status}`);
      }

      const data = await response.json();
      console.log('Update response:', data);

      setDonations(donations.map(donation => 
        donation.donationId === id ? { ...donation, status: newStatus } : donation
      ));
    } catch (err) {
      console.error('Error updating donation status:', err);
    }
  };

  if (loading) return <div className="loading">Loading donations...</div>;
  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="App">
      <header className="App-header">
        <h1>Community Sharing Platform</h1>
      </header>

      <main>
        <div className="donation-container">
          <div className="donation-column">
            <h2>Your Donations</h2>
            <div className="donation-box">
              {donations.length > 0 ? (
                donations.map(donation => (
                  <div key={donation.donationId} className="donation-item">
                    <h3>Donation to Food Bank #{donation.foodBankID}</h3>
                    <p><strong>Address:</strong> {donation.address}</p>
                    <p><strong>Status:</strong> {donation.status}</p>

                    <div className="donation-status">
                    <span className={`status-badge ${donation.status?.toLowerCase?.() || 'unknown'}`}>
                        {donation.status}
                      </span>

                      {donation.userType === 'donor' && (
                        <select
                          value={donation.status}
                          onChange={(e) => updateDonationStatus(donation.donationId, e.target.value)}
                        >
                          <option value="PENDING">Pending</option>
                          <option value="ONGOING">Ongoing</option>
                          <option value="DELIVERED">Delivered</option>
                        </select>
                      )}
                    </div>
                  </div>
                ))
              ) : (
                <p>No donation activity found.</p>
              )}
            </div>
          </div>
        </div>
      </main>

      <footer>
        <p>© 2023 Community Sharing Platform. Aligned with UN Sustainable Development Goals.</p>
      </footer>
    </div>
  );
}

export default DonationLanding;
