package com.sykj.uusmart.pojo;

/**
 * Created by Liang on 2016/12/23.
 */
import javax.persistence.*;

@Entity
@Table(name = "t_device_version_info")
public class DeviceVersionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 16)
    private Long id;//

    @Column(name = "pid", columnDefinition = " bigint(16)  DEFAULT NULL COMMENT '产品ID' ")
    private  Long pid;

    @Column(name = "force_up", columnDefinition = " smallint(2) DEFAULT NULL COMMENT '是否强制升级' ")
    private Short forceUp;

    @Column(name = "download_url", columnDefinition = " varchar(1024) DEFAULT NULL COMMENT '请求下载地址' ")
    private String downloadUrl;

    @Column(name = "version", columnDefinition = " varchar(16)  DEFAULT NULL COMMENT '版本信息' ")
    private String version;

    @Column(name = "create_time", columnDefinition = " bigint(13)  DEFAULT NULL COMMENT '创建时间戳' ")
    private Long createTime;

    @Column(name = "version_state", columnDefinition = " smallint(2) DEFAULT NULL COMMENT '版本状态' ")
    private Short versionState;

    public DeviceVersionInfo() {
    }

    public Short getForceUp() {
        return forceUp;
    }

    public void setForceUp(Short forceUp) {
        this.forceUp = forceUp;
    }

    public Short getVersionState() {
        return versionState;
    }

    public void setVersionState(Short versionState) {
        this.versionState = versionState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
