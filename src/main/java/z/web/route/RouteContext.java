package z.web.route;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import z.web.Request;
import z.web.Response;

public final class RouteContext {

    private HttpServletRequest  httpServletRequest;
    private HttpServletResponse httpServletResponse;
    //
    private String              path;
    private String              accept;
    private String              method;
    //
    private Request             request;
    private Response            response;

    private RouteContext() {
    }

    public static RouteContext create() {
        return new RouteContext();
    }

    //-----------------------------get-----------------------------
    public HttpServletRequest httpServletRequest() {
        return httpServletRequest;
    }

    public RouteContext httpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        return this;
    }

    public HttpServletResponse httpServletResponse() {
        return httpServletResponse;
    }

    public RouteContext HttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
        return this;
    }

    public String path() {
        return path;
    }

    public RouteContext path(String path) {
        this.path = path;
        return this;
    }

    public String accept() {
        return accept;
    }

    public RouteContext accept(String accept) {
        this.accept = accept;
        return this;
    }

    public Request request() {
        return request;
    }

    public RouteContext request(Request request) {
        this.request = request;
        return this;
    }

    public Response response() {
        return response;
    }

    public RouteContext response(Response response) {
        this.response = response;
        return this;
    }

    public String method() {
        return method;
    }

    public RouteContext method(String method) {
        this.method = method;
        return this;
    }

}
