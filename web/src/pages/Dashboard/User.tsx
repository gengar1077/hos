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
  Select,
  Row,
  Col,
  Checkbox,
  AutoComplete,
  FormInstance,
  notification,
  message,
} from 'antd';
import React, { useState, useEffect, useRef } from 'react';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import axios from 'axios';
import config from '../../config/env.test';
const { BASE_URL } = config;
enum RoleType {
  ROLE_ADMIN = 'admin',
  ROLE_STOCK = 'stock',
  ROLE_SELL = 'sell',
}
interface Item {
  key: string;
  id: string;
  name: string;
  photo: string;
  phone: string;
  wei: string;
  roleName: string;
  remark: string;
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
const formRef = React.createRef<FormInstance>();
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
              required: dataIndex === 'name',
              message: `请输入${title}!`,
            },
          ]}
        >
          {dataIndex === 'roleName' ? (
            <Select
              showSearch
              style={{ width: 200 }}
              placeholder="请选择角色"
              optionFilterProp="children"
              onChange={(value: string) => {
                console.log('[User] role change', value);
                formRef.current!.setFieldsValue({ roleName: value });
              }}
            >
              <Option value={RoleType.ROLE_ADMIN}>管理员</Option>
              <Option value={RoleType.ROLE_SELL}>销售员</Option>
              <Option value={RoleType.ROLE_STOCK}>仓管员</Option>
            </Select>
          ) : dataIndex === 'name' ? (
            formRef.current!.getFieldValue('name')
          ) : (
            inputNode
          )}
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
    sm: { span: 6 },
  },
  wrapperCol: {
    xs: { span: 24 },
    sm: { span: 18 },
  },
};
export default function User() {
  const [form] = Form.useForm();
  const [userForm] = Form.useForm();
  const [data, setData] = useState<Item[]>([]);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(true);
  const [editingKey, setEditingKey] = useState('');
  const [isModalVisible, setIsModalVisible] = useState(false);

  const isEditing = (record: Item) => record.key === editingKey;
  const updateList = async (pageNum: number, pageSize = 10) => {
    console.log(`[User] update user list:`, pageNum, pageSize);
    try {
      setLoading(true);
      const res = await axios.get(BASE_URL + '/user/findByPage', {
        data: {
          pageNum,
          pageSize,
        },
      });
      const data =
        res.data?.returnData?.list.map((item: Item) => {
          return { ...item, key: item.id };
        }) || [];
      const total = res.data?.returnData?.total || 0;
      setTotal(total);
      setData(data);
      console.log(`[User] update user list success:`, res);
    } catch (e) {
      console.log(`[User] update user list failed:`, e);
    } finally {
      setLoading(false);
      handEditCancel();
    }
  };
  useEffect(() => {
    updateList(1);
  }, []);
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
  const handleUserAdd = async ({ username, password }) => {
    console.log('[User] handleUserAdd:', username, password);
    try {
      const res = await axios.post(BASE_URL + '/login/register', {
        name: username,
        password,
      });
      await updateList(1);
      setIsModalVisible(false);
      console.log('[User] handleUserAdd success:', res);
      message.success('新增成功');
    } catch (e) {
      console.log('[User] handleUserAdd failed:', e);
      message.error('新增失败，请重试');
      throw e;
    }
  };
  const handleOk = async () => {
    try {
      const values = await userForm.validateFields();
      await handleUserAdd(values);
      userForm.resetFields();
    } catch (e) {
      console.log('handleOk Failed:', e);
    }
  };
  const handleCancel = () => {
    setIsModalVisible(false);
  };
  const handleUserDelete = async (key: React.Key) => {
    try {
      const index = data.findIndex((item) => key === item.key);
      if (index > -1) {
        const item = data[index];
        const res = await axios.post(BASE_URL + '/user/delete', {
          id: item.id,
        });
      }
      console.log('[User] handleUserDelete success:', key, index);
      updateList(1);
      message.success('删除成功');
    } catch (e) {
      console.log('[User] handleUserDelete failed:', e);
      message.error('删除失败，请重试');
    }
  };

  const save = async (key: React.Key) => {
    console.log('[User] save:', key);
    try {
      const row = (await form.validateFields()) as Item;
      const index = data.findIndex((item) => key === item.key);
      if (index > -1) {
        const item = data[index];
        const newItem = {
          ...item,
          ...row,
        };
        const res = await axios.post(BASE_URL + '/user/updateByAdmin', {
          ...newItem,
        });
        setEditingKey('');
      }
      updateList(1);
      console.log('[User] save success:', key, index);
      message.success('更新成功');
    } catch (e) {
      console.log('[User] save failed:', e);
      message.error('更新失败，请重试');
    }
  };

  const columns = [
    {
      title: '姓名',
      dataIndex: 'name',
      editable: true,
    },
    {
      title: '电话',
      dataIndex: 'phone',
      editable: true,
    },
    {
      title: '头像',
      dataIndex: 'photo',
      editable: true,
    },
    {
      title: '微博',
      dataIndex: 'wei',
      editable: true,
    },
    {
      title: '角色',
      dataIndex: 'roleName',
      editable: true,
    },
    {
      title: '简介',
      dataIndex: 'remark',
      editable: true,
    },
    {
      title: '操作',
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
              onConfirm={() => {
                handleUserDelete(record.key);
              }}
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
          initialValues={{
            prefix: '86',
          }}
          scrollToFirstError
        >
          <Form.Item
            name="username"
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
            hasFeedback
          >
            <Input.Password />
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
        <Form form={form} component={false} ref={formRef}>
          <Table
            components={{
              body: {
                cell: EditableCell,
              },
            }}
            loading={loading}
            bordered
            dataSource={data}
            columns={mergedColumns}
            rowClassName="editable-row"
            pagination={{
              onChange: updateList,
              total,
            }}
          />
        </Form>
      </div>
    </div>
  );
}
