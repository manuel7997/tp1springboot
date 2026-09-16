package com.example.demo.dto.producto;

/**
 * Representación propia de un producto, la que efectivamente devuelve
 * /api/productos. A diferencia de DummyJsonProducto (que vive en el
 * paquete "client" y nunca sale de ahí), acá los nombres de campo son los
 * de esta API, no los del proveedor externo.
 */
public record ProductoDTO(
        Long id,
        String nombre,
        String descripcion,
        String categoria,
        String marca,
        double precio,
        double porcentajeDescuento,
        int stock,
        double calificacion,
        String imagen
) {
}
