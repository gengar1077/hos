import {
  Layout,
  FormInstance,
  Menu,
  Button,
  Form,
  Modal,
  message,
  Input,
  Result,
} from 'antd';
import {
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  UserOutlined,
  DashboardOutlined,
  TableOutlined,
} from '@ant-design/icons';
import React, { useEffect, useReducer, useState } from 'react';
import './index.scss';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useParams,
  useRouteMatch,
  useHistory,
} from 'react-router-dom';
import DashboardPage from './Dashboard';
import User from './User';
import Drug from './Drug';
import Stock from './Stock';
import Supplier from './Supplier';
import Sell from './Sell';
import axios from 'axios';
import config from '../../config/env.test';
import UserRoleSelect, { RoleType } from '@/components/UserRoleSelect';
import UploadAvatar from '@/components/UploadAvatar';
const { BASE_URL } = config;
const { Header, Sider, Content } = Layout;
const formRef = React.createRef<FormInstance>();
const formItemLayout = {
  labelCol: {
    xs: { span: 24 },
    sm: { span: 6 },
  },
  wrapperCol: {
    xs: { span: 24 },
    sm: { span: 18 },
  },
};

async function getUserProfile() {
  try {
    const res = await axios.get(BASE_URL + '/user/getUser');
    console.log('[Dashboard] getUserProfile success:', res);
    return res.data;
  } catch (e) {
    console.log('[Dashboard] getUserProfile failed:', e);
    message.error('获取用户信息失败');
    throw e;
  }
}
function NoAuthPage() {
  const history = useHistory();
  return (
    <Result
      status="warning"
      title="用户角色无对应权限，请登录相应账户操作"
      extra={
        <Button
          type="primary"
          key="console"
          onClick={() => {
            history.push('/signin');
          }}
        >
          跳转登录
        </Button>
      }
    />
  );
}
export default function Dashboard(props) {
  const [userProfile, setUserProfile] = useState({
    name: props.user,
    photo: '',
    phone: '',
    wei: '',
    roleName: RoleType.ROLE_USER,
    remark: '',
  });
  let errCnt = 0
  let timer
  useEffect(() => {
    timer = setInterval(() => {
      if(errCnt<3){
        getUserProfile().then((profile) => {
          setUserProfile(profile);
        }).catch(err=>{
          console.log('errCnt', errCnt)
          errCnt ++
        });
      }else{
        clearInterval(timer)
      }
    }, 1000);
  }, []);
  const [collapsed, toggleCollapsed] = useReducer((state) => !state, false);
  const { path, url } = useRouteMatch();
  const PathIndex = [
    { path: '/', index: '1' },
    { path: '/dashboard', index: '1' },
    { path: '/user', index: '2' },
    { path: '/drug', index: '3' },
    { path: '/stock', index: '4' },
    { path: '/supplier', index: '5' },
    { path: '/sell', index: '6' },
  ];
  const index = PathIndex.find((item) => item.path === path)?.index ?? '1';
  const defaultSelectedKeys = [index];
  const [userForm] = Form.useForm();
  const handleLogout = () => {
    props.onLogout();
  };
  const handleProfileEdit = () => {
    userForm.setFieldsValue({ ...userProfile });
    setIsModalVisible(true);
  };
  const handleOk = async () => {
    try {
      const values = await userForm.validateFields();
      await handleUserEdit(values);
      userForm.resetFields();
    } catch (e) {
      console.log('handleOk Failed:', e);
    }
  };
  const handleCancel = () => {
    setIsModalVisible(false);
  };
  const handleUserEdit = async (values) => {
    console.log('[Dashboard] handleUserEdit:', values);
    try {
      const res = await axios.post(BASE_URL + '/user/update', {
        ...values,
      });
      setIsModalVisible(false);
      console.log('[Dashboard] handleUserEdit success:', res);
      message.success('修改成功');
    } catch (e) {
      console.log('[Dashboard] handleUserEdit failed:', e);
      message.error('修改失败，请重试');
      throw e;
    }
  };
  const [isModalVisible, setIsModalVisible] = useState(false);
  return (
    <Layout className="dashboard-wrapper">
      <Modal
        title="修改个人信息"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        cancelText="取消"
        okText="修改"
      >
        <Form
          form={userForm}
          {...formItemLayout}
          name="editUserProfile"
          initialValues={{
            prefix: '86',
          }}
          scrollToFirstError
          ref={formRef}
        >
          <Form.Item
            name="name"
            label="用户名"
            rules={[
              {
                required: true,
                message: '请输入用户名',
                whitespace: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="password"
            label="密码"
            rules={[
              {
                required: true,
                message: '请输入密码',
              },
            ]}
          >
            <Input.Password />
          </Form.Item>
          <Form.Item label="头像">
            <UploadAvatar></UploadAvatar>
          </Form.Item>
          <Form.Item name="wei" label="微博">
            <Input />
          </Form.Item>
          <Form.Item
            name="roleName"
            label="角色"
            rules={[
              {
                required: true,
                message: '请选择角色',
              },
            ]}
          >
            <UserRoleSelect
              isAdmin={false}
              onChange={(value) => {
                formRef.current!.setFieldsValue({ roleName: value });
              }}
            ></UserRoleSelect>
          </Form.Item>
          <Form.Item name="remark" label="简介">
            <Input />
          </Form.Item>
        </Form>
      </Modal>
      <Sider trigger={null} collapsible collapsed={collapsed}>
        <div className="logo" />
        <Menu
          theme="dark"
          mode="inline"
          defaultSelectedKeys={defaultSelectedKeys}
        >
          <Menu.Item key="1" icon={<DashboardOutlined />}>
            <Link to="/dashboard">主页</Link>
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

          <Menu.Item key="5" icon={<TableOutlined />}>
            <Link to="/supplier">供应商管理</Link>
          </Menu.Item>

          <Menu.Item key="6" icon={<TableOutlined />}>
            <Link to="/sell">销售管理</Link>
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
          <div className="profile-wrapper">
            <div className="avatar">{userProfile.name[0]}</div>
            <Button className="editBtn" type="link" onClick={handleProfileEdit}>
              修改个人信息
            </Button>
            <Button className="logoutBtn" type="primary" onClick={handleLogout}>
              登出
            </Button>
          </div>
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
              {userProfile.roleName === RoleType.ROLE_ADMIN ? (
                <User></User>
              ) : (
                <NoAuthPage></NoAuthPage>
              )}
            </Route>
            <Route path={`/drug`}>
              <Drug></Drug>
            </Route>
            <Route path={`/stock`}>
              {userProfile.roleName === RoleType.ROLE_STOCK ||
              userProfile.roleName === RoleType.ROLE_ADMIN ? (
                <Stock></Stock>
              ) : (
                <NoAuthPage></NoAuthPage>
              )}
            </Route>
            <Route path={`/supplier`}>
              {userProfile.roleName === RoleType.ROLE_ADMIN ? (
                <Supplier></Supplier>
              ) : (
                <NoAuthPage></NoAuthPage>
              )}
            </Route>
            <Route path={`/sell`}>
              {userProfile.roleName === RoleType.ROLE_SELL ||
              userProfile.roleName === RoleType.ROLE_ADMIN ? (
                <Sell></Sell>
              ) : (
                <NoAuthPage></NoAuthPage>
              )}
            </Route>
          </Switch>
        </Content>
      </Layout>
    </Layout>
  );
}
