package z.web;

import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.multipart.*;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.netty.handler.codec.http.HttpHeaderNames.COOKIE;
import static io.netty.util.CharsetUtil.UTF_8;

@Slf4j
public class Request {

    //    request.body              # request body sent by the client (see below), DONE
    //    request.scheme            # "http"                                DONE
    //    request.path_info         # "/foo",                               DONE
    //    request.port              # 80                                    DONE
    //    request.request_method    # "GET",                                DONE
    //    request.query_string      # "",                                   DONE
    //    request.content_length    # length of request.body,               DONE
    //    request.media_type        # media type of request.body            DONE, content type?
    //    request.host              # "example.com"                         DONE
    //    request["SOME_HEADER"]    # value of SOME_HEADER header,          DONE
    //    request.user_agent        # user agent (used by :agent condition) DONE
    //    request.url               # "http://example.com/example/foo"      DONE
    //    request.ip                # client IP address                     DONE
    //    request.env               # raw env hash handed in by Rack,       DONE
    //    request.get?              # true (similar methods for other verbs)
    //    request.secure?           # false (would be true over ssl)
    //    request.forwarded?        # true (if running behind a reverse proxy)
    //    request.cookies           # hash of browser cookies,              DONE
    //    request.xhr?              # is this an ajax request?
    //    request.script_name       # "/example"
    //    request.form_data?        # false
    //    request.referrer          # the referrer of the client or '/'

    //--------------------------------------------------------------------------------------------------
    // 使用内存保存数据(不使用硬盘存储)
    private static final HttpDataFactory HTTP_DATA_FACTORY = new DefaultHttpDataFactory(false);
    //--------------------------------------------------------------------------------------------------
    private              HttpRequest     nettyRequest;
    //--------------------------------------------------------------------------------------------------

    private String  url;
    private String  uri;
    private String  method;
    private String  protocol;
    private Boolean keepAlive;
    private Boolean multipart;

    private Map<String, String> header = null;
    private Map<String, Cookie> cookie = null;
    private Map<String, String> param  = null;
    private String              body   = "";

    public Request() {
    }

    public Request init(Request request) {
        return this;
    }

    public Request init(FullHttpRequest request) {
        this.nettyRequest = request;
        //
        this.url = request.uri();
        if (null != this.url && this.url.length() > 0) {
            int index = this.url.indexOf('?');
            this.uri = index < 0 ? this.url : this.url.substring(0, index);
        }
        this.method = request.method().name();
        this.protocol = request.protocolVersion().text();
        this.keepAlive = HttpUtil.isKeepAlive(request);
        this.header = this.header(request);
        this.cookie = this.cookie();
        this.param = this.param();
        //this.body = request.content();
        return this;
    }
    //-------------------------------------------------------------------

    /**
     * 获取请求头内容
     *
     * @return 请求头内容
     */
    public Map<String, String> header(HttpRequest request) {
        if (null == header) {
            header = new LinkedHashMap<>(request.headers().size());
            request.headers().forEach(h -> header.put(h.getKey(), h.getValue()));
        }
        return this.header;
    }

    /**
     * 获取请求头内容
     *
     * @return 请求头内容
     */
    public String header(String name) {
        return this.header.get(name);
    }

    /**
     * 设置请求头内容
     *
     * @return 请求头内容
     */
    public String header(String name, String value) {
        return this.header.put(name, value);
    }

    /**
     * 获取cookie
     *
     * @return cookie map
     */
    public Map<String, Cookie> cookie() {
        if (null == cookie) {
            cookie = new LinkedHashMap<>();
            String cks = this.header(COOKIE.toString());
            cks = Optional.ofNullable(cks).orElse("");
            ServerCookieDecoder.LAX.decode(cks).forEach(nck -> {
                Cookie ck = new Cookie();
                ck.name(nck.name());
                ck.value(nck.value());
                ck.httpOnly(nck.isHttpOnly());
                ck.path(nck.path());
                ck.domain(nck.domain());
                ck.expire(nck.maxAge());
                ck.secure(nck.isSecure());
                this.cookie.put(ck.name(), ck);
            });
        }
        return this.cookie;
    }

    /**
     * 获取cookie
     *
     * @return Cookie
     */
    public Cookie cookie(String name) {
        return cookie.get(name);
    }

    /**
     * 设置cookie
     *
     * @return Cookie
     */
    public Cookie cookie(String name, Cookie cookie) {
        return cookie.path(name);
    }

    /**
     * 获取请求body数据
     *
     * @param content body数据
     * @return Request
     */
    public Request body(String content) {
        this.body = content;
        return this;
    }

    public String body() {
        return this.body;
    }

    public Map<String, String> param() {
        if (null == param) {
            param = new LinkedHashMap<>();
            //-------------------------------------------------
            FullHttpRequest        fullRequest = (FullHttpRequest) nettyRequest;
            HttpPostRequestDecoder decoder     = new HttpPostRequestDecoder(HTTP_DATA_FACTORY, nettyRequest, UTF_8);
            this.multipart = decoder.isMultipart();
            //
            //解析body参数
            String                    bodyDataString = fullRequest.content().toString(UTF_8);
            Map<String, List<String>> bodyData       = new QueryStringDecoder(bodyDataString).parameters();
            bodyData.forEach((k, v) -> this.param.put(k, v.get(0)));
            //解析url参数
            Map<String, List<String>> urlData = new QueryStringDecoder(fullRequest.uri()).parameters();
            urlData.forEach((k, v) -> this.param.put(k, v.get(0)));
            if (HttpMethod.GET.name().equals(this.method)) {
                return param;
            }
            //-------------------------------------------------
            /*List<InterfaceHttpData> paramList = decoder.getBodyHttpDatas();
            paramList.stream().map(i -> (MemoryAttribute) i).forEach(item -> param.put(item.getName(), item.getValue()));*/
            decoder.destroy();
        }
        return this.param;
    }
}