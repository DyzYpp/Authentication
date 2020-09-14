package com.dyz.authentication.util.ResultUtil;

/**
 * @ClassName Enum
 * @Author Daun yuzhe
 * @Date 2020/8/24
 * @description 返回结果对应状态码
 */
public enum Enums {

    SUCCESS(200,"操作成功"),
    FAIL(500,"操作失败"),
    USER_REGISTER(422,"用户已经存在");

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    Enums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Enums{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
