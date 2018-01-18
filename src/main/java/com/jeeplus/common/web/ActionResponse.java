package com.jeeplus.common.web;


public class ActionResponse<E> {

    private int code = ResponseCode.code_SUCC;
    private String message;
    private E data;
    private boolean redirect = false;
    private String redirectUrl;

    public ActionResponse() {
    }

    public ActionResponse(int code, String message) {
        this.code = code;
        this.message = message;

    }

    public ActionResponse(int code, String message, String redirectUrl) {
        this.code = code;
        this.message = message;
        this.redirectUrl = redirectUrl;
    }

    public ActionResponse(E result) {
        code = 0;
        this.data = result;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }


}
