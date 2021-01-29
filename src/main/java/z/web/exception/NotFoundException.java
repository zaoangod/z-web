package z.web.exception;

public class NotFoundException extends WebException {

    public static final  int    STATUS = 404;
    private static final String NAME   = "Not Found";

    public NotFoundException(String message) {
        super(STATUS, NAME, message);
    }

}