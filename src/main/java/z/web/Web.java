package z.web;

import io.netty.handler.ssl.SslContext;
import lombok.extern.slf4j.Slf4j;

import static z.web.Server.instance;

@Slf4j
public class Web {
    //Server Instance
    private static final Server server = instance();

    private Web() {
    }

    /**
     * 启动
     */
    public static void start() {
        server.startServer();
    }

    /**
     * 设置SSL
     * 只能在添加路由之前调用
     *
     * @param certPath 证书路径
     * @param KeyPath  密钥路径
     * @param password 证书密码
     */
    public static void ssl(String certPath, String KeyPath, String password) {
        server.ssl(certPath, KeyPath, password);
    }

    /**
     * 获取SSL
     */
    public static SslContext ssl() {
        return server.ssl();
    }

    /**
     * 获取端口
     *
     * @return 端口|9000
     */
    public static Integer port() {
        return server.port();
    }

    /**
     * 设置端口
     * 只能在添加路由之前调用
     *
     * @param port 端口|9000
     */
    public static void port(int port) {
        server.port(port);
    }

    /**
     * 获取gzip状态
     *
     * @return 是|否
     */
    public static Boolean gzip() {
        return server.gzip();
    }

    /**
     * 设置gzip状态
     *
     * @param gzip 是|否
     */
    public static void gzip(Boolean gzip) {
        server.gzip(gzip);
    }

}