import { Line } from 'react-chartjs-2';
const labels = ['1', '2', '3', '4', '5', '6', '7'];
const data = {
  labels: labels,
  datasets: [
    {
      label: 'Dataset 1',
      data: Array(7)
        .fill(undefined)
        .map(() => Math.ceil(Math.random() * 100)),
      borderColor: 'Red',
      backgroundColor: 'Blue',
    },
    {
      label: 'Dataset 2',
      data: Array(7)
        .fill(undefined)
        .map(() => Math.ceil(Math.random() * 100)),
      borderColor: 'Blue',
      backgroundColor: 'Red',
    },
  ],
};
const config = {
  type: 'line',
  data: data,
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Chart.js Line Chart',
      },
    },
  },
};
export default function LineChart(props) {
  return <Line {...config} {...props}></Line>;
}