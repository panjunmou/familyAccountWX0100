package com.pjm.familyAccountWx.common;

/**
 * 返回Json对象
 *
 * @author PanJm
 * @date 2016.11.19
 */
public class Json implements java.io.Serializable {

    private static final long serialVersionUID = 8722126593920041253L;

    private boolean success = false;

    private String msg = "操作失败，请重试";

    private Object obj = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
