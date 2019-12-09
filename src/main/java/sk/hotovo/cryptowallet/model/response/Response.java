package sk.hotovo.cryptowallet.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Response<T> {

    private ResponseCode statusMessage;
    private T content;

    public Response(ResponseCode statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Response(ResponseCode statusMessage, T content) {
        this.statusMessage = statusMessage;
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public ResponseCode getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(ResponseCode statusMessage) {
        this.statusMessage = statusMessage;
    }

}
