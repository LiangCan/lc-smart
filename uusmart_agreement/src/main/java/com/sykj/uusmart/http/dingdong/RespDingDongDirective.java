package com.sykj.uusmart.http.dingdong;

/**
 * Created by Administrator on 2017/4/26 0026.
 */
public class RespDingDongDirective {
    private String type;
    private String content;

    public RespDingDongDirective() {
    }

    public RespDingDongDirective(String type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return "RespDingDongDirective{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
