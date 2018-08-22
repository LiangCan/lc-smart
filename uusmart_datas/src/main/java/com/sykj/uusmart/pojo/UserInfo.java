package com.sykj.uusmart.pojo;


/**
 * Created by Liang on 2016/12/22.
 */
import javax.persistence.*;
//,indexes={ @Index(name="index_ui_email)", unique=true, columnList="user_email")}
@Entity
@Table(name="t_user_info"  )
public class UserInfo {

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="user_id", length = 16)
    private Long userId;

    /**
     * token
     */
    @Column(name="login_token",columnDefinition="  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登录token' ")
    private String loginToken;
    /**
     * 邮箱
     */
    @Column(name="user_email",columnDefinition="  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱' ")
    private String email;

    /**
     * 用户呢称
     */
    @Column(name="user_name",columnDefinition="  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名' ")
    private String userName;


    /**
     * 登陆密码
     */
    @Column(name="password",columnDefinition=" varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户密码' ")
    private String password;
    /**
     * 注册时间
     */
    @Column(name="create_time",columnDefinition=" bigint(13) COMMENT '注册时间' ")
    private Long createTime;

    /**
     * 用户头像地址
     */
    @Column(name="icon_url",columnDefinition=" varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像地址' ")
    private String iconUrl;

    /**
     * 用户头像地址
     */
    @Column(name="vivo_open_id",columnDefinition=" varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'Vivo用户id' ")
    private String vivoOpenId;

    public UserInfo() {
    }


    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getVivoOpenId() {
        return vivoOpenId;
    }

    public void setVivoOpenId(String vivoOpenId) {
        this.vivoOpenId = vivoOpenId;
    }
}
