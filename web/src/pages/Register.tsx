import { Form, Input, Button, Checkbox, message } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import React, { useContext, createContext, useState } from 'react';
import './Register.scoped.scss';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useParams,
  useRouteMatch,
} from 'react-router-dom';
export enum ErrorType {
  NETWORK_ERROR = 0,
  PWD_ERROR,
}
export default function Register(props) {
  const [form] = Form.useForm();
  const onFinish = async (values: { username: string; password: string }) => {
    console.log('[Signin] received values of form: ', values);
    const res = await props.onSubmit(values);
    if (res === ErrorType.NETWORK_ERROR) {
      message.error('网络异常，请重试');
    } else if (res === ErrorType.PWD_ERROR) {
      onReset();
      message.error('用户名或密码错误，请重新输入');
    }
  };
  const onReset = () => {
    form.resetFields();
  };
  return (
    <div className="signin-wrapper">
      <Form
        name="normal_login"
        className="form"
        form={form}
        initialValues={{ remember: true }}
        onFinish={onFinish}
      >
        <div className="title">注册框</div>
        <Form.Item
          name="username"
          rules={[{ required: true, message: '请输入用户名!' }]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="用户名"
          />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[{ required: true, message: '请输入密码!' }]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="密码"
          />
        </Form.Item>
        <Form.Item
          name="confirm"
          rules={[
            {
              required: true,
              message: '请再次确认密码!',
            },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue('password') === value) {
                  return Promise.resolve();
                }
                return Promise.reject(new Error('两次密码输入不一致'));
              },
            }),
          ]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="确认密码"
          />
        </Form.Item>
        <Form.Item>
          <Button
            type="primary"
            htmlType="submit"
            className="login-form-button"
          >
            注册
          </Button>
          <div className="register">
            <Link to="/signin">已有账户返回登录</Link>
          </div>
        </Form.Item>
      </Form>
    </div>
  );
}
