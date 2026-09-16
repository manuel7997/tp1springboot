package com.example.demo.client.dummyjson;

import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.exception.ServicioExternoException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * Único punto de contacto con la API de DummyJSON. Usa el RestClient ya
 * configurado en {@link com.example.demo.config.RestClientConfig} y traduce
 * cualquier problema de red/HTTP a las excepciones propias de la app
 * (RecursoNoEncontradoException / ServicioExternoException), para que el
 * resto de la aplicación no dependa de las excepciones de RestClient.
 */
@Component
public class DummyJsonClient {

    private final RestClient restClient;

    public DummyJsonClient(RestClient dummyJsonRestClient) {
        this.restClient = dummyJsonRestClient;
    }

    public DummyJsonProductosResponse obtenerProductos() {
        try {
            return restClient.get()
                    .uri("/products")
                    .retrieve()
                    .body(DummyJsonProductosResponse.class);
        } catch (RestClientException ex) {
            throw new ServicioExternoException(
                    "No se pudo obtener el listado de productos desde DummyJSON", ex);
        }
    }

    public DummyJsonProducto obtenerProductoPorId(Long id) {
        try {
            return restClient.get()
                    .uri("/products/{id}", id)
                    .retrieve()
                    .body(DummyJsonProducto.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RecursoNoEncontradoException("No existe un producto con id " + id);
        } catch (RestClientException ex) {
            throw new ServicioExternoException(
                    "No se pudo obtener el producto " + id + " desde DummyJSON", ex);
        }
    }
}
