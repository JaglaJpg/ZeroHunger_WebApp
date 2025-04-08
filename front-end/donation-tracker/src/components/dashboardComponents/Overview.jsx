// Unvaulted Overview.jsx (mocked monthly data for now)
'use client';

import {
  Bar,
  BarChart,
  CartesianGrid,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from '@/components/ui/chart';

export function Overview({ data = [] }) {
  const chartData = data.length > 0 ? data : [
    { name: 'Jan', Saved: 400, Wasted: 100 },
    { name: 'Feb', Saved: 420, Wasted: 90 },
    { name: 'Mar', Saved: 450, Wasted: 85 },
    { name: 'Apr', Saved: 470, Wasted: 75 },
    { name: 'May', Saved: 500, Wasted: 70 },
    { name: 'Jun', Saved: 530, Wasted: 60 },
  ];

  return (
    <ResponsiveContainer width='100%' height={300}>
      <BarChart data={chartData}>
        <CartesianGrid strokeDasharray='3 3' stroke='#f0f0f0' />
        <XAxis dataKey='name' tick={{ fill: '#666' }} />
        <YAxis tick={{ fill: '#666' }} />
        <Tooltip
          contentStyle={{
            backgroundColor: 'white',
            borderRadius: '8px',
            border: 'none',
            boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
          }}
        />
        <Bar dataKey='Saved' fill='#10b981' radius={[4, 4, 0, 0]} />
        <Bar dataKey='Wasted' fill='#ef4444' radius={[4, 4, 0, 0]} />
      </BarChart>
    </ResponsiveContainer>
  );
}