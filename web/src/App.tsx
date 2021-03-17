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

export default function AuthExample() {
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

const fakeAuth = {
  isAuthenticated: false,
  signin(cb?) {
    fakeAuth.isAuthenticated = true;
    setTimeout(cb, 100); // fake async
  },
  signout(cb?) {
    fakeAuth.isAuthenticated = false;
    setTimeout(cb, 100);
  },
};

/** For more details on
 * `authContext`, `ProvideAuth`, `useAuth` and `useProvideAuth`
 * refer to: https://usehooks.com/useAuth/
 */
const authContext = createContext<{
  user: string | null | undefined;
  signin: (cb?: any) => void;
  signout: (cb?: any) => void;
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

  const signin = (cb) => {
    return fakeAuth.signin(() => {
      setUser('user');
      cb();
    });
  };

  const signout = (cb) => {
    return fakeAuth.signout(() => {
      setUser(null);
      cb();
    });
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
    auth.signin(() => {
      history.push('/dashboard');
    });
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
