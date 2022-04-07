package z.web;

/**
 * 路由处理器
 */
@FunctionalInterface
public interface Handler {

    Object handle(Request request, Response b) throws Exception;

}
