package br.com.sbm.backend.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.sbm.backend.exceptions.InvalidDateRangeException;
import br.com.sbm.backend.model.Fruit;
import br.com.sbm.backend.repository.FruitRepository;
import br.com.sbm.backend.validators.LocalDateTimeValidators;

@Service
public class FruitService {

	private LocalDateTimeValidators localDateTimeValidators;

	@Autowired
	private FruitRepository repository;

	@Autowired
	public FruitService(LocalDateTimeValidators localDateTimeValidators) {
		this.localDateTimeValidators = localDateTimeValidators;
	}

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

	public List<Fruit> filterComposed(String origin, Integer quantity, LocalDateTime initialImportDate,
			LocalDateTime finalImportDate) throws Exception {

		if (!localDateTimeValidators.initialDateAndFinalDateIsNull(initialImportDate, finalImportDate)) {
			if (!localDateTimeValidators.initialDateIsLessThenFinalDate(initialImportDate, finalImportDate)) {
				throw new InvalidDateRangeException("Data Final não pode ser maior que a Data Inicial");
			}
		}
		return this.repository.filterComposed(origin, quantity, initialImportDate, finalImportDate);
	}
}
