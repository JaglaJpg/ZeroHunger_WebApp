import { useEffect, useState } from 'react';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '../ui/tabs';
import { DonationsByCategory } from '../dashboardComponents/DonationsByCategory';
import { DonationStats } from '../dashboardComponents/DonationStats';
import { FoodWasteStats } from '../dashboardComponents/FoodWasteStats';
import { FoodWasteOverTime } from '../dashboardComponents/FoodWasteOverTime';
import { Overview } from '../dashboardComponents/Overview';
import { Carrot, Leaf, ShoppingBag, Utensils } from 'lucide-react';
import MyFridge from '../dashboardComponents/MyFridge';


export default function DashboardPage() {
  const [summaryData, setSummaryData] = useState(null);
  const [monthlyData, setMonthlyData] = useState([]);
  const [weeklyData, setWeeklyData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [mockMode, setMockMode] = useState(false);
  const [fridgeOpen, setFridgeOpen] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      if (mockMode) return;

      try {
        setLoading(true);

        const [statsRes, monthlyRes, weeklyRes] = await Promise.all([
          fetch('http://localhost:8080/user/stats', { credentials: 'include' }),
          fetch('http://localhost:8080/user/monthly', { credentials: 'include' }),
          fetch('http://localhost:8080/user/weekly', { credentials: 'include' }),
        ]);

        const stats = await statsRes.json();
        const monthly = await monthlyRes.json();
        const weekly = await weeklyRes.json();

        const total = stats.foodDonated + stats.clothesDonated + stats.appliancesDonated;
        const totalWaste = stats.totalSaved + stats.totalWasted;
        const wasteReductionRate = totalWaste ? Math.round((stats.totalSaved / totalWaste) * 100) : 0;

        setSummaryData({ ...stats, totalDonations: total, wasteReductionRate });
        setMonthlyData(monthly);
        setWeeklyData(weekly);
      } catch (err) {
        console.error('Error fetching stats:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [mockMode]);

  const SummaryCard = ({ title, icon, color, value, loading }) => (
    <Card className={`border-none bg-${color}-50 to-${color}-100 shadow-md`}>
      <CardHeader className='flex flex-row items-center justify-between space-y-0 pb-2'>
        <CardTitle className={`text-sm font-medium text-${color}-800`}>{title}</CardTitle>
        {icon}
      </CardHeader>
      <CardContent>
        <div className={`text-2xl font-bold text-${color}-700`}>
          {loading ? 'Loading...' : value}
        </div>
      </CardContent>
    </Card>
  );

  return (
    <div className='flex min-h-screen w-full flex-col bg-gradient-to-b from-amber-50 to-white'>
      <div className='border-b border-slate-200 bg-white/50 backdrop-blur-sm'>
        <div className='flex h-16 items-center px-4 md:px-8 justify-between'>
          <div className='flex items-center gap-2'>
            <Leaf className='h-8 w-8 text-emerald-500' />
            <h1 className='text-xl font-bold text-emerald-800'>FoodSaver Dashboard</h1>
          </div>

          <div className='flex gap-4'>
            <button
              onClick={() => setFridgeOpen(true)}
              className='text-sm bg-emerald-100 hover:bg-emerald-200 text-emerald-700 px-3 py-1.5 rounded-lg shadow-sm flex items-center gap-1'
            >
              🧊 Open My Fridge
            </button>
            <button
              onClick={() => setMockMode(!mockMode)}
              className='text-sm bg-slate-100 hover:bg-slate-200 text-slate-700 px-3 py-1.5 rounded-lg shadow-sm'
            >
              {mockMode ? 'Use Backend Data' : 'Use Mock Data'}
            </button>
          </div>
        </div>
      </div>

      <div className='flex-1 space-y-6 p-4 pt-6 md:p-8'>
        <div className='grid gap-4 md:grid-cols-2 lg:grid-cols-4'>
          <SummaryCard
            title='Total Food Saved'
            icon={<Leaf className='h-5 w-5 text-emerald-600' />}
            color='emerald'
            value={summaryData?.totalSaved + ' items'}
            loading={loading}
          />
          <SummaryCard
            title='Total Food Wasted'
            icon={<Utensils className='h-5 w-5 text-red-600' />}
            color='red'
            value={summaryData?.totalWasted + ' items'}
            loading={loading}
          />
          <SummaryCard
            title='Total Donations'
            icon={<ShoppingBag className='h-5 w-5 text-amber-600' />}
            color='amber'
            value={summaryData?.totalDonations + ' items'}
            loading={loading}
          />
          <SummaryCard
            title='Waste Reduction Rate'
            icon={<Carrot className='h-5 w-5 text-blue-600' />}
            color='blue'
            value={summaryData?.wasteReductionRate + '%'}
            loading={loading}
          />
        </div>

        <Tabs defaultValue='overview' className='space-y-6'>
          <TabsList className='bg-white/70 backdrop-blur-sm'>
            <TabsTrigger value='overview'>Overview</TabsTrigger>
            <TabsTrigger value='food-waste'>Food Waste</TabsTrigger>
            <TabsTrigger value='donations'>Donations</TabsTrigger>
          </TabsList>

          <TabsContent value='overview' className='space-y-6'>
            <div className='grid gap-6 md:grid-cols-2 lg:grid-cols-7'>
              <Card className='col-span-4 border-none bg-white shadow-md'>
                <CardHeader className='border-b border-slate-200 p-4 bg-gradient-to-r from-emerald-50 to-emerald-100'>
                  <CardTitle className='text-emerald-800'>Monthly Overview</CardTitle>
                  <CardDescription className='text-emerald-600'>Saved vs Wasted Food</CardDescription>
                </CardHeader>
                <CardContent className='p-6'>
                  <Overview data={mockMode ? undefined : monthlyData} />
                </CardContent>
              </Card>

              <Card className='col-span-3 border-none bg-white shadow-md'>
                <CardHeader className='border-b border-slate-200 p-4 bg-gradient-to-r from-amber-50 to-amber-100'>
                  <CardTitle className='text-amber-800'>Donations by Category</CardTitle>
                  <CardDescription className='text-amber-600'>Distribution across categories</CardDescription>
                </CardHeader>
                <CardContent className='p-6'>
                  {summaryData && <DonationsByCategory data={summaryData} />}
                </CardContent>
              </Card>
            </div>
          </TabsContent>

          <TabsContent value='food-waste' className='space-y-6'>
            <Card className='border-none bg-white shadow-md'>
              <CardHeader className='border-b border-slate-200 p-4 bg-gradient-to-r from-red-50 to-red-100'>
                <CardTitle className='text-red-800'>Food Waste Over Time</CardTitle>
                <CardDescription className='text-red-600'>Recent trends</CardDescription>
              </CardHeader>
              <CardContent className='p-6'>
                <FoodWasteOverTime data={mockMode ? undefined : weeklyData} />
              </CardContent>
            </Card>
            {summaryData && <FoodWasteStats data={summaryData} />}
          </TabsContent>

          <TabsContent value='donations' className='space-y-6'>
            {summaryData && <DonationStats data={summaryData} />}
          </TabsContent>
        </Tabs>
      </div>

      <MyFridge isOpen={fridgeOpen} setIsOpen={setFridgeOpen} />
    </div>
  );
}
