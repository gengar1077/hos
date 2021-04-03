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
import Dashboard from './pages/Dashboard';
import Signin from './pages/Signin';
import axios from 'axios';
import config from './config/env.test';
const { BASE_URL } = config;
export default function AuthExample() {
  const [roleInfo, setRoleInfo] = useState<any>();
  const [errorText, setErrorText] = useState<string>();
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
          {/* <AuthButton /> */}
          {/* <ul>
            <li>
              <Link to="/dashboard">dashboard</Link>
            </li>
            <li>
              <Link to="/signin">signin</Link>
            </li>
            <li>
              <Link to="/public">public</Link>
            </li>
          </ul> */}
          <h1>TEST PAGE</h1>
          <div>
            <h2>CORS</h2>
            <button onClick={sendRequest}>
              click me to send request to get role-info
            </button>
            <h3></h3>
            <p>{roleInfo ?? 'no role info'}</p>
            <h3>Error</h3>
            <p>{errorText ?? 'no error'}</p>
          </div>

          <Switch>
            <Route path="/signin">
              <AuthSignin />
            </Route>
            {/* <PrivateRoute path="/dashboard">
              <Dashboard />
            </PrivateRoute> */}
            <Route path="/dashboard">
              <Dashboard />
            </Route>
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

  const signin = (username: string, password: string) => {
    console.log(`[signin] username:${username}, password:${password}`);
    axios
      .post(BASE_URL + '/login/login', {
        username,
        password,
      })
      .then((res) => {
        console.log(`[signin] sigin success:${JSON.stringify(res)}`);
      });
  };

  const signout = (cb) => {
    setUser(null);
    cb();
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
  const onSubmit = (values: {
    password: string;
    remember: boolean;
    username: string;
  }) => {
    console.log('onSubmit values: ', values);
    auth.signin(values.username, values.password);
    // auth.signin(() => {
    //   history.push('/dashboard');
    // });
  };

  return <Signin onSubmit={onSubmit} />;
}

function AuthButton() {
  let history = useHistory();
  let auth = useAuth();

  return auth.user ? (
    <p>
      Welcome!{' '}
      <button
        onClick={() => {
          auth.signout(() => history.push('/'));
        }}
      >
        Sign out
      </button>
    </p>
  ) : (
    <p>You are not logged in.</p>
  );
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
