package com.sykj.uusmart.mqtt.cmd.timing.input;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class MqIotTimerTaskDTO {
    private Long id;
    private String mode;
    private String days;
    private LinkedHashMap start;
    private LinkedHashMap finish;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public LinkedHashMap getStart() {
        return start;
    }

    public void setStart(LinkedHashMap start) {
        this.start = start;
    }

    public LinkedHashMap getFinish() {
        return finish;
    }

    public void setFinish(LinkedHashMap finish) {
        this.finish = finish;
    }
}
