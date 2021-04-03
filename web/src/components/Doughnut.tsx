import { Doughnut as DoughnutChartJS } from 'react-chartjs-2';
const data = {
  labels: ['Red', 'Orange', 'Yellow', 'Green', 'Blue'],
  datasets: [
    {
      label: 'Dataset 1',
      data: Array(5)
        .fill(undefined)
        .map(() => Math.ceil(Math.random() * 100)),
      backgroundColor: ['Red', 'Orange', 'Yellow', 'Green', 'Blue'],
    },
    {
      label: 'Dataset 2',
      data: Array(5)
        .fill(undefined)
        .map(() => Math.ceil(Math.random() * 100)),
      backgroundColor: ['Red', 'Orange', 'Yellow', 'Green', 'Blue'],
    },
  ],
};
const config = {
  type: 'doughnut',
  data: data,
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Chart.js Doughnut Chart',
      },
    },
  },
};

export default function Doughnut(props) {
  return <DoughnutChartJS {...config}></DoughnutChartJS>;
}
