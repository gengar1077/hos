import './Drug.scoped.scss';
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
interface Item {
  key: string;
  pid: string;
  pname: string;
  place: string;
  spec: string;
  price: number;
  remark: string;
  status: string;
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
          {inputNode}
        </Form.Item>
      ) : (
        children
      )}
    </td>
  );
};

const HorizontalSearchForm = (props) => {
  const [form] = Form.useForm();
  const [, forceUpdate] = useState({});

  // To disable submit button at the beginning.
  useEffect(() => {
    forceUpdate({});
  }, []);

  const onFinish = (values: any) => {
    console.log('Finish:', values);
    props.onFinish(values);
  };

  return (
    <Form
      form={form}
      name="horizontal_search"
      layout="inline"
      onFinish={onFinish}
    >
      <Form.Item name="pname">
        <Input
          prefix={<UserOutlined className="site-form-item-icon" />}
          placeholder="药品名"
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
export default function Drug() {
  const [form] = Form.useForm();
  const [addForm] = Form.useForm();
  const [data, setData] = useState<Item[]>([]);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(true);
  const [editingKey, setEditingKey] = useState('');
  const [isModalVisible, setIsModalVisible] = useState(false);

  const isEditing = (record: Item) => record.key === editingKey;
  const updateList = async (pageNum: number, pageSize = 10, name?: string) => {
    console.log(`[Drug] update list:`, pageNum, pageSize);
    try {
      setLoading(true);
      const res = await axios.get(BASE_URL + '/product/findByPage', {
        params: {
          pageNum,
          pageSize,
          name,
        },
      });
      const data =
        res.data?.returnData?.list.map((item: Item) => {
          return { ...item, key: item.pid };
        }) || [];
      const total = res.data?.returnData?.total || 0;
      setTotal(total);
      setData(data);
      console.log(`[Drug] update list success:`, res);
    } catch (e) {
      console.log(`[Drug] update list failed:`, e);
    } finally {
      setLoading(false);
      handEditCancel();
    }
  };
  useEffect(() => {
    updateList(1);
  }, []);
  const edit = (record: Partial<Item> & { key: React.Key }) => {
    form.setFieldsValue({ ...record });
    setEditingKey(record.key);
  };
  const handEditCancel = () => {
    setEditingKey('');
  };
  const showModal = () => {
    setIsModalVisible(true);
  };
  const handleAdd = async (values) => {
    console.log('[Drug] handleAdd:', values);
    try {
      const res = await axios.post(BASE_URL + '/product/add', {
        ...values,
      });
      await updateList(1);
      setIsModalVisible(false);
      console.log('[Drug] handleAdd success:', res);
      message.success('新增成功');
    } catch (e) {
      console.log('[Drug] handleAdd failed:', e);
      message.error('新增失败，请重试');
      throw e;
    }
  };
  const handleOk = async () => {
    try {
      const values = await addForm.validateFields();
      await handleAdd(values);
      addForm.resetFields();
    } catch (e) {
      console.log('handleOk Failed:', e);
    }
  };
  const handleCancel = () => {
    setIsModalVisible(false);
  };
  const handleDelete = async (key: React.Key) => {
    try {
      const index = data.findIndex((item) => key === item.key);
      if (index > -1) {
        const item = data[index];
        const res = await axios.post(BASE_URL + '/product/del', {
          id: item.pid,
        });
      }
      console.log('[Drug] handleDelete success:', key, index);
      updateList(1);
      message.success('删除成功');
    } catch (e) {
      console.log('[Drug] handleDelete failed:', e);
      message.error('删除失败，请重试');
    }
  };

  const handleUpdate = async (key: React.Key) => {
    console.log('[Drug] handleUpdate:', key);
    try {
      const row = (await form.validateFields()) as Item;
      const index = data.findIndex((item) => key === item.key);
      if (index > -1) {
        const item = data[index];
        const newItem = {
          ...item,
          ...row,
        };
        const res = await axios.post(BASE_URL + '/product/update', {
          ...newItem,
        });
        setEditingKey('');
      }
      updateList(1);
      console.log('[Drug] handleUpdate success:', key, index);
      message.success('更新成功');
    } catch (e) {
      console.log('[Drug] handleUpdate failed:', e);
      message.error('更新失败，请重试');
    }
  };

  const columns = [
    {
      title: '药品名',
      dataIndex: 'pname',
      editable: true,
    },
    {
      title: '药品地点',
      dataIndex: 'place',
      editable: true,
    },
    {
      title: '药品规格',
      dataIndex: 'spec',
      editable: true,
    },
    {
      title: '药品价格',
      dataIndex: 'price',
      editable: true,
    },
    {
      title: '药品描述',
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
              onClick={() => handleUpdate(record.key)}
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
                handleDelete(record.key);
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
    <div className="drug-wrapper">
      <Modal
        title="新增药品"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        cancelText="取消"
        okText="新增"
      >
        <Form
          {...formItemLayout}
          form={addForm}
          initialValues={{
            prefix: '86',
          }}
          scrollToFirstError
        >
          <Form.Item
            name="pname"
            label="药品名"
            rules={[
              {
                required: true,
                message: '请输入药品名',
                whitespace: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="place"
            label="药品地点"
            rules={[
              {
                required: true,
                message: '请输入药品地点',
                whitespace: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="spec"
            label="药品规格"
            rules={[
              {
                required: true,
                message: '请输入药品规格',
                whitespace: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="price"
            label="药品价格"
            rules={[
              {
                required: true,
                message: '请输入药品价格',
                whitespace: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="remark"
            label="药品描述"
            rules={[
              {
                message: '请输入药品描述',
                whitespace: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
        </Form>
      </Modal>
      <div className="search-form">
        <HorizontalSearchForm
          onFinish={(values) => {
            updateList(1, 999, values?.pname);
          }}
        ></HorizontalSearchForm>
        <Button type="primary" onClick={showModal}>
          新增药品
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
