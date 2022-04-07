package z.service;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Response {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //HttpServletResponse
    private HttpServletResponse response;

    private Response(HttpServletResponse response) {
        this.response = response;
    }

    //------------create------------
    public static Response create(HttpServletResponse response) {
        return new Response(response);
    }

    //------------------------------------------------
    public HttpServletResponse response() {
        return response;
    }

    public Response response(HttpServletResponse response) {
        this.response = response;
        return this;
    }

}
