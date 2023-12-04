class TokenService {
  getLocalRefreshToken() {
    const user = JSON.parse(localStorage.getItem('user'))
    return user?.refreshToken
  }

  getLocalAccessToken() {
    const jwtString = localStorage.getItem('jwt')

    try {
      if (jwtString !== null && jwtString !== undefined) {
        return JSON.parse(jwtString)
      } else {
        return null
      }
    } catch (error) {
      console.error('Error parsing JWT:', error)
      return null
    }
  }

  updateLocalAccessToken(token) {
    window.localStorage.setItem('jwt', JSON.stringify(token))
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user'))
  }

  setUser(user) {
    window.localStorage.setItem('user', JSON.stringify(user))
  }

  removeUser() {
    window.localStorage.removeItem('user')
    window.localStorage.removeItem('jwt')
  }
}

const tokenService = new TokenService()

export default tokenService
