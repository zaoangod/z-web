/**
 * Copyright (c) 2018, biezhi 王爵 nice (biezhi.me@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package z.web.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.AsciiString;
import lombok.extern.slf4j.Slf4j;
import z.web.Request;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 自定义处理器
 * FullHttpRequest
 * 客户端 HTTP 请求参数类型为 FullHttpRequest. 我们可以把 FullHttpRequest 对象看作是 HTTP 请求报文的 Java 对象的表现形式
 */
@Slf4j
@ChannelHandler.Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final String FAVICON_ICO = "/favicon.ico";

    /**
     * 处理客户端发送的 HTTP 请求发生异常的时候被调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 服务端消费完客户端发送的 HTTP 请求之后调用的方法
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * 服务端接收并处理客户端发送的 HTTP 请求调用的方法
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {
        log.info("Handle http request: {}", fullHttpRequest.toString());
        String uri = fullHttpRequest.uri();
        if (uri.equals(FAVICON_ICO)) {
            return;
        }
        FullHttpResponse response;
        try {
            //
            Request request = new Request().init(fullHttpRequest);
            log.info("---------------------------------------------");
            log.info("=> {}", request.param());
            log.info("---------------------------------------------");

            StringBuffer result = new StringBuffer();
            result.append("==========header==========").append("\r\n");
            fullHttpRequest.headers().forEach(item -> result.append(item.getKey()).append(" = ").append(item.getValue()).append("\r\n"));

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(fullHttpRequest.toString().getBytes(StandardCharsets.UTF_8)));
            //response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(responseBytes));
            response.headers().set(CONTENT_TYPE, "text/plain; charset=utf-8");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
            log.info("=> 处理完成");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            byte[] responseBytes = e.toString().getBytes(StandardCharsets.UTF_8);
            response = new DefaultFullHttpResponse(HTTP_1_1, INTERNAL_SERVER_ERROR, Unpooled.wrappedBuffer(responseBytes));
            response.headers().set(CONTENT_TYPE, "text/plain; charset=utf-8");
        }
       /*boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }*/
        ctx.write(response);
    }
}