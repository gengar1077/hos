import React, { useContext, createContext, useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect,
  useHistory,
  useLocation,
} from 'react-router-dom';
import Dashboard from './pages/Dashboard/index';
import Signin, { ErrorType } from './pages/Signin';
import Register from './pages/Register';
import axios from 'axios';
import config from './config/env.test';
import { message } from 'antd';
const { BASE_URL } = config;

const routeConifg = [
  {
    path: ['/', '/dashboard', '/user', '/drug', '/stock', '/supplier', '/sell'],
    component: AuthDashboard,
  },
];
export default function AuthExample() {
  const [roleInfo, setRoleInfo] = useState<any>();
  const [errorText, setErrorText] = useState<string>();
  const handleRegister = async ({ username, password }) => {
    console.log('[App] handleRegister', username, password);
    const res = await axios.post(BASE_URL + '/login/register', {
      name: username,
      password,
    });
    console.log('[App] handleRegister success', res);
    message.success('注册成功，请到登录页面进行登录');
  };
  return (
    <ProvideAuth>
      <Router>
        <div>
          <Switch>
            <Route path="/signin">
              <AuthSignin />
            </Route>
            <Route path="/register">
              <Register onSubmit={handleRegister} />
            </Route>
            {routeConifg.map((item, i) => {
              return (
                // <PrivateRoute key={i} path={item.path}>
                //   <item.component></item.component>
                // </PrivateRoute>
                <PrivateRoute exact key={i} path={item.path}>
                  <item.component></item.component>
                </PrivateRoute>
              );
            })}
          </Switch>
        </div>
      </Router>
    </ProvideAuth>
  );
}

function setAxiosConfig(token: string) {
  axios.interceptors.request.use(
    function (config) {
      config.headers.hosToken = token;
      return config;
    },
    function (error) {
      return Promise.reject(error);
    },
  );
}
/** For more details on
 * `authContext`, `ProvideAuth`, `useAuth` and `useProvideAuth`
 * refer to: https://usehooks.com/useAuth/
 */
const authContext = createContext<{
  user: string | null | undefined;
  signin: any;
  signout: any;
}>({
  user: 'defaultuser',
  signin: () => {
    throw Error();
  },
  signout: () => {
    throw Error();
  },
});

function ProvideAuth({ children }) {
  const auth = useProvideAuth();
  return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}

function useAuth() {
  return useContext(authContext);
}

function useProvideAuth() {
  const [user, setUser] = useState<string | null>();

  const signin = async (username: string, password: string) => {
    console.log(`[AuthContext] username:${username}, password:${password}`);
    try {
      // TODO: 跳转先写死等，接口完成之后在放开
      // setUser('kongfu-cat');
      const res = await axios.post<{
          isAdmin: boolean;
          isLogged: boolean;
          password: string;
          phone: string;
          token: string;
          username: string;
      }>(BASE_URL + '/login/login', {
        username,
        password,
      });
      console.log(`[AuthContext] sigin success:`, res);
      const token = res.data.token;
      const name = res.data.username;
      localStorage.setItem(
        'userInfo',
        JSON.stringify({
          username: name,
          hosToken: token,
        }),
      );
      // config hosToken to request header
      setAxiosConfig(token);
      setUser(res.data.username);
      return res.data;
    } catch (e) {
      console.log(`[AuthContext] sigin failed:`, e);
      throw e;
    }
  };

  const signout = async () => {
    try {
      const res = await axios.post<void>(BASE_URL + '/login/logout');
      setUser(null);
      localStorage.setItem('userInfo', ''),
        console.log(`[AuthContext] signout success:`);
    } catch (e) {
      console.log(`[AuthContext] signout failed:`, e);
      throw e;
    }
  };

  return {
    user,
    signin,
    signout,
  };
}

function AuthSignin() {
  const auth = useAuth();
  const history = useHistory();
  const onSubmit = async (values: {
    password: string;
    remember: boolean;
    username: string;
  }) => {
    console.log(`[App] user signin info: `, values);
    try {
      const res = await auth.signin(values.username, values.password);
      history.push('/dashboard');
    } catch (e) {
      console.log(`[App] user signin error: `, e);
      if (e.message === 'Network Error') {
        return ErrorType.NETWORK_ERROR;
      }
      return ErrorType.PWD_ERROR;
    }
  };

  return <Signin onSubmit={onSubmit} />;
}

function AuthDashboard() {
  const auth = useAuth();
  const history = useHistory();
  const onLogout = async () => {
    try {
      const res = await auth.signout();
      history.push('/signin');
    } catch (e) {
      console.log(`[App] user signout error: `, e);
      if (e.message === 'Network Error') {
        return ErrorType.NETWORK_ERROR;
      }
      return ErrorType.PWD_ERROR;
    }
  };
  return <Dashboard onLogout={onLogout} user={auth.user} />;
}

// A wrapper for <Route> that redirects to the login
// screen if you're not yet authenticated.
function PrivateRoute({ children, ...rest }) {
  let auth = useAuth();
  return (
    <Route
      {...rest}
      render={({ location }) => {
        const userInfo = localStorage.getItem('userInfo');
        if (!userInfo) {
          return (
            <Redirect
              to={{
                pathname: '/signin',
                state: { from: location },
              }}
            />
          );
        } else {
          const res = JSON.parse(userInfo);
          setAxiosConfig(res?.hosToken);
          auth.user = res?.username;
          return children;
        }
      }}
    />
  );
}
