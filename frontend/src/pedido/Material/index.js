import React, { useState } from 'react'
import '../../static/css/pedido/Material/material.css'

const ElegirMaterial = ({ onMaterialSeleccionado }) => {
  const [tipoMaterial, setTipoMaterial] = useState('')

  const handleMaterialSeleccionado = (opcion) => {
    setTipoMaterial(opcion.tipo)
    onMaterialSeleccionado(opcion.tipo)
  }

  const opcionesDeMaterial = [
    { tipo: 'Melamina', imagen: '/tableros/melaminaBlanca.jpg' },
    { tipo: 'Contrachapado', imagen: '/tableros/contrachapadoBlanca.jpg' },
    { tipo: 'Macizo', imagen: '/tableros/macizoBlanco.jpg' },
  ]

  return (
    <div>
      <h2 className="titulo-material">Selecciona el tipo de material</h2>
      <div className="material-container">
        {opcionesDeMaterial.map((opcion) => (
          <div key={opcion.tipo} className="material-material">
            <div className="material-contenedor">
              <img src={opcion.imagen} alt={opcion.tipo} />
              <p className="nombre-material">{opcion.tipo}</p>
              {/* Modificado para pasar la opción completa a handleMaterialSeleccionado */}
              <button
                className="realizar-pedido-btn"
                onClick={() => handleMaterialSeleccionado(opcion)}
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

export default ElegirMaterial
