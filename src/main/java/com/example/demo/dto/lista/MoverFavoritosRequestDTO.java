package com.example.demo.dto.lista;

import jakarta.validation.constraints.NotNull;

public record MoverFavoritosRequestDTO(

        @NotNull(message = "El id de la lista destino es obligatorio")
        Long listaDestinoId
) {
}
