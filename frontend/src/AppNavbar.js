import React, { useState, useEffect } from 'react'
import {
  Navbar,
  NavbarBrand,
  NavLink,
  NavItem,
  Nav,
  NavbarText,
  NavbarToggler,
  Collapse,
} from 'reactstrap'
import { Link } from 'react-router-dom'
import tokenService from './services/token.service'
import jwt_decode from 'jwt-decode'

function AppNavbar() {
  const [roles, setRoles] = useState([])
  const [username, setUsername] = useState('')
  const jwt = tokenService.getLocalAccessToken()
  const [collapsed, setCollapsed] = useState(true)

  const toggleNavbar = () => setCollapsed(!collapsed)

  useEffect(() => {
    if (jwt) {
      setRoles(jwt_decode(jwt).authorities)
      setUsername(jwt_decode(jwt).sub)
    }
  }, [jwt])

  let adminLinks = <></>
  let userLinks = <></>
  let userLogout = <></>
  let publicLinks = <></>

  roles.forEach((role) => {
    if (role === 'OWNER') {
      adminLinks = (
        <>
          <NavItem>
            <NavLink style={{ color: 'white' }} tag={Link} to="/owners">
              Owners
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink style={{ color: 'white' }} tag={Link} to="/pets">
              Pets
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink style={{ color: 'white' }} tag={Link} to="/vets">
              Vets
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink style={{ color: 'white' }} tag={Link} to="/consultations">
              Consultations
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink style={{ color: 'white' }} tag={Link} to="/clinicOwners">
              Clinic Owners
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink style={{ color: 'white' }} tag={Link} to="/clinics">
              Clinics
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink style={{ color: 'white' }} tag={Link} to="/users">
              Users
            </NavLink>
          </NavItem>
        </>
      )
    }
    if (role === 'USER') {
      userLinks = (
        <>
          <NavItem>
            <NavLink style={{ color: 'white' }} tag={Link} to="/mispedidos">
              Mis Pedidos
            </NavLink>
          </NavItem>
        </>
      )
      userLogout = (
        <>
          <NavbarText
            style={{ color: 'white' }}
            className="justify-content-end"
          >
            {username}
          </NavbarText>
          <NavItem className="d-flex">
            <NavLink
              style={{ color: 'white' }}
              id="logout"
              tag={Link}
              to="/logout"
            >
              Logout
            </NavLink>
          </NavItem>
        </>
      )
    }
  })

  if (!jwt) {
    publicLinks = (
      <>
        <NavItem>
          <NavLink
            style={{ color: 'white' }}
            id="register"
            tag={Link}
            to="/register"
          >
            Register
          </NavLink>
        </NavItem>
        <NavItem>
          <NavLink style={{ color: 'white' }} id="login" tag={Link} to="/login">
            Login
          </NavLink>
        </NavItem>
      </>
    )
  }

  return (
    <div>
      <Navbar expand="md" dark color="dark">
        <NavbarBrand href="/homeuser">
          <img
            alt="logo"
            src="/logoSinFondo.png"
            style={{ height: 40, width: 40 }}
          />
          Maderas Nuñez y Villada
        </NavbarBrand>
        <NavbarToggler onClick={toggleNavbar} className="ms-2" />
        <Collapse isOpen={!collapsed} navbar>
          <Nav className="me-auto mb-2 mb-lg-0" navbar>
            {userLinks}
            {adminLinks}
          </Nav>
          <Nav className="ms-auto mb-2 mb-lg-0" navbar>
            {publicLinks}
            {userLogout}
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  )
}

export default AppNavbar
