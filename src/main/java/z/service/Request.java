package z.service;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Request {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //HttpServletRequest
    private HttpServletRequest request;

    private Request(HttpServletRequest request) {
        this.request = request;
    }

    //------------create------------
    public static Request create(HttpServletRequest request) {
        return new Request(request);
    }
    //------------create------------

    //------------------------------------------------
    public HttpServletRequest request() {
        return request;
    }

    public Request request(HttpServletRequest request) {
        this.request = request;
        return this;
    }

}
