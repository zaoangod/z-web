package z.service.ssl;

/**
 * SSL Stores
 */
public class SslStore {

    private String  keyStoreFile;
    private String  keyStorePassword;
    private String  certAlias;
    private String  trustStoreFile;
    private String  trustStorePassword;
    private boolean needClientAuth;

    public String keyStoreFile() {
        return keyStoreFile;
    }

    public void keyStoreFile(String keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
    }

    public String keyStorePassword() {
        return keyStorePassword;
    }

    public void keyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String certAlias() {
        return certAlias;
    }

    public void certAlias(String certAlias) {
        this.certAlias = certAlias;
    }

    public String trustStoreFile() {
        return trustStoreFile;
    }

    public void trustStoreFile(String trustStoreFile) {
        this.trustStoreFile = trustStoreFile;
    }

    public String trustStorePassword() {
        return trustStorePassword;
    }

    public void trustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public boolean needClientAuth() {
        return needClientAuth;
    }

    public void needClientAuth(boolean needClientAuth) {
        this.needClientAuth = needClientAuth;
    }

}