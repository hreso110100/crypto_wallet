package sk.hotovo.cryptowallet.model.response;

import java.util.ArrayList;
import java.util.HashMap;

public class ValidatorError {

    private HashMap<String, String> fieldError;
    private ArrayList<HashMap<String, String>> errorsList;

    public ValidatorError() {
        this.errorsList = new ArrayList<>();
        this.fieldError = new HashMap<>();
    }

    public ArrayList<HashMap<String, String>> getErrorsList() {
        return errorsList;
    }

    public HashMap<String, String> getFieldError() {
        return fieldError;
    }

}
