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
import Doughnut from '../components/Doughnut';
import LineChart from '../components/LineChart';
import BarChart from '../components/BarChart';
import PolarChart from '../components/PolarChart';
const { Header, Sider, Content } = Layout;

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
              <Doughnut></Doughnut>
              <LineChart></LineChart>
              <BarChart></BarChart>
              <PolarChart></PolarChart>
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
