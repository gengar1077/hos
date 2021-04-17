import { Select, message } from 'antd';
import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import config from '@/config/env.test';
const { BASE_URL } = config;

export default (props: any) => {
  const [selectLoading, setSelectLoading] = useState(false);
  const [drugList, setDrugList] = useState<string[]>([]);
  const { Option } = Select;
  const getDrugInfoList = async () => {
    console.log(`[Drug] getDrugInfoList`);
    setSelectLoading(true);
    try {
      const res = await axios.get(BASE_URL + '/product/findByPage');
      const data = res.data?.list || [];
      const pnameList = data?.map((item) => item.pname) || [];
      setDrugList(pnameList);
      console.log(`[Drug] getDrugInfoList:`, pnameList);
    } catch (e) {
      console.log(`[Drug] getDrugInfoList failed:`, e);
      message.error('获取药物列表失败');
    } finally {
      setSelectLoading(false);
    }
  };
  return (
    <Select
      loading={selectLoading}
      style={{ width: 200 }}
      placeholder="请选择药品"
      defaultValue={props.value}
      optionFilterProp="children"
      onDropdownVisibleChange={getDrugInfoList}
      onChange={(value: string) => {
        props.onChange(value);
        console.log('[Drug] drug name select change', value);
      }}
    >
      {drugList.map((item) => {
        return <Option value={item}>{item}</Option>;
      })}
    </Select>
  );
};
