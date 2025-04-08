// Updated DonationsByCategory.jsx (with zero filter fix)
'use client';

import {
  Cell,
  Legend,
  Pie,
  PieChart,
  ResponsiveContainer,
  Tooltip,
} from '@/components/ui/chart';

const COLORS = ['#10b981', '#3b82f6', '#8b5cf6'];

export function DonationsByCategory({ data }) {
  const chartData = [
    { name: 'Food', value: data.foodDonated },
    { name: 'Clothes', value: data.clothesDonated },
    { name: 'Appliances', value: data.appliancesDonated },
  ].filter(entry => entry.value > 0); // ✅ Only include non-zero values

  return (
    <ResponsiveContainer width='100%' height={300}>
      <PieChart>
        <Pie
          data={chartData}
          cx='50%'
          cy='50%'
          labelLine={false}
          outerRadius={100}
          innerRadius={60}
          fill='#8884d8'
          dataKey='value'
          label={({ name, percent }) =>
            percent > 0 ? `${name} ${(percent * 100).toFixed(0)}%` : ''
          }
          paddingAngle={2}
        >
          {chartData.map((entry, index) => (
            <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
          ))}
        </Pie>
        <Tooltip
          contentStyle={{
            backgroundColor: 'white',
            borderRadius: '8px',
            border: 'none',
            boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
          }}
        />
        <Legend />
      </PieChart>
    </ResponsiveContainer>
  );
}
