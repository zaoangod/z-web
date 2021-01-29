package z.web.exception;

public class ParamException extends WebException {

    public static final  int    STATUS = 500;
    private static final String NAME   = "Parameter Exception";

    public ParamException(String message) {
        super(STATUS, NAME, message);
    }

}