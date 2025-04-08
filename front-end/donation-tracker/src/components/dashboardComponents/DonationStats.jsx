// Updated DonationStats.jsx
import { ShoppingBag, Utensils, Shirt, Package } from 'lucide-react';

export function DonationStats({ data }) {
  return (
    <div className='space-y-6'>
      <div className='rounded-lg bg-amber-50 p-4'>
        <div className='flex items-center gap-3'>
          <ShoppingBag className='h-6 w-6 text-amber-600' />
          <div>
            <span className='text-sm font-medium text-amber-600'>Total Donations</span>
            <span className='mt-1 block text-2xl font-bold text-amber-700'>
              {data.totalDonations} items
            </span>
          </div>
        </div>
      </div>

      <div className='grid grid-cols-2 gap-4'>
        <StatCard label='Food Donated' icon={<Utensils className='h-5 w-5 text-emerald-600' />} value={data.foodDonated} color='emerald' />
        <StatCard label='Clothes Donated' icon={<Shirt className='h-5 w-5 text-blue-600' />} value={data.clothesDonated} color='blue' />
        <StatCard label='Appliances Donated' icon={<Package className='h-5 w-5 text-purple-600' />} value={data.appliancesDonated} color='purple' />
      </div>
    </div>
  );
}

function StatCard({ label, icon, value, color }) {
  return (
    <div className={`rounded-lg bg-${color}-50 p-4`}>
      <div className='flex items-center gap-2'>
        {icon}
        <span className={`text-sm font-medium text-${color}-600`}>{label}</span>
      </div>
      <span className={`mt-1 block text-2xl font-bold text-${color}-700`}>{value}</span>
    </div>
  );
}
