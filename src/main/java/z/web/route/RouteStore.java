package z.web.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z.web.Handler;
import z.web.kit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用的所有路由映射管理
 */
public class RouteStore {

    private final Logger log = LoggerFactory.getLogger(RouteStore.class);

    private final List<RouteInfo> routeList;

    private static class Node {

        Boolean    root;
        String     method;
        Boolean    wildChild;
        String     dataType;
        String     path;
        List<Node> child;
        Handler    handler;

    }

    private RouteStore() {
        routeList = new ArrayList<>();
    }

    public static RouteStore create() {
        return new RouteStore();
    }

    /**
     * 添加路由
     *
     * @param method 处理方法
     * @param path   路径
     * @param accept 接受类型
     * @param target 处理器
     */
    public void add(Method method, String path, String accept, Handler target) {
        Assert.notNull(target, () -> new RuntimeException("route cannot be null"));
        Assert.notNull(method, () -> new RuntimeException("method cannot be null"));
        Assert.notBlank(path, () -> new RuntimeException("path cannot be null or blank"));
        Assert.notBlank(accept, () -> new RuntimeException("accept cannot be null or blank"));
        //
        RouteInfo routeInfo = new RouteInfo(method, path, accept, target);
        //存储路由信息
        routeList.add(routeInfo);
        log.debug("RouteInfo: {}", routeInfo);
    }

    /**
     * 添加组路由
     *
     * @param method 处理方法
     * @param path   路径
     * @param accept 接受类型
     * @param target 处理器
     */
    public void addGroup(Method method, String path, String accept, Handler target) {
        Assert.notNull(target, () -> new RuntimeException("route cannot be null"));
        Assert.notNull(method, () -> new RuntimeException("method cannot be null"));
        Assert.notBlank(path, () -> new RuntimeException("path cannot be null or blank"));
        Assert.notBlank(accept, () -> new RuntimeException("accept cannot be null or blank"));
        //
        RouteInfo routeInfo = new RouteInfo(method, path, accept, target);
        //存储路由信息
        routeList.add(routeInfo);
        log.debug("RouteInfo: {}", routeInfo);
    }

    /**
     * 删除路由
     *
     * @param method 方法
     * @param path   请求路径
     */
    private void remove(Method method, String path) {
        List<RouteInfo> deleteList = new ArrayList<>();
        for (RouteInfo item : routeList) {
            if (item.method() == method && path.equals(item.path())) {
                log.debug("remove route info, method: {}, path: {}", method.name(), path);
                deleteList.add(item);
            }
        }
        routeList.removeAll(deleteList);
    }

    /**
     * 清理路由列表
     */
    public void clear() {
        routeList.clear();
    }

    /**
     * 查找路由对象
     *
     * @param method 请求方法
     * @param path   请求路径
     * @param accept 接受类型
     * @return RouteInfo
     */
    public RouteInfo find(Method method, String path, String accept) {
        log.debug("RouteStore => find => Method: {}, Path: {}, Accept: {}", method.name(), path, accept);
        return routeList.stream().filter(a -> a.match(method, path))
                //.filter(a -> a.accept().equals(accept))
                .findFirst().orElse(null);
    }

}
