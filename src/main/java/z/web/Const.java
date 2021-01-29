package z.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Const {
    Integer      DEFAULT_SERVER_PORT    = 9000;
    String       DEFAULT_SERVER_ADDRESS = "0.0.0.0";
    String       LOCAL_IP_ADDRESS       = "127.0.0.1";
    String       Z_WEB_VERSION          = "0.0.1";
    List<String> DEFAULT_STATIC         = new ArrayList<>(
            Arrays.asList("/favicon.ico", "/robots.txt", "/static", "/upload", "/webjars/")
    );

    String ENV_KEY_CONTEXT_PATH         = "app.context-path";
    String ENV_KEY_GZIP_ENABLE          = "http.gzip.enable";
    String ENV_KEY_CORS_ENABLE          = "http.cors.enable";
    String ENV_KEY_SERVER_PORT          = "server.port";
    String ENV_KEY_SSL_ENABLE           = "server.ssl.enable";
    String ENV_KEY_SSL_CERT_PATH        = "server.ssl.cert-path";
    String ENE_KEY_SSL_PRIVATE_KEY_PATH = "server.ssl.private-key-path";
    String ENE_KEY_SSL_PRIVATE_KEY_PASS = "server.ssl.private-key-pass";
}