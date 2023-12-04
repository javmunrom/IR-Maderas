import React, { useState, useEffect } from 'react'
import axios from 'axios'
import ElegirColor from './color'
import ElegirMaterial from './Material'

const CrearPiezaPage = () => {
  const [tipoMaterial, setTipoMaterial] = useState('')
  const [color, setColor] = useState('')
  const [tablero, setTablero] = useState(null)

  useEffect(() => {
    const fetchData = async () => {
      if (tipoMaterial && color) {
        try {
          const response = await axios.get(
            `/api/v1/tableros/materialcolor/${tipoMaterial}/${color}`
          )
          setTablero(response.data)
        } catch (error) {
          console.error('Error al obtener el tablero:', error)
        }
      }
    }

    fetchData()
  }, [tipoMaterial, color])

  const handleMaterialSeleccionado = async (tipoMaterialSeleccionado) => {
    setTipoMaterial(tipoMaterialSeleccionado)
  }

  const handleColorSeleccionado = (colorSeleccionado) => {
    setColor(colorSeleccionado)
  }

  return (
    <div>
      <h1>Crear Pieza</h1>

      {!tipoMaterial && (
        <ElegirMaterial onMaterialSeleccionado={handleMaterialSeleccionado} />
      )}

      {tipoMaterial && !color && (
        <ElegirColor onColorSeleccionado={handleColorSeleccionado} />
      )}

      {tablero && (
        <div>
          <h2>Tablero Seleccionado</h2>
          <p>Tipo de Material: {tablero.tipoMaterial}</p>
          <p>Color: {tablero.color}</p>
          <p>Espesor: {tablero.espesor}</p>
          <p>Stock: {tablero.stock}</p>
          <img src={tablero.img} alt={`Tablero ${tablero.color}`} />
        </div>
      )}

      {tipoMaterial && color && !tablero && (
        <p>Cargando información del tablero...</p>
      )}

      {tipoMaterial && color && tablero && (
        <button onClick={() => console.log('Lógica para crear la pieza aquí')}>
          Confirmar
        </button>
      )}
    </div>
  )
}

export default CrearPiezaPage
