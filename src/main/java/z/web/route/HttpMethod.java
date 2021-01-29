package z.web.route;

public interface HttpMethod {
    String get     = "get";
    String post    = "post";
    String put     = "put";
    String patch   = "patch";
    String delete  = "delete";
    String head    = "head";
    String trace   = "trace";
    String connect = "connect";
    String options = "options";
    String before  = "before";
    String after   = "after";
    String unknown = "unknown";
}