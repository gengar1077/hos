import { Select, message } from 'antd';
const { Option } = Select;
export enum RoleType {
  ROLE_ADMIN = 'admin',
  ROLE_STOCK = 'stock',
  ROLE_SELL = 'sell',
}
export default function UserRoleSelect(props) {
  return (
    <Select
      showSearch
      style={{ width: 200 }}
      placeholder="请选择角色"
      optionFilterProp="children"
      onChange={(value: string) => {
        props.onChange(value);
        console.log('[User] role change', value);
      }}
    >
      {props.isAdmin ? <Option value={RoleType.ROLE_ADMIN}>管理员</Option> : ''}
      <Option value={RoleType.ROLE_SELL}>销售员</Option>
      <Option value={RoleType.ROLE_STOCK}>仓管员</Option>
    </Select>
  );
}
