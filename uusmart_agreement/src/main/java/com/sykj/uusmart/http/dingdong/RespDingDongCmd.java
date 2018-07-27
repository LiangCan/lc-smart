package com.sykj.uusmart.http.dingdong;

/**
 * Created by Administrator on 2017/4/26 0026.
 */
public class RespDingDongCmd {
    private String versionid;
    private long timestamp;
    private RespDDDirective directive = new RespDDDirective();
    private boolean is_end;
    private String sequence;

    public RespDingDongCmd() {
    }



    @Override
    public String toString() {
        return "RespDingDongCmd{" +
                "versionid='" + versionid + '\'' +
                ", timestamp=" + timestamp +
                ", directice_items=" + directive == null ? " " : directive.toString() +
                ", is_end=" + is_end +
                ", sequence='" + sequence + '\'' +
                '}';
    }

    public RespDingDongCmd(String versionid, long timestamp, RespDingDongDirective directice_items, boolean is_end, String sequence) {
        this.versionid = versionid;
        this.timestamp = timestamp;
        this.directive.getDirective_items().add(directice_items);
        this.is_end = is_end;
        this.sequence = sequence;
    }

    public String getVersionid() {
        return versionid;
    }

    public void setVersionid(String versionid) {
        this.versionid = versionid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public boolean isIs_end() {
        return is_end;
    }

    public void setIs_end(boolean is_end) {
        this.is_end = is_end;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
