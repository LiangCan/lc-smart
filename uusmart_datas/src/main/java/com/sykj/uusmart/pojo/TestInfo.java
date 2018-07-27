package com.sykj.uusmart.pojo;


/**
 * Created by Liang on 2016/12/22.
 */
import javax.persistence.*;

@Entity
@Table(name="t_test_info"  )
public class TestInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="test_id", length = 16)
    private Long testId;

    @Column(name="testName",columnDefinition="  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登录token' ")
    private String testName;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
