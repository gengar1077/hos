import './Dashboard.scss';
import Doughnut from '../../components/Doughnut';
import LineChart from '../../components/LineChart';
import BarChart from '../../components/BarChart';
import PolarChart from '../../components/PolarChart';
export default function Dashboard() {
  return (
    <div>
      <h3>dashboard</h3>
      <Doughnut></Doughnut>
      <LineChart></LineChart>
      <BarChart></BarChart>
      <PolarChart></PolarChart>
    </div>
  );
}
