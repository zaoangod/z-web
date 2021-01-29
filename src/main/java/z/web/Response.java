package z.web;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * HttpResponse
 *
 * @author biezhi
 * 2017/5/31
 */
@Slf4j
public class Response {

    private Map<String, String> header = new LinkedHashMap<>();
    private Set<Cookie>         cookie = new LinkedHashSet<>();

    private FullHttpResponse httpResponse;
    private Integer          statusCode;

    public Response() {
        this.statusCode = OK.code();
    }

    public Response(Response response) {
        this.statusCode = response.status();
        if (response.header().size() != 0) {
            response.header().forEach(this.header::put);
        }
        if (response.cookie().size() != 0) {
            response.cookie().forEach((k, v) -> this.cookie.add(new DefaultCookie(k, v)));
        }
    }

    public int status() {
        return statusCode;
    }

    public Response status(int code) {
        this.statusCode = code;
        return this;
    }

    /*public String contentType() {
        return this.header.get(CONTENT_TYPE.toString());
    }

    public Response contentType(@NonNull String contentType) {
        this.header.put(CONTENT_TYPE.toString(), contentType);
        return this;
    }*/
    //====================================================
    public Map<String, String> header() {
        return this.header;
    }

    public String header(@NonNull String name) {
        return this.header.get(name);
    }

    public Response header(String name, String value) {
        this.header.put(name, value);
        return this;
    }
    //====================================================

    public Map<String, String> cookie() {
        Map<String, String> r = new HashMap<>(8);
        this.cookie.forEach(i -> r.put(i.name(), i.value()));
        return r;
    }

    public Response cookie(@NonNull Cookie nettyCookie) {
        this.cookie.add(nettyCookie);
        return this;
    }

    public Response removeCookie(@NonNull String name) {
        this.cookie.stream().filter(i -> i.name().equals(name)).findFirst().ifPresent(i -> {
            i.setMaxAge(-1);
            i.setValue("");
            this.cookie.add(i);
        });
        return this;
    }

    public Set<Cookie> cookieRaw() {
        return this.cookie;
    }
    //====================================================
    /*
    public Response cookie(String name, String value) {
        this.cookie.add(new DefaultCookie(name, value));
        return this;
    }

    public Response cookie(@NonNull String name, @NonNull String value, int maxAge) {
        Cookie nettyCookie = new DefaultCookie(name, value);
        nettyCookie.setPath("/");
        nettyCookie.setMaxAge(maxAge);
        this.cookie.add(nettyCookie);
        return this;
    }

    public Response cookie(@NonNull String name, @NonNull String value, int maxAge, boolean secured) {
        Cookie nettyCookie = new DefaultCookie(name, value);
        nettyCookie.setPath("/");
        nettyCookie.setMaxAge(maxAge);
        nettyCookie.setSecure(secured);
        this.cookie.add(nettyCookie);
        return this;
    }

    public Response cookie(@NonNull String path, @NonNull String name, @NonNull String value, int maxAge, boolean secured) {
        Cookie nettyCookie = new DefaultCookie(name, value);
        nettyCookie.setMaxAge(maxAge);
        nettyCookie.setSecure(secured);
        nettyCookie.setPath(path);
        this.cookies.add(nettyCookie);
        return this;
    }*/

    /*public void download(@NonNull String fileName, @NonNull File file) throws Exception {
        if (!file.exists() || !file.isFile()) {
            throw new NotFoundException("Not found file: " + file.getPath());
        }
        String contentType = StrKit.mimeType(file.getName());
        header.put(CONTENT_DISPOSITION.toString(), "attachment; filename=" + new String(fileName.getBytes(UTF_8), ISO_8859_1));
        header.put(CONTENT_LENGTH.toString(), String.valueOf(file.length()));
        header.put(CONTENT_TYPE.toString(), contentType);
        this.body = new StreamBody(new FileInputStream(file));
    }*/
}