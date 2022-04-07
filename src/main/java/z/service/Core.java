package z.service;

import org.eclipse.jetty.util.thread.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z.service.route.Method;
import z.service.route.RouteStore;
import z.service.server.WebServer;
import z.service.ssl.SslStore;

/**
 * 服务核心类
 * 不建议直接使用此类
 */
public class Core {

    private final Logger log = LoggerFactory.getLogger(Core.class);

    private Integer port = 9000;
    private String  host = "0.0.0.0";

    private Integer minThread   = 8;
    private Integer maxThread   = 200;
    private Integer idleTimeout = 60000;

    private SslStore sslStore = null;

    //是否已经初始化服务
    private boolean    initialized = false;
    //WebServer
    private WebServer  server;
    //路由仓库
    private RouteStore routeStore;

    //private String rootPath = "";

    private Core() {
    }

    private static void throwBeforeRouteMappingException() {
        throw new IllegalStateException("This must be done before route mapping has begun");
    }

    /**
     * 初始化路由仓库
     */
    private void _initRouteStore() {
        log.debug("-> 初始化路由仓库");
        routeStore = RouteStore.create();
    }

    /**
     * 初始化服务
     */
    private synchronized void init() {
        if (initialized) {
            return;
        }
        this._initRouteStore();
        new Thread(() -> {
            try {
                server = WebServer.create(routeStore);
                server.init(host, port, maxThread, minThread, idleTimeout, sslStore);
                server.join();
            } catch (Exception e) {
                log.error("server interrupted", e);
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }).start();
        initialized = true;
    }

    /**
     * 创建实例
     *
     * @return Service
     */
    public static Core create() {
        return new Core();
    }

    /**
     * 设置监听端口
     *
     * @param port 端口
     */
    public synchronized void port(int port) {
        if (initialized) {
            throwBeforeRouteMappingException();
        }
        this.port = port;
    }

    /**
     * 设置监听IP
     *
     * @param host IP
     */
    public synchronized void host(String host) {
        if (initialized) {
            throwBeforeRouteMappingException();
        }
        this.host = host;
    }

    /**
     * 设置线程池线参数
     *
     * @param maxThread   最大线程数
     * @param minThread   最小线程数
     * @param idleTimeout 线程最大空闲时间(毫秒)
     */
    public synchronized void threadPool(int maxThread, int minThread, int idleTimeout) {
        if (initialized) {
            throwBeforeRouteMappingException();
        }
        this.maxThread = maxThread;
        this.minThread = minThread;
        this.idleTimeout = idleTimeout;
    }

    /**
     * 设置线程池
     *
     * @param threadPool 线程池
     */
    public synchronized void threadPool(ThreadPool threadPool) {
        if (initialized) {
            throwBeforeRouteMappingException();
        }
        server.withThreadPool(threadPool);
    }

    /**
     * SSL配置
     *
     * @param sslStore sslStore
     */
    public synchronized void sslStore(SslStore sslStore) {
        if (initialized) {
            throwBeforeRouteMappingException();
        }
        this.sslStore = sslStore;
    }

    /**
     * 添加路由
     *
     * @param method 请求方法
     * @param path   请求路径
     * @param accept 接收类型
     * @param target 处理器
     */
    public void addRoute(Method method, String path, String accept, Handler target) {
        init();
        routeStore.add(method, path, accept, target);
    }

    /**
     * 添加批量路由
     *
     * @param path         群组路由前缀
     * @param handlerGroup 群组路由处理器
     */
    /*public void addRouteGroup(String path, HandlerGroup handlerGroup) {
        rootPath = path;
        handlerGroup.addHandle();
        rootPath = "";
    }*/

}