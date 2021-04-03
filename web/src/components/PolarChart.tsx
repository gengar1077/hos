import { Polar } from 'react-chartjs-2';
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
  type: 'polarArea',
  data: data,
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Chart.js Polar Area Chart',
      },
    },
  },
};
export default function PolarChart() {
  return <Polar {...config}></Polar>;
}
