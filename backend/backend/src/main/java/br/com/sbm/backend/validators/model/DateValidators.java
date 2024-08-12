package br.com.sbm.backend.validators.model;

import java.time.LocalDateTime;

public interface DateValidators {

	public boolean initialDateIsLessThenFinalDate(LocalDateTime initialDate, LocalDateTime finalDate);
	
	public boolean initialDateAndFinalDateIsNull(LocalDateTime initialDate, LocalDateTime finalDate);
}
