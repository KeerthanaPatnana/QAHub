import React from 'react'
import './Login.css'

function Login() {
  return (
    <div className='login-container'>
      <div className='login-content'>
        <img src='https://cdni.iconscout.com/illustration/premium/thumb/login-access-denied-4560620-3784187.png' alt='logo' />
        <a href='http://localhost:8080/oauth2/authorization/google'>
          <button className='btn-google'>Login with Google</button>
        </a>
      </div>
    </div>
  )
}

export default Login