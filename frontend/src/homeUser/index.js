import React, { useState, useEffect } from 'react'
import '../static/css/homeuser/homeuser.css'

const HomeUser = () => {
  const [tableros, setTableros] = useState([])

  useEffect(() => {
    fetch('/api/v1/tableros/random')
      .then((response) => response.json())
      .then((data) => setTableros(data))
      .catch((error) => console.error('Error fetching tableros:', error))
  }, [])

  const redirectToMaterialPage = () => {
    window.location.href = '/pieza'
  }

  return (
    <div>
      <div className="tableros-container">
        {tableros.map((tablero) => (
          <div key={tablero.id} className="tablero-item">
            <div className="image-container">
              <img
                src={tablero.img}
                alt={`${tablero.color} ${tablero.tipoMaterial}`}
              />
            </div>
            <div className="tablero-details">
              <div className="tipo-color-container">
                <p className="tipo-material">{tablero.tipoMaterial}</p>
                <p className="color">{tablero.color}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
      <div
        className="realizar-pedido-container"
        onClick={redirectToMaterialPage}
      >
        <button className="realizar-pedido-btn">Realiza tu pedido</button>
      </div>
    </div>
  )
}

export default HomeUser
