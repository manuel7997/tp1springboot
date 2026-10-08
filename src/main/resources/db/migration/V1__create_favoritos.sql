CREATE TABLE favoritos (
    id              BIGSERIAL    PRIMARY KEY,
    producto_id     BIGINT       NOT NULL,
    nombre_producto VARCHAR(255) NOT NULL,
    comentario      VARCHAR(500),
    fecha_agregado  TIMESTAMP    NOT NULL
);
