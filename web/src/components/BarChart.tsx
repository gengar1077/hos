import { Bar } from 'react-chartjs-2';
const labels = ['1', '2', '3', '4', '5', '6', '7'];
const data = {
  labels: labels,
  datasets: [
    {
      label: 'Dataset 1',
      data: Array(7)
        .fill(undefined)
        .map(() => Math.ceil(Math.random() * 100)),
      borderColor: '#fec979',
      backgroundColor: '#fec979',
    },
    {
      label: 'Dataset 2',
      data: Array(7)
        .fill(undefined)
        .map(() => Math.ceil(Math.random() * 100)),
      borderColor: '#42b7d8',
      backgroundColor: '#42b7d8',
    },
  ],
};
const config = {
  type: 'bar',
  data: data,
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Chart.js Bar Chart',
      },
    },
  },
};
export default function BarChart(props) {
  return <Bar {...config} {...props}></Bar>;
}
