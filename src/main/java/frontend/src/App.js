import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import './App.css';
import Login from './components/auth/Login';
import Quora from './components/Quora';
import { login, selectUser } from './feature/userSlice';
import axios from 'axios';

function App() {
  const user = useSelector(selectUser);
  const dispatch = useDispatch();

  useEffect(() => {
    // Only fetch user info if not already authenticated
    if (!user) {
      axios.get("http://localhost:8080/auth/me", { withCredentials: true })
        .then((res) => {
          if (res.data) {
            dispatch(
              login({
                uid: res.data.id,
                userName: res.data.userName,
                photo: res.data.photo,
                email: res.data.email
              })
            );
          }
        })
        .catch((err) => {
          // User not authenticated, do nothing
        });
    }
  }, [user, dispatch]);

  return (
    <div className="App">
      {user ? <Quora /> : <Login />}
    </div>
  );
}

export default App;
