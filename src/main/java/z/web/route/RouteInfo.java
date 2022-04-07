package z.web.route;

import z.web.kit.WebKit;

import java.util.List;

public class RouteInfo {

    private Method method;
    private String path;
    private String accept;
    private Object target;

    public RouteInfo() {
    }

    public RouteInfo(Method method, String path, String accept, Object target) {
        this.method = method;
        this.path = path;
        this.accept = accept;
        this.target = target;
    }

    public boolean match(Method methodValue, String path) {
        //钩子路由方法
        boolean hook = Method.before == methodValue || Method.after == methodValue;
        //自身路由方法
        boolean own = methodValue.equals(this.method);
        //匹配全局路径
        boolean global = "**".equals(this.path);
        if (hook && own && global) {
            return true;
        }
        boolean match = false;
        if (this.method.equals(methodValue)) {
            match = matchPath(path);
        }
        return match;
    }

    private boolean matchPath(String path) {
        if (!this.path.endsWith("*") && ((path.endsWith("/") && !this.path.endsWith("/")) || (this.path.endsWith("/") && !path.endsWith("/")))) {
            // One and not both ends with slash
            return false;
        }
        if (this.path.equals(path)) {
            return true;
        }

        List<String> thisPathList = WebKit.route(this.path);
        List<String> pathList     = WebKit.route(path);

        int thisPathSize = thisPathList.size();
        int pathSize     = pathList.size();

        if (thisPathSize == pathSize) {
            for (int i = 0; i < thisPathSize; i++) {
                String thisPathPart = thisPathList.get(i);
                String pathPart     = pathList.get(i);

                if ((i == thisPathSize - 1) && (thisPathPart.equals("*") && this.path.endsWith("*"))) {
                    // wildcard match
                    return true;
                }

                if ((!thisPathPart.startsWith(":")) && !thisPathPart.equals(pathPart) && !thisPathPart.equals("*")) {
                    return false;
                }
            }
            // All parts matched
            return true;
        } else {
            // Number of "path parts" not the same
            // check wild card:
            if (this.path.endsWith("*")) {
                if (pathSize == (thisPathSize - 1) && (path.endsWith("/"))) {
                    // Hack for making wildcards work with trailing slash
                    pathList.add("");
                    pathList.add("");
                    pathSize += 2;
                }

                if (thisPathSize < pathSize) {
                    for (int i = 0; i < thisPathSize; i++) {
                        String thisPathPart = thisPathList.get(i);
                        String pathPart     = pathList.get(i);
                        if (thisPathPart.equals("*") && (i == thisPathSize - 1) && this.path.endsWith("*")) {
                            // wildcard match
                            return true;
                        }
                        if (!thisPathPart.startsWith(":") && !thisPathPart.equals(pathPart) && !thisPathPart.equals("*")) {
                            return false;
                        }
                    }
                    // All parts matched
                    return true;
                }
                // End check wild card
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return method + ", " + path + ", " + target.toString();
    }

    //--------------------------get or set--------------------------

    public Method method() {
        return method;
    }

    public RouteInfo method(Method method) {
        this.method = method;
        return this;
    }

    public String path() {
        return path;
    }

    public RouteInfo path(String path) {
        this.path = path;
        return this;
    }

    public String accept() {
        return accept;
    }

    public RouteInfo accept(String accept) {
        this.accept = accept;
        return this;
    }

    public Object target() {
        return target;
    }

    public RouteInfo target(Object target) {
        this.target = target;
        return this;

    }

}
