package com.sykj.uusmart.pojo;

import javax.persistence.*;

@Entity
@Table(name="t_feedback_info")
public class FeedbackInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="feedback_id", length = 16)
    private Long feedbackId;

    @Column(name = "user_id", columnDefinition = " bigint(13) COMMENT '用户ID' ")
    private Long userId;

    @Column(name = "create_time", columnDefinition = " bigint(13)  DEFAULT NULL COMMENT '创建时间戳' ")
    private Long createTime;

    @Column(name = "handle_time", columnDefinition = " bigint(13)  DEFAULT NULL COMMENT '处理时间' ")
    private Long handleTime;

    @Column(name = "feedback_type", columnDefinition = " bigint(13)  DEFAULT NULL COMMENT '反馈类型； 0：意见反馈' ")
    private Short feedbackType;

    @Column(name = "feedback_status", columnDefinition = " bigint(2) COMMENT '反馈状态；0：未处理，1：已处理，2：处理中' ")
    private Short feedbackStatus;

    @Column(name = "feedback_content", columnDefinition = " varchar(200)  DEFAULT NULL COMMENT '反馈内容' ")
    private String feedbackContent;

    @Column(name = "contact_way", columnDefinition = " varchar(30)  DEFAULT NULL COMMENT '类型方式' ")
    private String contactWay;

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Long handleTime) {
        this.handleTime = handleTime;
    }

    public Short getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Short feedbackType) {
        this.feedbackType = feedbackType;
    }

    public Short getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(Short feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }
}
