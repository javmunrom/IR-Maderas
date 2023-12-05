import React, { useState, useEffect } from 'react'
import axios from 'axios'
import ElegirColor from '../color'
import ElegirMaterial from '../Material'
import { nuevaPiezaFormInputs } from './form/nuevaPiezaFormInputs'
import FormGenerator from '../../components/formGenerator/formGenerator'
import tokenService from '../../services/token.service'
import '../../static/css/pedido/Material/pedido.css'

const CrearPiezaPage = () => {
  const [tipoMaterial, setTipoMaterial] = useState('')
  const [color, setColor] = useState('')
  const [tablero, setTablero] = useState(null)
  const [nuevaPiezaData, setNuevaPiezaData] = useState({
    cantoLadoLargo: 0,
    cantoLadoCorto: 0,
    medidaLargo: 0,
    medidaCorto: 0,
    diseño: '',
  })

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

  async function handleSubmit({ values }) {
    try {
      const reqBody = {
        tablero: tablero,
        nuevaPiezaData: values,
      }
      console.log(reqBody)
      const response = await fetch('/api/v1/piezas', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${tokenService.getLocalAccessToken()}`,
        },
        body: JSON.stringify(reqBody),
      })
      window.location.href = '/tuspiezas'
      if (response.status === 200) {
        const data = await response.json()
        // Actualiza el estado de la nueva pieza creada
        console.log('Nueva pieza creada:', data)
      } else {
        const error = await response.text()
        console.error('Error al crear la pieza:', error)
      }
    } catch (error) {
      console.error('Error al crear la pieza:', error)
    }
  }

  return (
    <div className="opciones-container">
      <div>
        {!tipoMaterial && (
          <ElegirMaterial onMaterialSeleccionado={handleMaterialSeleccionado} />
        )}

        {tipoMaterial && !color && (
          <ElegirColor onColorSeleccionado={handleColorSeleccionado} />
        )}

        {tipoMaterial && color && !tablero && (
          <p>Cargando información del tablero...</p>
        )}

        {tipoMaterial && color && tablero && (
          <div>
            <FormGenerator
              ref={null} // Ref no se usa en este ejemplo, ajusta según tus necesidades
              inputs={nuevaPiezaFormInputs}
              onSubmit={handleSubmit}
              numberOfColumns={1}
              listenEnterKey
              buttonText="Confirmar"
              buttonClassName="auth-button"
            />
          </div>
        )}
      </div>
    </div>
  )
}

export default CrearPiezaPage
