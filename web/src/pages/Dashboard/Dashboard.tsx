import './Dashboard.scoped.scss';
import Doughnut from '@/components/Doughnut';
import LineChart from '@/components/LineChart';
import BarChart from '@/components/BarChart';
import PolarChart from '@/components/PolarChart';
import { Calendar, Card, Col, Row, Statistic, Timeline } from 'antd';
import { ArrowUpOutlined, ArrowDownOutlined } from '@ant-design/icons';
export default function Dashboard() {
  function onPanelChange(value, mode) {
    console.log(value, mode);
  }
  return (
    <div className="dashboard-wrapper">
      <h3>dashboard</h3>
      <Row gutter={16}>
        <Col span={8}>
          <Card title="Card title" bordered={false}>
            <Doughnut></Doughnut>
          </Card>
        </Col>
        <Col span={8}>
          <Card title="Card title" bordered={false}>
            <LineChart></LineChart>
          </Card>
        </Col>
        <Col span={8}>
          <Card title="Card title" bordered={false}>
            <BarChart></BarChart>
          </Card>
        </Col>
      </Row>
      <Row gutter={16}>
        <Col span={12}>
          <Card>
            <Statistic
              title="Active"
              value={11.28}
              precision={2}
              valueStyle={{ color: '#3f8600' }}
              prefix={<ArrowUpOutlined />}
              suffix="%"
            />
            <Statistic
              title="Idle"
              value={9.3}
              precision={2}
              valueStyle={{ color: '#cf1322' }}
              prefix={<ArrowDownOutlined />}
              suffix="%"
            />
            <Timeline>
              <Timeline.Item>Create a services site 2015-09-01</Timeline.Item>
              <Timeline.Item>
                Solve initial network problems 2015-09-01
              </Timeline.Item>
              <Timeline.Item>Technical testing 2015-09-01</Timeline.Item>
              <Timeline.Item>
                Network problems being solved 2015-09-01
              </Timeline.Item>
            </Timeline>
            <PolarChart></PolarChart>
          </Card>
        </Col>
        <Col span={12}>
          <Card>
            <Calendar
              className="calendar"
              fullscreen={false}
              onPanelChange={onPanelChange}
            />
          </Card>
        </Col>
      </Row>
    </div>
  );
}
