import React from 'react'
import { Route, Routes, useLocation } from 'react-router-dom'
import jwt_decode from 'jwt-decode'
import { ErrorBoundary } from 'react-error-boundary'
import AppNavbar from './AppNavbar'
import Home from './home'
import PrivateRoute from './privateRoute'
import Register from './auth/register'
import Login from './auth/login'
import Logout from './auth/logout'
import tokenService from './services/token.service'
import SwaggerDocs from './public/swagger'
import HomeUser from './homeUser'
import ElegirMaterial from './pedido/Material'
import ElegirColor from './pedido/color'
import CrearPiezaPage from './pedido/crearPieza'
import PiezasPage from './pedido/piezas'
import AgregarPiezaAPedidoPage from './pedido/crearPieza/añadirpiezas'
import MisPedidosPage from './mispedidos'
import PedidosPage from './admin/todoslospedidos'
import PedidoOwner from './admin/pedidoOwner'
import PedidosOwnerPage from './admin/allPedidosOwner'

function ErrorFallback({ error, resetErrorBoundary }) {
  return (
    <div role="alert">
      <p>Something went wrong:</p>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>Try again</button>
    </div>
  )
}

function App() {
  const jwt = tokenService.getLocalAccessToken()
  const location = useLocation()
  const isPartidaRoute = location.pathname.includes('/partida')
  let roles = []
  if (jwt) {
    roles = getRolesFromJWT(jwt)
  }

  function getRolesFromJWT(jwt) {
    return jwt_decode(jwt).authorities
  }

  let adminRoutes = <></>
  let userRoutes = <></>
  let publicRoutes = <></>

  roles.forEach((role) => {
    if (role === 'OWNER') {
      adminRoutes = (
        <>
          <Route path="/logout" element={<Logout />} />

          <Route
            path="/"
            element={
              <PrivateRoute>
                <Home />
              </PrivateRoute>
            }
          />
          <Route path="/pedidosAll" element={<PedidosPage />} />
          <Route path="/pedidoOwner" element={<PedidoOwner />} />
          <Route path="/pedidoOwnerAll" element={<PedidosOwnerPage />} />
        </>
      )
    }
    if (role === 'USER') {
      userRoutes = (
        <>
          <Route
            path="/"
            element={
              <PrivateRoute>
                <Home />
              </PrivateRoute>
            }
          />
          <Route path="/logout" element={<Logout />} />
          <Route path="/homeuser" element={<HomeUser />} />
          <Route path="/material" element={<ElegirMaterial />} />
          <Route path="/color" element={<ElegirColor />} />
          <Route path="/pieza" element={<CrearPiezaPage />} />
          <Route path="/nuevapieza" element={<AgregarPiezaAPedidoPage />} />
          <Route path="/tuspiezas" element={<PiezasPage />} />
          <Route path="/mispedidos" element={<MisPedidosPage />} />
        </>
      )
    }
  })
  if (!jwt) {
    publicRoutes = (
      <>
        <Route
          path="/"
          element={
            <PrivateRoute>
              <Home />
            </PrivateRoute>
          }
        />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
      </>
    )
  }

  return (
    <div>
      <ErrorBoundary FallbackComponent={ErrorFallback}>
        {!isPartidaRoute && <AppNavbar />}
        <Routes>
          <Route path="/" exact={true} element={<Home />} />
          <Route path="/docs" element={<SwaggerDocs />} />
          {publicRoutes}
          {userRoutes}
          {adminRoutes}
        </Routes>
      </ErrorBoundary>
    </div>
  )
}

export default App
