// Updated FoodWasteStats.jsx
export function FoodWasteStats({ data }) {
  const calculateImprovement = (current, previous) => {
    if (!previous || previous === 0) return 0;
    const change = ((previous - current) / previous) * 100;
    return change.toFixed(1);
  };

  const weeklyImprovement = calculateImprovement(data.wastedLastWeek, data.wastedLastMonth / 4);
  const monthlyImprovement = calculateImprovement(data.wastedLastMonth, data.totalWasted);

  return (
    <div className='space-y-6'>
      <div className='grid grid-cols-2 gap-4'>
        <StatCard label='This Week' value={`${data.wastedLastWeek} kg`} color='red' />
        <StatCard label='Last Week' value={`${(data.wastedLastMonth / 4).toFixed(1)} kg`} color='emerald' improvement={weeklyImprovement} />
      </div>

      <div className='grid grid-cols-2 gap-4'>
        <StatCard label='This Month' value={`${data.wastedLastMonth} kg`} color='red' />
        <StatCard label='Total Waste' value={`${data.totalWasted} kg`} color='emerald' improvement={monthlyImprovement} />
      </div>

      <div className='rounded-lg bg-amber-50 p-4'>
        <h4 className='text-sm font-medium text-amber-800'>Waste Reduction Tips</h4>
        <ul className='mt-2 space-y-2 text-sm text-amber-700'>
          <li>📝 Plan meals and create shopping lists</li>
          <li>📦 Store food properly to extend shelf life</li>
          <li>🍲 Use leftovers creatively in new recipes</li>
          <li>❄️ Freeze excess food before it spoils</li>
        </ul>
      </div>
    </div>
  );
}

function StatCard({ label, value, color, improvement }) {
  const isImprovement = improvement > 0;

  return (
    <div className={`rounded-lg bg-${color}-50 p-4`}>
      <span className={`text-sm font-medium text-${color}-600`}>{label}</span>
      <span className={`mt-1 block text-2xl font-bold text-${color}-700`}>{value}</span>
      {improvement !== undefined && (
        <span className={`mt-1 text-xs font-medium ${isImprovement ? 'text-emerald-600' : 'text-red-600'}`}>
          {isImprovement ? '-' : '+'}{Math.abs(improvement)}% {isImprovement ? 'improvement' : 'increase'}
        </span>
      )}
    </div>
  );
}
