package sk.hotovo.cryptowallet.validator.exceptions;

public class MissingMapKeyException extends Exception {

    private String missingKey;
    private String message;

    public MissingMapKeyException(String missingKey) {
        this.missingKey = missingKey;
        this.message = "must not be empty.";
    }

    public String getMissingKey() {
        return missingKey;
    }

    public String getMessage() {
        return message;
    }

}
