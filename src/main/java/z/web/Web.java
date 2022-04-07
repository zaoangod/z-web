package z.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z.web.route.Method;
import z.web.ssl.SslStore;

public class Web {

    private static final Logger log = LoggerFactory.getLogger(Web.class);

    private Web() {
    }

    //初始化单例服务
    private static class SingletonCore {

        private static final Core INSTANCE = Core.create();

    }

    public static Core instance() {
        return SingletonCore.INSTANCE;
    }

    /**
     * 设置监听端口
     *
     * @param port 端口
     * @return this
     */
    public synchronized Web port(int port) {
        instance().port(port);
        return this;
    }

    /**
     * 设置监听IP
     *
     * @param host IP
     * @return this
     */
    public synchronized Web host(String host) {
        instance().host(host);
        return this;
    }

    /**
     * 设置线程池线参数
     *
     * @param maxThread   最大线程数
     * @param minThread   最小线程数
     * @param idleTimeout 线程最大空闲时间(毫秒)
     * @return this
     */
    public synchronized Web threadPool(int maxThread, int minThread, int idleTimeout) {
        instance().threadPool(maxThread, minThread, idleTimeout);
        return this;
    }

    /**
     * SSL配置
     *
     * @param sslStore sslStore
     * @return this
     */
    public synchronized Web sslStore(SslStore sslStore) {
        instance().sslStore(sslStore);
        return this;
    }

    /**
     * 无接受类型请求
     * get, post, put, PATCH, DELETE, HEAD, TRACE, CONNECT, OPTIONS
     *
     * @param path    请求路径
     * @param handler 处理器
     */
    public static void get(final String path, final Handler handler) {
        Web.get(path, "*/*", handler);
    }

    public static void post(final String path, final Handler handler) {
        Web.post(path, "*/*", handler);
    }

    public static void put(final String path, final Handler handler) {
        Web.put(path, "*/*", handler);
    }

    public static void patch(final String path, final Handler handler) {
        Web.patch(path, "*/*", handler);
    }

    public static void delete(final String path, final Handler handler) {
        Web.delete(path, "*/*", handler);
    }

    public static void head(final String path, final Handler handler) {
        Web.head(path, "*/*", handler);
    }

    public static void trace(final String path, final Handler handler) {
        Web.trace(path, "*/*", handler);
    }

    public static void connect(final String path, final Handler handler) {
        Web.connect(path, "*/*", handler);
    }

    public static void options(final String path, final Handler handler) {
        Web.options(path, "*/*", handler);
    }

    /**
     * 有接受类型请求
     * get, post, put, patch, delete, head, trace, connect, options
     *
     * @param path    请求路径
     * @param handler 处理器
     */
    public static void get(final String path, final String accept, final Handler handler) {
        instance().addRoute(Method.get, path, accept, handler);
    }

    public static void post(final String path, final String accept, final Handler handler) {
        instance().addRoute(Method.post, path, accept, handler);
    }

    public static void put(final String path, final String accept, final Handler handler) {
        instance().addRoute(Method.put, path, accept, handler);
    }

    public static void patch(final String path, final String accept, final Handler handler) {
        instance().addRoute(Method.patch, path, accept, handler);
    }

    public static void delete(final String path, final String accept, final Handler handler) {
        instance().addRoute(Method.delete, path, accept, handler);
    }

    public static void head(final String path, final String accept, final Handler handler) {
        instance().addRoute(Method.head, path, accept, handler);
    }

    public static void trace(final String path, final String accept, final Handler handler) {
        instance().addRoute(Method.trace, path, accept, handler);
    }

    public static void connect(final String path, final String accept, final Handler handler) {
        instance().addRoute(Method.connect, path, accept, handler);
    }

    public static void options(final String path, final String accept, final Handler handler) {
        instance().addRoute(Method.options, path, accept, handler);
    }

    /**
     * 组路由处理
     *
     * @param path         路由前缀
     * @param handlerGroup 群组路由处理器
     */
    /*public static void path(String path, HandlerGroup handlerGroup) {
        instance().addRouteGroup(path, handlerGroup);
    }*/
    public static void main(String[] args) {
        Web.get("/", (a, b) -> {
            log.info("-> 测试路由");
            return "测试路由A";
        });
        Web.get("/index", (a, b) -> {
            log.info("-> 测试路由index");
            return "测试路由B";
        });
        //
        /*Web.path("/index", () -> {
            Web.get("/", (a, b) -> {
                log.info("-> 测试路由");
                return "测试路由A";
            });
            Web.get("/", (a, b) -> {
                log.info("-> 测试路由");
                return "测试路由A";
            });
            Web.path("/index", () -> {
                Web.get("/", (a, b) -> {
                    log.info("-> 测试路由");
                    return "测试路由A";
                });
                Web.get("/", (a, b) -> {
                    log.info("-> 测试路由");
                    return "测试路由A";
                });
            });
        });*/
    }

}