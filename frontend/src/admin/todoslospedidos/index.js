import React, { useState, useEffect } from 'react'
import axios from 'axios'
import tokenService from '../../services/token.service'
import '../../static/css/pedido/Material/pedidos.css'

const PedidosPage = () => {
  const [pedidos, setPedidos] = useState([])
  const [nuevoEstado, setNuevoEstado] = useState('')

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

  const handleEstadoChange = async (pedidoId) => {
    const reqBody = {
      nuevoEstado: nuevoEstado,
    }
    try {
      console.log(reqBody)
      await axios.put(
        `/api/v1/pedidos/${pedidoId}/estado`,
        JSON.stringify(reqBody),
        {
          headers: {
            Authorization: `Bearer ${tokenService.getLocalAccessToken()}`,
            'Content-Type': 'application/json',
          },
        }
      )

      // Actualizar la lista de pedidos después de cambiar el estado
      const updatedPedidos = await axios.get('/api/v1/pedidos', {
        headers: {
          Authorization: `Bearer ${tokenService.getLocalAccessToken()}`,
        },
      })
      setPedidos(updatedPedidos.data)

      // Limpiar el estado después de la actualización
      setNuevoEstado('')
    } catch (error) {
      console.error('Error al actualizar el estado del pedido:', error)
    }
  }

  useEffect(() => {
    const fetchPedidos = async () => {
      try {
        const response = await axios.get('/api/v1/pedidos', {
          headers: {
            Authorization: `Bearer ${tokenService.getLocalAccessToken()}`,
          },
        })
        setPedidos(response.data)
      } catch (error) {
        console.error('Error al obtener los pedidos:', error)
      }
    }

    fetchPedidos()
  }, [])

  const obtenerEstadoLegible = (estado) => {
    switch (estado) {
      case 'INCOMPLETO':
        return 'Incompleto'
      case 'ENPRODUCCION':
        return 'En Producción'
      case 'LISTOPARARECOGIDA':
        return 'Listo para Recogida'
      case 'ENTREGADO':
        return 'Entregado'
      default:
        return 'Desconocido'
    }
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
                  <p>Pedido de: {pedido.user.username}</p>
                  <p>Fecha de Creación: {formatFecha(pedido.fechaPedido)}</p>
                </div>

                {pedido.piezas.length > 0 && (
                  <div className="piezas-list">
                    <p>
                      Ids de las piezas:{' '}
                      {pedido.piezas.map((pieza) => pieza.id).join(', ')}
                    </p>
                  </div>
                )}

                <div className="pedido-info">
                  <p>Estado Actual: {obtenerEstadoLegible(pedido.estado)}</p>
                </div>

                {/* Desplegable para cambiar el estado */}
                <div>
                  <label htmlFor={`estadoSelect-${pedido.id}`}>
                    Cambiar Estado:
                  </label>
                  <select
                    id={`estadoSelect-${pedido.id}`}
                    value={nuevoEstado}
                    onChange={(e) => setNuevoEstado(e.target.value)}
                    className="estado-select"
                  >
                    <option value={null} hidden>
                      Selecciona un estado
                    </option>
                    <option value="INCOMPLETO">Incompleto</option>
                    <option value="ENPRODUCCION">En Producción</option>
                    <option value="LISTOPARARECOGIDA">
                      Listo para Recogida
                    </option>
                    <option value="ENTREGADO">Entregado</option>
                  </select>
                  <button
                    onClick={() => handleEstadoChange(pedido.id)}
                    className="estado-btn"
                  >
                    Actualizar Estado
                  </button>
                </div>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}

export default PedidosPage
