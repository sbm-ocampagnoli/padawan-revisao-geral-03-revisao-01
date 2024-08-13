package br.com.sbm.backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import br.com.sbm.backend.controller.form.FruitDTO;
import br.com.sbm.backend.model.Fruit;

@Repository
public class FruitRepository {

	@Autowired
	private Connection connection;

	@Autowired
	public FruitRepository(DataSource dataSource) throws SQLException {
		this.connection = dataSource.getConnection();
	}

	public List<Fruit> getAll() throws SQLException {
		List<Fruit> fruits = new ArrayList<Fruit>();

		String sql = "SELECT id, quantity, origin, importDate FROM fruit";

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.execute();

			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					Fruit fruit = new Fruit();
					fruit.setId(rst.getLong("id"));
					fruit.setQuantity(rst.getInt("quantity"));
					fruit.setOrigin(rst.getString("origin"));
					LocalDateTime importDate = rst.getObject("importDate", LocalDateTime.class);
					fruit.setImportDate(importDate);

					fruits.add(fruit);
				}
			} catch (Exception e) {
				throw new SQLException(e);
			}
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return fruits;
	}

	public Fruit save(FruitDTO form) throws SQLException {
		String sql = "INSERT INTO fruit (quantity, origin, importDate) " + "VALUES (?, ?, ?)";
		Fruit fruitEdited = new Fruit();
		try (PreparedStatement pstm = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			pstm.setInt(1, form.getQuantity());
			pstm.setString(2, form.getOrigin());
			pstm.setObject(3, form.getImportDate());

			pstm.execute();

			try (ResultSet rst = pstm.getGeneratedKeys()) {
				if (rst.next()) {
					fruitEdited.setId(rst.getLong(1));
				}
			} catch (Exception e) {
				throw new SQLException("Erro ao obter a chave gerada", e);
			}

		} catch (Exception e) {
			throw new SQLException(e);
		}
		return fruitEdited;
	}

	public Fruit update(Long id, FruitDTO form) throws SQLException {

		String sql = "UPDATE fruit SET quantity = ?, origin = ?, importDate = ? WHERE id = ?";
		Fruit fruitUpdated = new Fruit();

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setInt(1, form.getQuantity());
			pstm.setString(2, form.getOrigin());
			pstm.setObject(3, form.getImportDate());
			pstm.setLong(4, id);

			pstm.execute();

			int rowsAffected = (pstm.executeUpdate());
			if (rowsAffected == 0) {
				throw new SQLException("Erro ao atualizar a Fruta");

			}
			fruitUpdated.setId(id);
			fruitUpdated.setQuantity(form.getQuantity());
			fruitUpdated.setOrigin(form.getOrigin());
			fruitUpdated.setImportDate(form.getImportDate());

		} catch (Exception e) {
			throw new SQLException("Erro ao preparar a atualização", e);
		}
		return fruitUpdated;
	}

	public ResponseEntity<?> delete(Long id) throws SQLException {
		String sql = "DELETE FROM fruit WHERE id = ?";

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setLong(1, id);
			pstm.execute();
			return new ResponseEntity<>(id, HttpStatus.OK);
		} catch (Exception e) {
			new SQLException(e);
			return new ResponseEntity<>(id, HttpStatus.BAD_REQUEST);
		}
	}

	public List<Fruit> filterComposed(String origin, Integer quantity, LocalDateTime initialImportDate,
			LocalDateTime finalImportDate) {
		List<Fruit> fruits = new ArrayList<Fruit>();

		String sql = "SELECT id, quantity, origin, importDate FROM fruit WHERE 1=1";

		if (!origin.isEmpty() && origin != null) {
			sql += " AND origin LIKE ?";
		}

		if (quantity != null && quantity > 0) {
			sql += " AND quantity = ?";
		}

		if (finalImportDate != null && initialImportDate != null) {
			sql += " AND importDate BETWEEN ? AND ?";
			System.out.println(sql);
		} else if (initialImportDate != null) {
			sql += " AND importDate = ?";
		}

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {

			int paramIndex = 1;

			if (!origin.isEmpty() && origin != null) {
				pstm.setString(paramIndex++, "%" + origin + "%");
			}

			if (quantity != null && quantity > 0) {
				pstm.setInt(paramIndex++, quantity);
			}

			if (initialImportDate != null && finalImportDate != null) {
				pstm.setObject(paramIndex++, initialImportDate.withSecond(0).withNano(0));
				pstm.setObject(paramIndex++, finalImportDate.withSecond(0).withNano(0));
			} else if (initialImportDate != null) {
				pstm.setObject(paramIndex++, initialImportDate.withSecond(0).withNano(0));
			}

			pstm.execute();

			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					Fruit fruit = new Fruit();
					fruit.setId(rst.getLong("id"));
					fruit.setOrigin(rst.getString("origin"));
					fruit.setQuantity(rst.getInt("quantity"));
					LocalDateTime importDateObj = rst.getObject("importDate", LocalDateTime.class);
					fruit.setImportDate(importDateObj);

					fruits.add(fruit);
				}
			} catch (Exception e) {
				new SQLException(e);
			}

		} catch (Exception e) {
			new SQLException(e);
		}
		return fruits;
	}
}
