import React, { useState, useEffect } from 'react'
import axios from 'axios'
import '../../static/css/pedido/allPedidoOwner.css'

const PedidosOwnerPage = () => {
  const [pedidos, setPedidos] = useState([])

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('/api/v1/pedidosOwner')
        setPedidos(response.data)
      } catch (error) {
        console.error('Error al obtener los pedidos:', error)
      }
    }

    fetchData()
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
    <div className="pedido-owner-container">
      <h1>Lista de Pedidos Owner</h1>
      {pedidos.length === 0 ? (
        <p>No hay pedidos disponibles.</p>
      ) : (
        <ul className="pedido-owner-list">
          {pedidos.map((pedido) => (
            <li key={pedido.id} className="pedido-owner-item">
              <div className="pedido-owner-info">
                <p>
                  <strong>Pedido ID:</strong> {pedido.id}
                </p>
                <p>
                  <strong>Fecha de Pedido:</strong>{' '}
                  {formatFecha(pedido.fechaPedido)}
                </p>
                <p>
                  <strong>Proveedor:</strong> {pedido.proveedor}
                </p>
                <p>
                  <strong>Cantidad:</strong> {pedido.cantidad}
                </p>
              </div>
              <div className="tablero-info-container">
                <div className="tablero-info">
                  <img
                    src={pedido.tablero.img}
                    alt={`Imagen del Tablero para Pedido ${pedido.id}`}
                  />
                  <div>
                    <p>
                      <strong>Tipo de Material:</strong>{' '}
                      {pedido.tablero.tipoMaterial}
                    </p>
                    <p>
                      <strong>Color:</strong> {pedido.tablero.color}
                    </p>
                    <p>
                      <strong>Espesor:</strong> {pedido.tablero.espesor}
                    </p>
                  </div>
                </div>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}

export default PedidosOwnerPage
