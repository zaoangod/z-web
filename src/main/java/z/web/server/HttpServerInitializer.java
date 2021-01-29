package z.web.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import z.web.Web;

import javax.net.ssl.SSLEngine;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * HttpServerInitializer
 */
@Slf4j
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private final HttpServerHandler httpServerHandler;

    public static volatile String date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

    /*public HttpServerInitializer(ScheduledExecutorService service) {
        this.httpServerHandler = new HttpServerHandler();
        service.scheduleWithFixedDelay(() -> date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()), 1000, 1000, TimeUnit.MILLISECONDS);
    }*/

    public HttpServerInitializer() {
        this.httpServerHandler = new HttpServerHandler();
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        try {
            ChannelPipeline pipeline = ch.pipeline();
            if (null != Web.ssl()) {
                log.info("=> Enable SSL");
                pipeline.addLast(Web.ssl().newHandler(ch.alloc()));
                /*SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
                engine.setNeedClientAuth(true); //ssl双向认证
                engine.setUseClientMode(false);
                engine.setWantClientAuth(true);
                engine.setEnabledProtocols(new String[]{"SSLv3"});
                pipeline.addLast("ssl", new SslHandler(engine));*/
            }
            if (Web.gzip()) {
                log.info("=> Enable GZIP");
                // 请求压缩
                pipeline.addLast(new HttpContentCompressor());
            }
            // 请求/响应解码器, HttpRequestDecoder 和 HttpResponseEncoder 的结合
            pipeline.addLast(new HttpServerCodec());
            // 可以将这些消息聚合成一个完整的, 方便处理
            // 将多个消息转换为单一的 request 或者 response 对象
            pipeline.addLast(new HttpObjectAggregator(512 * 1024 * 1024));
            pipeline.addLast(new HttpServerExpectContinueHandler());
            // 支持异步大文件传输
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(httpServerHandler);
        } catch (Exception e) {
            log.error("Add channel pipeline error", e);
        }
    }
}
