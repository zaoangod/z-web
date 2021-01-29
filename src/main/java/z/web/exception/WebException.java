package z.web.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WebException extends RuntimeException {

    protected int    status;
    protected String name;

    public WebException(Throwable cause) {
        super(cause);
    }

    public WebException(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public WebException(int status, String name, String message) {
        super(message);
        this.status = status;
        this.name = name;
    }

    public static WebException wrapper(Exception e) {
        return new WebException(e);
    }
}