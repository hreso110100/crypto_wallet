package sk.hotovo.cryptowallet.validator;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sk.hotovo.cryptowallet.model.response.Response;
import sk.hotovo.cryptowallet.model.response.ResponseCode;
import sk.hotovo.cryptowallet.model.response.ValidatorError;
import sk.hotovo.cryptowallet.validator.exceptions.MissingMapKeyException;

@RestControllerAdvice
public class ModelValidator {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationExceptions(MethodArgumentNotValidException ex) {

        ValidatorError modelValidatorError = new ValidatorError();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            modelValidatorError.getFieldError().put(fieldName, errorMessage);
        });

        modelValidatorError.getErrorsList().add(modelValidatorError.getFieldError());

        return new Response<>(ResponseCode.ERROR, modelValidatorError.getErrorsList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingMapKeyException.class)
    public Response handleValidationExceptions(MissingMapKeyException ex) {

        ValidatorError modelValidatorError = new ValidatorError();

        modelValidatorError.getFieldError().put(ex.getMissingKey(), ex.getMessage());
        modelValidatorError.getErrorsList().add(modelValidatorError.getFieldError());

        return new Response<>(ResponseCode.ERROR, modelValidatorError.getErrorsList());
    }

}
