import React, { useState, useEffect } from 'react'
import axios from 'axios'
import tokenService from '../services/token.service'
import { Link } from 'react-router-dom'
import '../static/css/pedido/Material/pedidos.css'

const MisPedidosPage = () => {
  const [pedidos, setPedidos] = useState([])

  useEffect(() => {
    const fetchPedidos = async () => {
      try {
        const user = tokenService.getUser()
        const response = await axios.get(`/api/v1/pedidos/userId/${user.id}`, {
          headers: {
            Authorization: `Bearer ${tokenService.getLocalAccessToken()}`,
          },
        })
        setPedidos(response.data)
      } catch (error) {
        console.error('Error al obtener los pedidos del usuario:', error)
      }
    }

    fetchPedidos()
  }, [])
  const formatFecha = (fecha) => {
    const fechaFormateada = new Date(fecha)
    const options = {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric',
      second: 'numeric',
      timeZoneName: 'short',
    }

    return fechaFormateada.toLocaleDateString('es-ES', options)
  }

  return (
    <div>
      {pedidos.length === 0 ? (
        <p>No hay pedidos para mostrar.</p>
      ) : (
        <ul className="pedidos-list">
          {pedidos.map((pedido) => (
            <li key={pedido.id} className="pedido-item">
              <div className="pedido-container">
                <div className="pedido-info">
                  <h3>Pedido ID: {pedido.id}</h3>
                  <p>Fecha de Creación: {formatFecha(pedido.fechaPedido)}</p>
                  <p>Estado del pedido: {pedido.estado}</p>
                </div>

                {pedido.piezas.length > 0 && (
                  <ul className="piezas-list">
                    {pedido.piezas.map((pieza) => (
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
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}

export default MisPedidosPage
