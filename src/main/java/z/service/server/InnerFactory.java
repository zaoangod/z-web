package z.service.server;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import z.service.kit.Assert;
import z.service.ssl.SslStore;

import java.util.concurrent.TimeUnit;

/**
 * 创建 Jetty Server 实例
 */
public final class InnerFactory {

    private InnerFactory() {
    }

    /**
     * ServerFactory
     */
    protected static final class ServerFactory {

        private ServerFactory() {
        }

        /**
         * 创建指定线程的服务器
         *
         * @param threadPool 线程池
         * @return jetty server 实例
         */
        public static Server create(ThreadPool threadPool) {
            Server server = threadPool != null ? new Server(threadPool) : new Server();
            server.setStopAtShutdown(true);
            return server;
        }

        /**
         * 创建服务器
         *
         * @param maxThread   最大线程数
         * @param minThread   最小线程数
         * @param idleTimeout 线程超时(毫秒)
         * @return jetty server 实例
         */
        public static Server create(int maxThread, int minThread, int idleTimeout) {
            ThreadPool pool = new QueuedThreadPool(maxThread, minThread, idleTimeout);
            return create(pool);
        }

        /**
         * 创建服务器
         *
         * @return jetty server 实例
         */
        public static Server create() {
            return create(null);
        }

    }

    /**
     * ServerConnectorFactory
     */
    protected static final class ServerConnectorFactory {

        private ServerConnectorFactory() {
        }

        /**
         * 创建连接器
         *
         * @param server             server
         * @param host               host
         * @param port               port
         * @param trustForwardHeader trustForwardHeader
         * @param sslStore           sslStore
         * @return ServerConnector
         */
        public static ServerConnector createServerConnector(Server server, String host, int port, SslStore sslStore) {
            Assert.notNull(server, () -> new IllegalArgumentException("'server' must not be null"));
            Assert.notNull(host, () -> new IllegalArgumentException("'host' must not be null"));
            //connection
            HttpConnectionFactory httpConnectionFactory = _createHttpConnectionFactory();
            ServerConnector       connector;
            if (sslStore == null) {
                connector = new ServerConnector(server, httpConnectionFactory);
            } else {
                SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
                if (sslStore.keyStoreFile() != null) {
                    sslContextFactory.setKeyStorePath(sslStore.keyStoreFile());
                }
                if (sslStore.keyStorePassword() != null) {
                    sslContextFactory.setKeyStorePassword(sslStore.keyStorePassword());
                }
                if (sslStore.certAlias() != null) {
                    sslContextFactory.setCertAlias(sslStore.certAlias());
                }
                if (sslStore.trustStoreFile() != null) {
                    sslContextFactory.setTrustStorePath(sslStore.trustStoreFile());
                }
                if (sslStore.trustStorePassword() != null) {
                    sslContextFactory.setTrustStorePassword(sslStore.trustStorePassword());
                }
                if (sslStore.needClientAuth()) {
                    sslContextFactory.setNeedClientAuth(true);
                    sslContextFactory.setWantClientAuth(true);
                }
                connector = new ServerConnector(server, sslContextFactory, httpConnectionFactory);
            }
            _initializeConnector(connector, host, port);
            return connector;
        }

        private static void _initializeConnector(ServerConnector connector, String host, int port) {
            connector.setReuseAddress(false);
            // Set some timeout options to make debugging easier.
            connector.setIdleTimeout(TimeUnit.HOURS.toMillis(1));
            connector.setHost(host);
            connector.setPort(port);
        }

        private static HttpConnectionFactory _createHttpConnectionFactory() {
            HttpConfiguration httpConfig = new HttpConfiguration();
            httpConfig.addCustomizer(new ForwardedRequestCustomizer());
            //不发送版本号
            httpConfig.setSendServerVersion(false);
            return new HttpConnectionFactory(httpConfig);
        }

    }

}
