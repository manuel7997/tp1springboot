package com.example.demo.model;

/**
 * Entidad interna de favorito. No tiene anotaciones de JPA a propósito:
 * se persiste en memoria a través de FavoritoRepository, no en una base
 * de datos.
 */
public class Favorito {

    private Long id;
    private Long productoId;
    private String nombreProducto;
    private String comentario;

    public Favorito() {
    }

    public Favorito(Long id, Long productoId, String nombreProducto, String comentario) {
        this.id = id;
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.comentario = comentario;
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
}
