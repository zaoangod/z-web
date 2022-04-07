package z.service.server.handle;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z.service.match.ContextMatcher;
import z.service.route.RouteStore;

public class ContentHandler extends AbstractHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final static String DEFAULT_CHARSET_UTF_8 = "utf-8";
    private final static String DEFAULT_CONTENT_TYPE  = "*/*";

    private final ContextMatcher contextMatcher;

    public ContentHandler(RouteStore routeStore) {
        this.contextMatcher = new ContextMatcher(routeStore);
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding(DEFAULT_CHARSET_UTF_8);
            response.setCharacterEncoding(DEFAULT_CHARSET_UTF_8);
            ContextMatcher(target, baseRequest, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    private void ContextMatcher(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("target: {}", target);
        log.info("RequestURL: {}", request.getRequestURL());
        log.info("Method: {}", request.getMethod());

        contextMatcher.handle(request, response);

        String result = "{Method:" + request.getMethod() + "," + "URL:" + request.getRequestURL() + "}";
        log.debug("Result: {}", result);

        if (response.getContentType() == null) {
            response.setContentType(DEFAULT_CONTENT_TYPE);
        }
        baseRequest.setHandled(true);
    }

}
