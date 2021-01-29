package z.web.kit;

public class R {

    private String  msg;
    private Integer code;
    private String  data;

    public String getMsg() {
        return msg;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public R setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getData() {
        return data;
    }

    public R setData(String data) {
        this.data = data;
        return this;
    }
    //--------------------------------------------

    public R() {
    }

    public static R f() {
        return new R().setCode(1);
    }

    public static R s() {
        return new R().setCode(0);
    }
    //--------------------------------------------

    @Override
    public String toString() {
        return "{msg=" + msg + ", code=" + code + ", data=" + data + "}";
    }
}
