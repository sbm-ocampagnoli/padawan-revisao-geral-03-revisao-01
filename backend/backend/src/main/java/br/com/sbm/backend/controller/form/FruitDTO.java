package br.com.sbm.backend.controller.form;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FruitDTO {

	@NotNull(message = "A quantidade é obrigatória!")
	@Min(value = 1, message = "A quantidade deve ser maior que 0!")
	private Integer quantity;

	@NotNull(message = "A origem é obrigatória!")
    @Size(min = 2, max = 255, message = "A origem deve ter entre 2 e 255 caracteres!")
	private String origin;

	@NotNull(message = "A data da importação é obrigatória!")
	private LocalDateTime importDate;

	public FruitDTO() {
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public LocalDateTime getImportDate() {
		return importDate;
	}

	public void setImportDate(LocalDateTime importDate) {
		this.importDate = importDate;
	}

}
