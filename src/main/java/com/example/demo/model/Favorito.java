package com.example.demo.model;

import java.time.LocalDateTime;

/**
 * Entidad de dominio de favorito. No tiene anotaciones de JPA a propósito:
 * la persistencia vive en el adapter (FavoritoEntity), el dominio no sabe
 * si se guarda en memoria o en PostgreSQL.
 */
public class Favorito {

    private Long id;
    private Long productoId;
    private String nombreProducto;
    private String comentario;
    private LocalDateTime fechaAgregado;
    private Long listaId;

    public Favorito() {
    }

    public Favorito(Long id, Long productoId, String nombreProducto, String comentario, LocalDateTime fechaAgregado, Long listaId) {
        this.id = id;
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.comentario = comentario;
        this.fechaAgregado = fechaAgregado;
        this.listaId = listaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public Long getListaId() {
        return listaId;
    }

    public void setListaId(Long listaId) {
        this.listaId = listaId;
    }
}
