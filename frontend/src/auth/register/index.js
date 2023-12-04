import React, { useState } from 'react'
import { Alert } from 'reactstrap'
import FormGenerator from '../../components/formGenerator/formGenerator'
import tokenService from '../../services/token.service'
import '../../static/css/auth/authButton.css'
import { registerFormInputs } from './form/registerFormInputs'

export default function Register() {
  const [message, setMessage] = useState(null)
  const registerFormRef = React.createRef()

  async function handleSubmit({ values }) {
    const reqBody = values

    values.authority = 'USER'

    setMessage(null)

    await fetch('/api/v1/auth/signup', {
      headers: { 'Content-Type': 'application/json' },
      method: 'POST',
      body: JSON.stringify(reqBody),
    })
      .then(function (response) {
        if (response.status === 200) return response.json()
        else return Promise.reject('Registration failed')
      })
      .then(function (data) {
        tokenService.setUser(data)
        tokenService.updateLocalAccessToken(data.token)
        window.location.href = '/login'
      })
      .catch((error) => {
        setMessage(error)
      })
  }

  return (
    <div className="auth-page-container">
      {message ? <Alert color="primary">{message}</Alert> : <></>}

      <div className="auth-form-container">
        <FormGenerator
          ref={registerFormRef}
          inputs={registerFormInputs}
          onSubmit={handleSubmit}
          numberOfColumns={1}
          listenEnterKey
          buttonText="Registrate"
          buttonClassName="auth-button"
        />
      </div>
    </div>
  )
}
