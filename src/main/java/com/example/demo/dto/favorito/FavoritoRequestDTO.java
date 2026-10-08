package com.example.demo.dto.favorito;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Datos que llegan del cliente al crear o actualizar un favorito. Las
 * violaciones de estas anotaciones las traduce a un 400 el
 * GlobalExceptionHandler ya existente. Como productoId, la lista se
 * referencia por id: el dominio no navega objetos completos.
 */
public record FavoritoRequestDTO(

        @NotNull(message = "El id del producto es obligatorio")
        Long productoId,

        @NotBlank(message = "El nombre del producto es obligatorio")
        String nombreProducto,

        String comentario,

        @NotNull(message = "El id de la lista es obligatorio")
        Long listaId
) {
}
