package z.web;

import z.web.ssl.SslStore;

public final class Config {

    private String contextPath = "/";
    //port
    private int    port        = 9876;
    //host
    private String host        = "0.0.0.0";

    //Server线程池最小线程数
    private int minThread   = 8;
    //Server线程池最大线程数
    private int maxThread   = 200;
    //最大线程空闲时间(单位毫秒)
    private int idleTimeout = 60000;

    //信任转发标头
    private boolean trustForwardHeader = true;

    //启用会话
    private boolean sessionStatus  = true;
    //会话超时, 默认3600秒
    private int     sessionTimeout = 3600;

    //WebSocket状态
    private boolean webSocketStatus = false;

    //SSL 配置
    private SslStore sslStore = null;

    //
    private boolean httpOnly = true;

    //----------------------------------
    public String contextPath() {
        return contextPath;
    }

    public void contextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public int port() {
        return port;
    }

    public void port(int port) {
        this.port = port;
    }

    public String host() {
        return host;
    }

    public void host(String host) {
        this.host = host;
    }

    public int minThread() {
        return minThread;
    }

    public void minThread(int minThread) {
        this.minThread = minThread;
    }

    public int maxThread() {
        return maxThread;
    }

    public void maxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    public int idleTimeout() {
        return idleTimeout;
    }

    public void idleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public boolean trustForwardHeader() {
        return trustForwardHeader;
    }

    public void trustForwardHeader(boolean trustForwardHeader) {
        this.trustForwardHeader = trustForwardHeader;
    }

    public boolean sessionStatus() {
        return sessionStatus;
    }

    public void sessionStatus(boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public int sessionTimeout() {
        return sessionTimeout;
    }

    public void sessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public boolean webSocketStatus() {
        return webSocketStatus;
    }

    public void webSocketStatus(boolean webSocketStatus) {
        this.webSocketStatus = webSocketStatus;
    }

    public SslStore sslStore() {
        return sslStore;
    }

    public void sslStore(SslStore sslStore) {
        this.sslStore = sslStore;
    }

    public boolean httpOnly() {
        return httpOnly;
    }

    public void httpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

}
