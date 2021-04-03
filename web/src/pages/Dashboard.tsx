import { Layout, Menu } from 'antd';
import {
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  UserOutlined,
  DashboardOutlined,
  TableOutlined,
} from '@ant-design/icons';
import React, { useReducer } from 'react';
import './Dashboard.scss';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useParams,
  useRouteMatch,
} from 'react-router-dom';
import { Doughnut } from 'react-chartjs-2';
const { Header, Sider, Content } = Layout;
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
export default function Dashboard() {
  const [collapsed, toggleCollapsed] = useReducer((state) => !state, false);
  const { path, url } = useRouteMatch();
  const PathIndex = [
    { path: '/', index: '1' },
    { path: '/dashboard', index: '1' },
    { path: '/user', index: '2' },
    { path: '/drug', index: '3' },
  ];
  const index = PathIndex.find((item) => item.path === path)?.index ?? '1';
  const defaultSelectedKeys = [index];
  return (
    <Layout className="dashboard-wrapper">
      <Sider trigger={null} collapsible collapsed={collapsed}>
        <div className="logo" />
        <Menu
          theme="dark"
          mode="inline"
          defaultSelectedKeys={defaultSelectedKeys}
        >
          <Menu.Item key="1" icon={<DashboardOutlined />}>
            <Link to="/dashboard">仪表盘</Link>
          </Menu.Item>
          <Menu.Item key="2" icon={<UserOutlined />}>
            <Link to="/user">用户管理</Link>
          </Menu.Item>
          <Menu.Item key="3" icon={<TableOutlined />}>
            <Link to="/drug">药物管理</Link>
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout className="site-layout">
        <Header className="site-layout-background" style={{ padding: 0 }}>
          {React.createElement(
            collapsed ? MenuUnfoldOutlined : MenuFoldOutlined,
            {
              className: 'trigger',
              onClick: toggleCollapsed,
            },
          )}
        </Header>
        <Content
          className="site-layout-background"
          style={{
            margin: '24px 16px',
            padding: 24,
            minHeight: 280,
          }}
        >
          <Switch>
            <Route exact path={[`/dashboard`, '/']}>
              <h3>dashboard</h3>
              <Doughnut {...config}></Doughnut>
            </Route>
            <Route path={`/user`}>
              <h3>manager user</h3>
            </Route>
            <Route path={`/drug`}>
              <h3>manager drug</h3>
            </Route>
          </Switch>
        </Content>
      </Layout>
    </Layout>
  );
}
