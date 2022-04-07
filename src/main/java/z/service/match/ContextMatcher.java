package z.service.match;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z.service.Request;
import z.service.Response;
import z.service.route.RouteContext;
import z.service.route.RouteStore;

public class ContextMatcher {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String ACCEPT_TYPE_REQUEST_MIME_HEADER = "Accept";
    private static final String HTTP_METHOD_OVERRIDE_HEADER     = "X-HTTP-Method-Override";

    private final RouteStore routeStore;

    public ContextMatcher(RouteStore routeStore) {
        this.routeStore = routeStore;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("ContextMatcher => handle");
        String method = _httpMethod(request);
        String path   = request.getRequestURI();
        String accept = request.getHeader(ACCEPT_TYPE_REQUEST_MIME_HEADER);
        //
        RouteContext context = RouteContext.create()
                .httpServletRequest(request)
                .HttpServletResponse(response)
                .path(path)
                .accept(accept)
                .method(method)
                .request(Request.create(request))
                .response(Response.create(response));
        //过滤路由
        HandlerInvoke.routeExecute(routeStore, context);

    }

    private String _httpMethod(HttpServletRequest httpRequest) {
        String method = httpRequest.getHeader(HTTP_METHOD_OVERRIDE_HEADER);
        if (method == null) {
            method = httpRequest.getMethod();
        }
        return method.toLowerCase();
    }

}
