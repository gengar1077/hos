import { Form, Input, Button, Checkbox, message } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import React, { useContext, createContext, useState } from 'react';
import './Signin.scss';

export enum ErrorType {
  NETWORK_ERROR = 0,
  PWD_ERROR,
}
export default function Signin(props) {
  const [form] = Form.useForm();
  const onFinish = async (values: {
    username: string;
    password: string;
    remember: boolean;
  }) => {
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
        <div className="title">登录框</div>
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
        <Form.Item>
          <Form.Item name="remember" valuePropName="checked" noStyle>
            <Checkbox>记住我</Checkbox>
          </Form.Item>

          <a className="login-form-forgot" href="">
            忘记密码
          </a>
        </Form.Item>
        <Form.Item>
          <Button
            type="primary"
            htmlType="submit"
            className="login-form-button"
          >
            登录
          </Button>
          <div className="register">
            没有账户 <a href="">注册!</a>
          </div>
        </Form.Item>
      </Form>
    </div>
  );
}
