package com.example.demo.service;

import com.example.demo.client.dummyjson.DummyJsonClient;
import com.example.demo.client.dummyjson.DummyJsonProducto;
import com.example.demo.dto.producto.ProductoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final DummyJsonClient dummyJsonClient;

    public ProductoService(DummyJsonClient dummyJsonClient) {
        this.dummyJsonClient = dummyJsonClient;
    }

    public List<ProductoDTO> listar() {
        return dummyJsonClient.obtenerProductos().products().stream()
                .map(this::aDTO)
                .toList();
    }

    public ProductoDTO obtenerPorId(Long id) {
        return aDTO(dummyJsonClient.obtenerProductoPorId(id));
    }

    private ProductoDTO aDTO(DummyJsonProducto producto) {
        return new ProductoDTO(
                producto.id(),
                producto.title(),
                producto.description(),
                producto.category(),
                producto.brand(),
                producto.price(),
                producto.discountPercentage(),
                producto.stock(),
                producto.rating(),
                producto.thumbnail()
        );
    }
}
