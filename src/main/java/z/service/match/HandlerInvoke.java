package z.service.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z.service.Handler;
import z.service.kit.Assert;
import z.service.route.*;

import java.util.Optional;

public final class HandlerInvoke {

    private static final Logger log = LoggerFactory.getLogger(HandlerInvoke.class);

    public static void routeExecute(RouteStore routeStore, RouteContext context) throws Exception {
        log.debug("HandlerInvoke => routeExecute");
        Assert.notNull(routeStore, () -> new RuntimeException("RouteStore cannot be null"));
        Assert.notNull(context, () -> new RuntimeException("RouteContext cannot be null"));

        String path   = context.path();
        String method = context.method();
        String accept = context.accept();

        log.debug("path:   {}", path);
        log.debug("method: {}", method);
        log.debug("accept: {}", accept);

        //查询路由对象
        RouteInfo routeInfo = routeStore.find(Method.get(method), path, accept);

        log.debug("RouteInfo: {}", routeInfo);

        //处理器
        Handler target = (Handler) Optional.ofNullable(routeInfo).map(RouteInfo::target).filter(a -> a instanceof Handler).orElse(null);

        if (target != null) {
            Object result = target.handle(context.request(), context.response());
            if (result instanceof String && ((String) result).length() != 0) {
                context.httpServletResponse().getWriter().print(result);
            }
        }
    }

}
