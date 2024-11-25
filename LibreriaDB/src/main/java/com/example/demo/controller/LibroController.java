/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.model.Libro;
import com.example.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    // Crear un libro
    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro) {
        return libroRepository.save(libro);
    }

    // Obtener un libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibro(@PathVariable Long id) {
        return libroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Listar todos los libros
    @GetMapping("/listado")
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    // Actualizar un libro
    @PutMapping("/modifica/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroDetalles) {
        return libroRepository.findById(id)
                .map(libro -> {
                    libro.setTitulo(libroDetalles.getTitulo());
                    libro.setAutor(libroDetalles.getAutor());
                    libro.setIsbn(libroDetalles.getIsbn());
                    libro.setPublicacion(libroDetalles.getPublicacion());
                    libro.setPrecio(libroDetalles.getPrecio());
                    libroRepository.save(libro);
                    return ResponseEntity.ok(libro);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un libro
    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<Object> eliminarLibro(@PathVariable Long id) {
        return libroRepository.findById(id)
                .map(libro -> {
                    libroRepository.delete(libro);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
