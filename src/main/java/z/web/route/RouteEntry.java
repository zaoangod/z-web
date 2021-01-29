package z.web.route;

public class RouteEntry {
    private String method;
    private String path;
    private String acceptedType;
    private Object target;

    public RouteEntry() {
    }

    public RouteEntry(RouteEntry entry) {
        this.method = entry.method;
        this.path = entry.path;
        this.acceptedType = entry.acceptedType;
        this.target = entry.target;
    }
}