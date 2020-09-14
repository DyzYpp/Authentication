package com.dyz.authentication.util.ResultUtil;

import org.springframework.stereotype.Component;

/**
 * @ClassName ResponseUtil
 * @Author
 * @Date 2020/8/24
 * @description
 */
@Component
public class AjaxResult {

    private String msg;

    private Integer code;

    private Object data;

    public AjaxResult() {
    }

    public AjaxResult(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public AjaxResult(String msg, Integer code, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static AjaxResult success(){
        return AjaxResult.success("操作成功");
    }

    public static AjaxResult success(Object data){
        return AjaxResult.success("操作成功",data);
    }

    public static AjaxResult success(String msg){
        return AjaxResult.success(msg,null);
    }

    public static AjaxResult success(String msg, Object data){
        return new AjaxResult(Enums.SUCCESS.getMsg(), Enums.SUCCESS.getCode(),data);
    }

    public static AjaxResult error(){
        return AjaxResult.error("操作失败");
    }

    public static AjaxResult error(Object data){
        return AjaxResult.error("操作失败",data);
    }

    public static AjaxResult error(String msg){
        return AjaxResult.error(msg,null);
    }

    public static AjaxResult error(String msg, Object data){
        return new AjaxResult(msg, Enums.FAIL.getCode(),data);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
