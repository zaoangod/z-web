package z.web;

public class Cookie {
    private String  name     = null;
    private String  value    = null;
    private String  domain   = null;
    private String  path     = "/";
    private Boolean secure   = false;
    private Boolean httpOnly = false;
    private Long    expire   = -1L;

    public String name() {
        return name;
    }

    public Cookie name(String name) {
        this.name = name;
        return this;
    }

    public String value() {
        return value;
    }

    public Cookie value(String value) {
        this.value = value;
        return this;
    }

    public String domain() {
        return domain;
    }

    public Cookie domain(String domain) {
        this.domain = domain;
        return this;
    }

    public String path() {
        return path;
    }

    public Cookie path(String path) {
        this.path = path;
        return this;
    }

    public Long expire() {
        return expire;
    }

    public Cookie expire(Long expire) {
        this.expire = expire;
        return this;
    }

    public Boolean secure() {
        return secure;
    }

    public Cookie secure(Boolean secure) {
        this.secure = secure;
        return this;
    }

    public Boolean httpOnly() {
        return httpOnly;
    }

    public Cookie httpOnly(Boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }
}