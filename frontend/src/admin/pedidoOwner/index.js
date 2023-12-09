import React, { useState, useEffect } from 'react'
import axios from 'axios'
import ElegirColor from '../../pedido/color'
import ElegirMaterial from '../../pedido/Material'
import tokenService from '../../services/token.service'
import '../../static/css/pedido/pedidoOwner.css'

import BRICOMAR from '../../static/images/BRICOMAR.png'
import BRICTION from '../../static/images/BRICTION.png'
import MADERASGAMEZ from '../../static/images/MADERASGAMEZ.png'
import MADERASTABRISAS from '../../static/images/MADERASTABRISAS.png'

const PedidoOwner = () => {
  const [tipoMaterial, setTipoMaterial] = useState('')
  const [color, setColor] = useState('')
  const [tablero, setTablero] = useState(null)
  const [cantidad, setCantidad] = useState(1)
  const [proveedorSeleccionado, setProveedorSeleccionado] = useState(null)

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

  const handleProveedorSeleccionado = (proveedorSeleccionado) => {
    setProveedorSeleccionado(proveedorSeleccionado)
    setCantidad(1)
  }

  const handleCompletarPedido = async () => {
    const reqBody = {
      tablero: tablero,
      proveedor: proveedorSeleccionado,
      cantidad: cantidad,
    }
    console.log(reqBody)
    try {
      const response = await fetch('/api/v1/pedidosOwner', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${tokenService.getLocalAccessToken()}`,
        },
        body: JSON.stringify(reqBody),
      })

      if (response.status === 201) {
        const data = await response.json()
        console.log('Pedido completado:', data)
        window.location.href = '/pedidoOwnerAll'
      } else {
        const error = await response.text()
        console.error('Error al completar el pedido:', error)
      }
    } catch (error) {
      console.error('Error al completar el pedido:', error)
    }
  }

  return (
    <div className="todoproveedor-container">
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
          <div className="proveedor-container">
            {[
              { proveedor: 'BRICOMAR', image: BRICOMAR },
              { proveedor: 'BRICTION', image: BRICTION },
              { proveedor: 'MADERASGAMEZ', image: MADERASGAMEZ },
              { proveedor: 'MADERASTABRISAS', image: MADERASTABRISAS },
            ].map((proveedorInfo) => (
              <div
                key={proveedorInfo.proveedor}
                className={`proveedor-box ${
                  proveedorSeleccionado === proveedorInfo.proveedor
                    ? 'seleccionado'
                    : ''
                }`}
                onClick={() =>
                  handleProveedorSeleccionado(proveedorInfo.proveedor)
                }
              >
                <img
                  src={proveedorInfo.image}
                  alt={`Proveedor ${proveedorInfo.proveedor}`}
                  className="proveedor-image"
                />
                {proveedorSeleccionado === proveedorInfo.proveedor && (
                  <div className="marca-seleccion">✔</div>
                )}
              </div>
            ))}
          </div>
        )}

        {tipoMaterial && color && tablero && (
          <div className="cantidad-container">
            <label htmlFor="cantidad">Cantidad:</label>
            <input
              type="number"
              className="cantidad-input"
              placeholder="Cantidad"
              value={cantidad}
              onChange={(e) => setCantidad(e.target.value)}
            />
            <button
              className="continuar-btn"
              onClick={handleCompletarPedido}
              disabled={!proveedorSeleccionado}
            >
              Completar
            </button>
          </div>
        )}
      </div>
    </div>
  )
}

export default PedidoOwner
