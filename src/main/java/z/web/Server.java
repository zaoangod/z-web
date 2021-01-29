package z.web;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import z.web.server.HttpServerInitializer;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Server implements AutoCloseable {

    //初始化配置
    private Map<String, String> config = new ConcurrentHashMap<>();

    //是否初始化
    private boolean    initialized = false;
    //默认端口
    private Integer    port        = 9000;
    //默认IP Address
    private String     ipAddress   = "0.0.0.0";
    //使用GZip
    private Boolean    gzip        = false;
    //SSL
    private SslContext sslContext  = null;

    // Configure the server
    private EventLoop      scheduleEventLoop;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    protected static Server instance() {
        return new Server();
    }

    private Server() {
        /*redirect = Redirect.create(this);
        staticFiles = new StaticFiles();

        if (isRunningFromServlet()) {
            staticFilesConfiguration = StaticFilesConfiguration.servletInstance;
        } else {
            staticFilesConfiguration = StaticFilesConfiguration.create();
        }*/
    }

    protected void startServer() {
        try {
            log.info("=> Start ...");
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
            ServerBootstrap bootstrap = new ServerBootstrap();
            log.info("=> Use NioEventLoopGroup");
            this.bossGroup = new NioEventLoopGroup(1);
            this.workerGroup = new NioEventLoopGroup(0);
            this.scheduleEventLoop = new DefaultEventLoop();

            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);

            // TCP默认开启了 Nagle 算法, 该算法的作用是尽可能的发送大数据快, 减少网络传输,
            // TCP_NODELAY 参数的作用就是控制是否启用 Nagle 算法
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            // 是否开启 TCP 底层心跳机制
            //bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            // 表示系统用于临时存放已完成三次握手的请求的队列的最大长度, 如果连接建立频繁, 服务器处理创建新连接较慢, 可以适当调大
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));

            bootstrap.childHandler(new HttpServerInitializer());

            ChannelFuture channelFuture = bootstrap.bind(ipAddress, port).sync();
            log.info("=> Initialize Successfully, {}", (sslContext != null ? "https" : "http") + "://127.0.0.1" + ":" + port);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("=> Initialize failed");
            e.printStackTrace();
        }
    }

    /**
     * 设置监听的IP地址, 默认 0.0.0.0
     *
     * @param ipAddress IP Address
     */
    public Server ipAddress(String ipAddress) {
        if (initialized) {
            throw new IllegalStateException("This must be done before route mapping has begun");
        }
        this.ipAddress = ipAddress;
        return this;
    }

    /**
     * 获取监听的IP地址
     */
    public String ipAddress() {
        return this.ipAddress;
    }

    /**
     * 设置SSL
     * 只能在添加路由之前调用
     *
     * @param certPath    证书路径
     * @param KeyPath     密钥路径
     * @param keyPassword 证书密码
     */
    public Server ssl(String certPath, String KeyPath, String keyPassword) {
        log.info("=> SSL Enable");
        if (this.initialized) {
            throw new IllegalStateException("This must be done before route mapping has begun");
        }
        try {
            log.info("=> SSL CertChainFile  Path: {}", certPath);
            log.info("=> SSL PrivateKeyFile Path: {}", KeyPath);
            sslContext = SslContextBuilder.forServer(new File(certPath), new File(KeyPath), keyPassword).build();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取SSL
     */
    public SslContext ssl() {
        return this.sslContext;
    }

    /**
     * 设置端口
     * 只能在添加路由之前调用
     */
    public Server port(int port) {
        if (this.initialized) {
            throw new IllegalStateException("This must be done before route mapping has begun");
        }
        this.port = port;
        return this;
    }

    /**
     * 获取端口
     *
     * @return 端口|9000
     */
    public Integer port() {
        return this.port;
    }

    /**
     * 设置gzip状态
     */
    public Server gzip(Boolean gzip) {
        if (this.initialized) {
            throw new IllegalStateException("This must be done before route mapping has begun");
        }
        this.gzip = gzip;
        return this;
    }

    /**
     * 获取gzip状态
     *
     * @return 是|否
     */
    public Boolean gzip() {
        return this.gzip;
    }

    @Override
    public void close() {
        if (!initialized) {
            return;
        }
        initialized = false;
        log.info("=> Shutdown ...");
        this.scheduleEventLoop.shutdownGracefully();
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
        log.info("=> Shutdown Successful");
    }
}