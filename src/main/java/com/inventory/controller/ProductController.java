package com.inventory.controller;

import com.inventory.model.Product;
import com.inventory.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    // GET all products  — public (see SecurityConfig)
    @GetMapping
    public List<Product> getAll() {
        return repo.findAll();
    }

    // GET by id  — public
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET by category  — public
    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable String category) {
        return repo.findByCategory(category);
    }

    // GET low-stock (quantity < threshold)  — requires auth
    @GetMapping("/low-stock")
    public List<Product> lowStock(@RequestParam(defaultValue = "5") int threshold) {
        return repo.findByQuantityLessThan(threshold);
    }

    // POST create  — requires auth
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody Product product) {
        return repo.save(product);
    }

    // PUT update  — requires auth
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id,
                                          @Valid @RequestBody Product updated) {
        Optional<Product> opt = repo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Product p = opt.get();
        p.setName(updated.getName());
        p.setCategory(updated.getCategory());
        p.setPrice(updated.getPrice());
        p.setQuantity(updated.getQuantity());
        p.setDescription(updated.getDescription());
        return ResponseEntity.ok(repo.save(p));
    }

    // DELETE  — requires auth
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
