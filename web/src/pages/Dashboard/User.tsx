import './User.scoped.scss';
import {
  Table,
  Input,
  Button,
  InputNumber,
  Popconfirm,
  Form,
  Typography,
  Modal,
  Cascader,
  Select,
  Row,
  Col,
  Checkbox,
  AutoComplete,
} from 'antd';
import React, { useState, useEffect } from 'react';
import { UserOutlined, LockOutlined } from '@ant-design/icons';

interface Item {
  key: string;
  name: string;
  age: number;
  address: string;
}

const originData: Item[] = [];
for (let i = 0; i < 100; i++) {
  originData.push({
    key: i.toString(),
    name: `Edrward ${i}`,
    age: 32,
    address: `London Park no. ${i}`,
  });
}
interface EditableCellProps extends React.HTMLAttributes<HTMLElement> {
  editing: boolean;
  dataIndex: string;
  title: any;
  inputType: 'number' | 'text';
  record: Item;
  index: number;
  children: React.ReactNode;
}

const EditableCell: React.FC<EditableCellProps> = ({
  editing,
  dataIndex,
  title,
  inputType,
  record,
  index,
  children,
  ...restProps
}) => {
  const inputNode = inputType === 'number' ? <InputNumber /> : <Input />;

  return (
    <td {...restProps}>
      {editing ? (
        <Form.Item
          name={dataIndex}
          style={{ margin: 0 }}
          rules={[
            {
              required: true,
              message: `Please Input ${title}!`,
            },
          ]}
        >
          {inputNode}
        </Form.Item>
      ) : (
        children
      )}
    </td>
  );
};

const HorizontalSearchForm = () => {
  const [form] = Form.useForm();
  const [, forceUpdate] = useState({});

  // To disable submit button at the beginning.
  useEffect(() => {
    forceUpdate({});
  }, []);

  const onFinish = (values: any) => {
    console.log('Finish:', values);
  };

  return (
    <Form
      form={form}
      name="horizontal_search"
      layout="inline"
      onFinish={onFinish}
    >
      <Form.Item name="username">
        <Input
          prefix={<UserOutlined className="site-form-item-icon" />}
          placeholder="用户名"
        />
      </Form.Item>
      <Form.Item shouldUpdate>
        {() => (
          <Button type="primary" htmlType="submit">
            查询
          </Button>
        )}
      </Form.Item>
    </Form>
  );
};

const { Option } = Select;
const formItemLayout = {
  labelCol: {
    xs: { span: 24 },
    sm: { span: 8 },
  },
  wrapperCol: {
    xs: { span: 24 },
    sm: { span: 16 },
  },
};

export default function User() {
  const [form] = Form.useForm();
  const [userForm] = Form.useForm();
  const [data, setData] = useState(originData);
  const [editingKey, setEditingKey] = useState('');
  const [isModalVisible, setIsModalVisible] = useState(false);
  const isEditing = (record: Item) => record.key === editingKey;

  const prefixSelector = (
    <Form.Item name="prefix" noStyle>
      <Select style={{ width: 70 }}>
        <Option value="86">+86</Option>
        <Option value="87">+87</Option>
      </Select>
    </Form.Item>
  );
  const edit = (record: Partial<Item> & { key: React.Key }) => {
    form.setFieldsValue({ name: '', age: '', address: '', ...record });
    setEditingKey(record.key);
  };
  const handEditCancel = () => {
    setEditingKey('');
  };
  const showModal = () => {
    setIsModalVisible(true);
  };
  const handleOk = () => {
    userForm
      .validateFields()
      .then((values) => {
        userForm.resetFields();
        onFinish(values);
      })
      .catch((info) => {
        console.log('Validate Failed:', info);
      });
  };
  const onFinish = (values: any) => {
    console.log('Received values of form: ', values);
    setIsModalVisible(false);
  };
  const handleCancel = () => {
    setIsModalVisible(false);
  };
  const handleUserDelete = () => {
    console.log('handleUserDelete');
  };
  const save = async (key: React.Key) => {
    try {
      const row = (await form.validateFields()) as Item;

      const newData = [...data];
      const index = newData.findIndex((item) => key === item.key);
      if (index > -1) {
        const item = newData[index];
        newData.splice(index, 1, {
          ...item,
          ...row,
        });
        setData(newData);
        setEditingKey('');
      } else {
        newData.push(row);
        setData(newData);
        setEditingKey('');
      }
    } catch (errInfo) {
      console.log('Validate Failed:', errInfo);
    }
  };

  const columns = [
    {
      title: 'name',
      dataIndex: 'name',
      width: '25%',
      editable: true,
    },
    {
      title: 'age',
      dataIndex: 'age',
      width: '15%',
      editable: true,
    },
    {
      title: 'address',
      dataIndex: 'address',
      width: '40%',
      editable: true,
    },
    {
      title: 'operation',
      dataIndex: 'operation',
      render: (_: any, record: Item) => {
        const editable = isEditing(record);
        return editable ? (
          <span>
            <a
              href="javascript:;"
              onClick={() => save(record.key)}
              style={{ marginRight: 8 }}
            >
              保存
            </a>
            <Popconfirm
              title="确定取消？"
              onConfirm={handEditCancel}
              okText="确定"
              cancelText="取消"
            >
              <a>取消</a>
            </Popconfirm>
          </span>
        ) : (
          <>
            <Typography.Link
              disabled={editingKey !== ''}
              onClick={() => edit(record)}
              style={{ marginRight: 8 }}
            >
              编辑
            </Typography.Link>
            <Popconfirm
              title="确定删除？"
              onConfirm={handleUserDelete}
              okText="确定"
              cancelText="取消"
            >
              <a>删除</a>
            </Popconfirm>
          </>
        );
      },
    },
  ];

  const mergedColumns = columns.map((col) => {
    if (!col.editable) {
      return col;
    }
    return {
      ...col,
      onCell: (record: Item) => ({
        record,
        inputType: col.dataIndex === 'age' ? 'number' : 'text',
        dataIndex: col.dataIndex,
        title: col.title,
        editing: isEditing(record),
      }),
    };
  });

  return (
    <div className="user-wrapper">
      <Modal
        title="新增用户"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        cancelText="取消"
        okText="新增"
      >
        <Form
          {...formItemLayout}
          form={userForm}
          name="register"
          onFinish={onFinish}
          initialValues={{
            prefix: '86',
          }}
          scrollToFirstError
        >
          <Form.Item
            name="email"
            label="E-mail"
            rules={[
              {
                type: 'email',
                message: '这是个无效的电子邮箱',
              },
              {
                required: true,
                message: '请输入电子邮箱',
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
            hasFeedback
          >
            <Input.Password />
          </Form.Item>

          <Form.Item
            name="confirm"
            label="确认密码"
            dependencies={['password']}
            hasFeedback
            rules={[
              {
                required: true,
                message: '请再次确认密码',
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
            <Input.Password />
          </Form.Item>

          <Form.Item
            name="nickname"
            label="昵称"
            tooltip="请输入昵称"
            rules={[
              {
                required: true,
                message: '请输入昵称',
                whitespace: true,
              },
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            name="phone"
            label="手机号"
            rules={[{ required: true, message: '请输入手机号' }]}
          >
            <Input addonBefore={prefixSelector} style={{ width: '100%' }} />
          </Form.Item>
        </Form>
      </Modal>
      <div className="search-form">
        <HorizontalSearchForm></HorizontalSearchForm>
        <Button type="primary" onClick={showModal}>
          新增用户
        </Button>
      </div>
      <div className="list-table">
        <Form form={form} component={false}>
          <Table
            components={{
              body: {
                cell: EditableCell,
              },
            }}
            bordered
            dataSource={data}
            columns={mergedColumns}
            rowClassName="editable-row"
            pagination={{
              onChange: handEditCancel,
            }}
          />
        </Form>
      </div>
    </div>
  );
}
