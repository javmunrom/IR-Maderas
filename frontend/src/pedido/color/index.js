import React, { useState } from 'react'
import '../../static/css/pedido/Material/material.css'

const ElegirColor = ({ onColorSeleccionado }) => {
  const [colorSeleccionado, setColorSeleccionado] = useState('')

  const handleColorSeleccionado = (color) => {
    setColorSeleccionado(color.nombre)
    onColorSeleccionado(color.nombre)
  }

  const opcionesDeColor = [
    { nombre: 'Negro', imagen: '/colores/negro.png' },
    { nombre: 'Blanco', imagen: '/colores/blanco.png' },
    { nombre: 'Gris', imagen: '/colores/gris.png' },
    { nombre: 'Amarillo', imagen: '/colores/amarillo.png' },
  ]

  return (
    <div>
      <h2 className="titulo-material">Selecciona el color</h2>
      <div className="material-container">
        {opcionesDeColor.map((color) => (
          <div key={color.nombre} className="material-material">
            <div className="material-contenedor">
              <img src={color.imagen} alt={color.nombre} />
              <p className="nombre-material">{color.nombre}</p>
              <button
                className="realizar-pedido-btn"
                onClick={() => handleColorSeleccionado(color)}
              >
                Seleccionar
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default ElegirColor
