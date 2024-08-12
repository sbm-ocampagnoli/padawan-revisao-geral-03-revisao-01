package br.com.sbm.backend.validators;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import br.com.sbm.backend.validators.model.DateValidators;

@Component
public class LocalDateTimeValidators implements DateValidators {


	@Override
	public boolean initialDateIsLessThenFinalDate(LocalDateTime initialDate, LocalDateTime finalDate) {
		return initialDate.isBefore(finalDate);
	}


	@Override
	public boolean initialDateAndFinalDateIsNull(LocalDateTime initialDate, LocalDateTime finalDate) {
		return (initialDate == null && finalDate == null) ? true : false;
	}

}
