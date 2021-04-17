import './Stock.scoped.scss';
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
  Descriptions,
} from 'antd';
import React, { useState, useEffect, useRef } from 'react';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import SearchSelect from '@/components/SearchSelect';
import axios from 'axios';
import config from '../../config/env.test';
const { BASE_URL } = config;
interface Item {
  key: string;
  stockId: string;
  pid: string;
  pname: string;
  pnum: number;
  pNum: number;
  createtime: Date;
  productVO: ProductVO;
}
interface ProductVO {
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
          {dataIndex === 'pname' ? (
            <SearchSelect
              value={record.pname}
              onChange={(value) => {
                formRef.current!.setFieldsValue({ pname: value });
              }}
            />
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

const HorizontalSearchForm = (props) => {
  const [form] = Form.useForm();
  const [, forceUpdate] = useState({});

  // To disable submit button at the beginning.
  useEffect(() => {
    forceUpdate({});
  }, []);

  const onFinish = (values: any) => {
    console.log('Finish:', values);
    props.onFinish(values?.pname);
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
  const [drugInfoModalData, setDrugInfoModalData] = useState<
    ProductVO | undefined
  >(undefined);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(true);
  const [editingKey, setEditingKey] = useState('');
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isViewModalVisible, setIsViewModalVisible] = useState(false);

  const isEditing = (record: Item) => record.key === editingKey;
  const updateList = async (pageNum: number, pageSize = 10, name?: string) => {
    console.log(`[Stock] update list:`, pageNum, pageSize);
    try {
      setLoading(true);
      const res = await axios.get(BASE_URL + '/product/findStock', {
        params: {
          pageNum,
          pageSize,
          name,
        },
      });
      const data =
        res.data?.list.map((item: Item) => {
          return {
            ...item,
            key: item.stockId,
            pNum: item.pnum,
          };
        }) || [];
      const total = res.data?.total || 0;
      setTotal(total);
      setData(data);
      console.log(`[Stock] update list success:`, res);
    } catch (e) {
      console.log(`[Stock] update list failed:`, e);
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
    console.log('[Stock] handleAdd:', values);
    try {
      const res = await axios.post(BASE_URL + '/product/addStock', {
        ...values,
      });
      await updateList(1);
      setIsModalVisible(false);
      console.log('[Stock] handleAdd success:', res);
      message.success('新增成功');
    } catch (e) {
      console.log('[Stock] handleAdd failed:', e);
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
  const handleViewShow = async (key: React.Key) => {
    const index = data.findIndex((item) => key === item.key);
    if (index > -1) {
      const item = data[index];
      const drugInfo = item.productVO;
      setDrugInfoModalData(drugInfo);
      setIsViewModalVisible(true);
    }
  };
  const handleDelete = async (key: React.Key) => {
    try {
      const index = data.findIndex((item) => key === item.key);
      if (index > -1) {
        const item = data[index];
        const res = await axios.post(BASE_URL + '/product/delStock', {
          stockId: item.stockId,
        });
      }
      console.log('[Stock] handleDelete success:', key, index);
      updateList(1);
      message.success('删除成功');
    } catch (e) {
      console.log('[Stock] handleDelete failed:', e);
      message.error('删除失败，请重试');
    }
  };
  const handleViewCancel = () => {
    setIsViewModalVisible(false);
  };
  const handleUpdate = async (key: React.Key) => {
    console.log('[Stock] handleUpdate:', key);
    try {
      const row = (await form.validateFields()) as Item;
      const index = data.findIndex((item) => key === item.key);
      if (index > -1) {
        const item = data[index];
        const newItem = {
          ...item,
          ...row,
        };
        const res = await axios.post(BASE_URL + '/product/editStock', {
          ...newItem,
        });
        setEditingKey('');
      }
      updateList(1);
      console.log('[Stock] handleUpdate success:', key, index);
      message.success('更新成功');
    } catch (e) {
      console.log('[Stock] handleUpdate failed:', e);
      message.error('更新失败，请重试');
    }
  };

  const columns = [
    {
      title: '库存ID',
      dataIndex: 'stockId',
      editable: false,
    },
    {
      title: '药品名',
      dataIndex: 'pname',
      editable: true,
    },
    {
      title: '药品数量',
      dataIndex: 'pNum',
      editable: true,
    },
    {
      title: '操作时间',
      dataIndex: 'createtime',
      render: (_: any, record: Item) => {
        return <span>{record.createtime.toString()?.split('T')[0]}</span>;
      },
    },
    {
      title: '药品信息',
      dataIndex: 'productVO',
      render: (_: any, record: Item) => {
        return (
          <Typography.Link
            disabled={editingKey !== ''}
            onClick={() => handleViewShow(record.key)}
            style={{ marginRight: 8 }}
          >
            点击查看
          </Typography.Link>
        );
      },
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
        title="新增库存"
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
            <SearchSelect
              onChange={(value) => {
                addForm.setFieldsValue({ pname: value });
              }}
            ></SearchSelect>
          </Form.Item>
        </Form>
      </Modal>
      <Modal
        title="药品信息"
        onOk={handleViewCancel}
        onCancel={handleViewCancel}
        visible={isViewModalVisible}
        cancelText="取消"
        okText="确认"
      >
        {drugInfoModalData ? (
          <Descriptions title="药品详情">
            <Descriptions.Item label="药品名">
              {drugInfoModalData.pname}
            </Descriptions.Item>
            <Descriptions.Item label="药品地点">
              {drugInfoModalData.place}
            </Descriptions.Item>
            <Descriptions.Item label="药品规格">
              {drugInfoModalData.spec}
            </Descriptions.Item>
            <Descriptions.Item label="药品价格">
              {drugInfoModalData.price}
            </Descriptions.Item>
            <Descriptions.Item label="药品描述">
              {drugInfoModalData.remark}
            </Descriptions.Item>
          </Descriptions>
        ) : (
          <div>药品信息为空</div>
        )}
      </Modal>
      <div className="search-form">
        <HorizontalSearchForm
          onFinish={(values) => {
            updateList(1, 999, values);
          }}
        ></HorizontalSearchForm>
        <Button type="primary" onClick={showModal}>
          新增库存
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
