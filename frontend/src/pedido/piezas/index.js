import React, { useState, useEffect } from 'react'
import axios from 'axios'
import tokenService from '../../services/token.service'
import '../../static/css/pedido/Material/piezas.css'

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

  return (
    <div>
      <h2>Piezas en el Pedido</h2>
      {piezas.length === 0 ? (
        <p>No hay piezas en este pedido.</p>
      ) : (
        <ul className="piezas-list">
          {piezas.map((pieza) => (
            <li key={pieza.id} className="pieza-item">
              <img
                src={pieza.tablero.img}
                alt={`Imagen de ${pieza.tablero.color}`}
                className="pieza-image"
              />
              <div className="pieza-info">
                <h3>{pieza.nombre}</h3>
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
    </div>
  )
}
export default PiezasPage
