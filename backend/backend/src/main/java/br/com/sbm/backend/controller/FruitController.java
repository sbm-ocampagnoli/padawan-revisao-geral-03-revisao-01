package br.com.sbm.backend.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sbm.backend.model.Fruit;
import br.com.sbm.backend.service.FruitService;

@RestController
@RequestMapping("/fruits")
public class FruitController {

	@Autowired
	private FruitService service;

	@GetMapping
	public List<Fruit> getAll() throws SQLException {
		return this.service.getAll();
	}

	@GetMapping("/filter")
	public List<Fruit> filterComposed
	(
	@RequestParam(required = false, defaultValue = "") String origin,
	@RequestParam(required = false, defaultValue = "") int quantity,
	@RequestParam(required = false, defaultValue = "") LocalDateTime importDate
	) {
		return this.service.filterComposed(origin, quantity, importDate);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<Fruit> save(@RequestBody Fruit form) throws SQLException {
		return this.service.save(form);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Fruit> update(@PathVariable Long id, @RequestBody Fruit form) throws SQLException {
		return this.service.update(id, form);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
		return this.service.delete(id);
	}

}
