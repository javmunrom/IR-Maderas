import React, { useState, useEffect } from 'react'
import axios from 'axios'
import tokenService from '../../services/token.service'
import '../../static/css/pedido/Material/piezas.css'
import { Link } from 'react-router-dom'

const PiezasPage = () => {
  const [piezas, setPiezas] = useState([])

  useEffect(() => {
    const fetchPiezas = async () => {
      try {
        const token = tokenService.getLocalAccessToken()
        const response = await axios.get('/api/v1/pedidos/piezas', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        setPiezas(response.data)
      } catch (error) {
        console.error('Error al obtener las piezas del pedido:', error)
      }
    }

    fetchPiezas()
  }, [])

  const eliminarPieza = async (piezaId) => {
    try {
      const token = tokenService.getLocalAccessToken()
      await axios.delete(`/api/v1/pedidos/piezas/${piezaId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

      // Actualizar la lista de piezas después de la eliminación
      setPiezas((prevPiezas) =>
        prevPiezas.filter((pieza) => pieza.id !== piezaId)
      )
    } catch (error) {
      console.error('Error al eliminar la pieza:', error)
    }
  }

  return (
    <div>
      {piezas.length === 0 ? (
        <p>No hay piezas en este pedido.</p>
      ) : (
        <ul className="piezas-list">
          {piezas.map((pieza) => (
            <li key={pieza.id} className="pieza-item">
              <div className="pieza-buttons">
                <button onClick={() => eliminarPieza(pieza.id)}>
                  Eliminar Pieza
                </button>
              </div>
              <img
                src={pieza.tablero.img}
                alt={`Imagen de ${pieza.tablero.color}`}
                className="pieza-image"
              />
              <div className="pieza-info">
                <p>Canto Lado Largo: {pieza.cantoLadoLargo}</p>
                <p>Canto Lado Corto: {pieza.cantoLadoCorto}</p>
                <p>Medida Largo: {pieza.medidaLargo}</p>
                <p>Medida Corto: {pieza.medidaCorto}</p>
                <p>Diseño: {pieza.diseño}</p>
                <p>Cantidad: {pieza.cantidad}</p>
              </div>
            </li>
          ))}
        </ul>
      )}
      <Link to="/nuevapieza">
        <button className="nueva-pieza-btn">Añadir Nueva Pieza</button>
      </Link>
      <div className="completar-btn-container">
        <Link to="/mispedidos">
          <button className="completar-btn">Completar pedido</button>
        </Link>
      </div>
    </div>
  )
}

export default PiezasPage
