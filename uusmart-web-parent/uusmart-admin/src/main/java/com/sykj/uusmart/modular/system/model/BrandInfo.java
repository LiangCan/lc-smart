package com.sykj.uusmart.modular.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lgf
 * @since 2018-08-06
 */
@TableName("t_brand_info")
public class BrandInfo extends Model<BrandInfo> {

    private static final long serialVersionUID = 1L;

    @TableId("brand_id")
    private Long brandId;
    /**
     * 品牌代码
     */
    @TableField("brand_code")
    private Long brandCode;
    @TableField("brand_name")
    private String brandName;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;


    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(Long brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.brandId;
    }

    @Override
    public String toString() {
        return "BrandInfo{" +
        "brandId=" + brandId +
        ", brandCode=" + brandCode +
        ", brandName=" + brandName +
        ", createTime=" + createTime +
        "}";
    }
}
