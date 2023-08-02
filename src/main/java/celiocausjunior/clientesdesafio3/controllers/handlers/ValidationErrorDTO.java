package celiocausjunior.clientesdesafio3.controllers.handlers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import celiocausjunior.clientesdesafio3.models.dtos.CustomErrorDTO;

public class ValidationErrorDTO extends CustomErrorDTO {

    private List<FieldMessageDTO> errors = new ArrayList<>();

    public ValidationErrorDTO(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessageDTO> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessageDTO(fieldName, message));
    }

}
