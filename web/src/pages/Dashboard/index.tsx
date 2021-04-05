import { Layout, Menu } from 'antd';
import {
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  UserOutlined,
  DashboardOutlined,
  TableOutlined,
} from '@ant-design/icons';
import React, { useReducer } from 'react';
import './index.scss';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useParams,
  useRouteMatch,
} from 'react-router-dom';
import DashboardPage from './Dashboard';
import User from './User';
import Drug from './Drug';
import Stock from './Stock';
const { Header, Sider, Content } = Layout;

export default function Dashboard() {
  const [collapsed, toggleCollapsed] = useReducer((state) => !state, false);
  const { path, url } = useRouteMatch();
  const PathIndex = [
    { path: '/', index: '1' },
    { path: '/dashboard', index: '1' },
    { path: '/user', index: '2' },
    { path: '/drug', index: '3' },
    { path: '/stock', index: '4' },
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
          <Menu.Item key="4" icon={<TableOutlined />}>
            <Link to="/stock">库存管理</Link>
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
              <DashboardPage></DashboardPage>
            </Route>
            <Route path={`/user`}>
              <User></User>
            </Route>
            <Route path={`/drug`}>
              <Drug></Drug>
            </Route>
            <Route path={`/stock`}>
              <Stock></Stock>
            </Route>
          </Switch>
        </Content>
      </Layout>
    </Layout>
  );
}
