package z.service.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z.service.kit.Assert;
import z.service.route.RouteStore;
import z.service.server.handle.ContentHandler;
import z.service.ssl.SslStore;

public class WebServer {

    private final Logger  log = LoggerFactory.getLogger(this.getClass());
    //
    private final Handler handler;

    //
    private Server     server;
    private ThreadPool threadPool;

    /**
     * 私有构造函数
     */
    private WebServer(RouteStore routeStore) {
        this.handler = new ContentHandler(routeStore);
    }

    /**
     * 创建实例
     *
     * @return WebServer
     */
    public static WebServer create(RouteStore routeStore) {
        return new WebServer(routeStore);
    }

    public void init(String host, int port, int maxThread, int minThread, int idleTimeout, SslStore sslStore) throws Exception {
        //创建 Jetty 服务器
        if (threadPool == null) {
            server = InnerFactory.ServerFactory.create(maxThread, minThread, idleTimeout);
        } else {
            server = InnerFactory.ServerFactory.create(threadPool);
        }
        //创建连接器
        ServerConnector connector = InnerFactory.ServerConnectorFactory.createServerConnector(server, host, port, sslStore);
        server = connector.getServer();
        server.setConnectors(new Connector[]{connector});
        //
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setHandler(handler);
        /*SessionHandler sessionHandler = contextHandler.getSessionHandler();
        log.info("{}", sessionHandler);*/
        server.setHandler(contextHandler);
        //
        server.start();
        log.info("=> http://localhost:" + connector.getPort());
    }

    public void join() throws Exception {
        Assert.notNull(server, () -> new RuntimeException("join failed. server is not null"));
        server.join();
        log.info("=> stop done");
    }

    public void stop() throws Exception {
        Assert.notNull(server, () -> new RuntimeException("stop failed. server is not null"));
        server.stop();
        log.info("=> stop done");
    }

    /**
     * 指定线程池
     *
     * @param threadPool 线程池
     * @return 实例
     */
    public WebServer withThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
        return this;
    }

}
