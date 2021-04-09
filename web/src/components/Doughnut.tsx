import { Doughnut as DoughnutChartJS } from 'react-chartjs-2';
const backgroundColor = ['#eab676', '#76b5c5', '#abdbe3', '#eeeee4', '#1e81b0'];
const data = {
  labels: ['Red', 'Orange', 'Yellow', 'Green', 'Blue'],
  datasets: [
    {
      label: 'Dataset 1',
      data: Array(5)
        .fill(undefined)
        .map(() => Math.ceil(Math.random() * 100)),
      backgroundColor,
    },
    {
      label: 'Dataset 2',
      data: Array(5)
        .fill(undefined)
        .map(() => Math.ceil(Math.random() * 100)),
      backgroundColor,
    },
  ],
};
const config = {
  type: 'doughnut',
  data: data,
  options: {
    responsive: true,
    legend: {
      display: true,
    },
    plugins: {
      legend: {
        position: 'left',
      },
      title: {
        display: true,
        text: 'Chart.js Doughnut Chart',
      },
    },
  },
};

export default function Doughnut(props) {
  return <DoughnutChartJS {...config} {...props}></DoughnutChartJS>;
}
