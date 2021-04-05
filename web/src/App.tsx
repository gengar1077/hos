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
const { BASE_URL } = config;

const routeConifg = [
  {
    path: ['/', '/dashboard', '/user', '/drug', '/stock'],
    component: Dashboard,
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
  };
  const sendRequest = () => {
    axios
      .get(BASE_URL + '/role/info')
      .then((res) => {
        setRoleInfo(JSON.stringify(res.data));
        console.log(res);
      })
      .catch((err) => {
        setErrorText(JSON.stringify(err));
        console.log(err);
      });
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
                <Route exact key={i} path={item.path}>
                  <item.component></item.component>
                </Route>
              );
            })}
          </Switch>
        </div>
      </Router>
    </ProvideAuth>
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
      const res = await axios.post(BASE_URL + '/login/login', {
        username,
        password,
      });
      console.log(`[AuthContext] sigin success:`, res);
      return res.data;
    } catch (e) {
      console.log(`[AuthContext] sigin failed:`, e);
      throw e;
    }
  };

  const signout = () => {
    setUser(null);
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
      await auth.signin(values.username, values.password);
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

// A wrapper for <Route> that redirects to the login
// screen if you're not yet authenticated.
function PrivateRoute({ children, ...rest }) {
  let auth = useAuth();
  return (
    <Route
      {...rest}
      render={({ location }) =>
        auth.user ? (
          children
        ) : (
          <Redirect
            to={{
              pathname: '/signin',
              state: { from: location },
            }}
          />
        )
      }
    />
  );
}
