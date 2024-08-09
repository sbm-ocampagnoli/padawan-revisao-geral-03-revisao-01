package br.com.sbm.backend.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.sbm.backend.model.Fruit;
import br.com.sbm.backend.repository.FruitRepository;

@Service
public class FruitService {

	@Autowired
	private FruitRepository repository;

	public List<Fruit> getAll() throws SQLException {
		return this.repository.getAll();
	}

	public ResponseEntity<Fruit> save(Fruit form) throws SQLException {
		Long idCreated = this.repository.save(form);
		if (idCreated != 0) {
			return new ResponseEntity<>(form, HttpStatus.CREATED);

		}
		return new ResponseEntity<>(form, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Fruit> update(Long id, Fruit form) throws SQLException {
		Fruit fruitUpdated = this.repository.update(id, form);

		if (fruitUpdated.getId() != 0) {
			return new ResponseEntity<>(fruitUpdated, HttpStatus.OK);
		}
		return new ResponseEntity<>(fruitUpdated, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> delete(Long id) throws SQLException {
		return this.repository.delete(id);

	}

	public List<Fruit> filterComposed(String origin, int quantity, LocalDateTime importDate) {
		return this.repository.filterComposed(origin, quantity, importDate);
	}
}
