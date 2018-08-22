package com.sykj.uusmart.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="t_product_info")
public class ProductInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="pid", length = 16)
    private Long pid;

    @Column(name="brand_code", columnDefinition=" bigint(8)  COMMENT '品牌代码' ")
    private Short brandCode;

    @Column(name="brand_name", columnDefinition=" varchar(32)  COMMENT '品牌名称' ")
    private String brandName;

    @Column(name="model_code", columnDefinition=" bigint(16)  COMMENT '型号代码' ")
    private Short modelCode;

    @Column(name="model_name", columnDefinition=" varchar(32)  COMMENT '型号名称' ")
    private String modelName;

    @Column(name="type_code", columnDefinition=" bigint(8)  COMMENT '类型代码' ")
    private Long typeCode;

    @Column(name="type_name", columnDefinition=" varchar(32)  COMMENT '类型名称' ")
    private String typeName;

    @Column(name="create_time", columnDefinition=" bigint(13)  COMMENT '创建时间' ")
    private Long createTime;

    @Column(name="product_icon", columnDefinition=" varchar(32) DEFAULT NULL COMMENT '产品图标' ")
    private String productIcon;

    @Column(name="is_to_tiammao", columnDefinition=" tinyint(1) DEFAULT NULL COMMENT '是否接入天猫' ")
    private boolean isToTiamMao;

    @Column(name="tianmao_type_name", columnDefinition=" varchar(32) DEFAULT NULL COMMENT ' 天猫精灵的名称 ' ")
    private String tiamMaoTypeName;

    @Column(name="is_to_baidu", columnDefinition=" tinyint(1) DEFAULT NULL COMMENT '是否接入百度' ")
    private boolean isToBaiDu;

    @Column(name="baidu_type_name", columnDefinition=" varchar(32) DEFAULT NULL COMMENT ' 百度的类型名称 ' ")
    private String baiDuTypeName;

    @Column(name="support_vivo", columnDefinition=" tinyint(1) DEFAULT NULL COMMENT '是否支持VIVO' ")
    private boolean supportVivo;


    public String getTiamMaoTypeName() {
        return tiamMaoTypeName;
    }

    public void setTiamMaoTypeName(String tiamMaoTypeName) {
        this.tiamMaoTypeName = tiamMaoTypeName;
    }


    public boolean isToBaiDu() {
        return isToBaiDu;
    }

    public void setToBaiDu(boolean toBaiDu) {
        isToBaiDu = toBaiDu;
    }

    public String getBaiDuTypeName() {
        return baiDuTypeName;
    }

    public void setBaiDuTypeName(String baiDuTypeName) {
        this.baiDuTypeName = baiDuTypeName;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Short getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(Short brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Short getModelCode() {
        return modelCode;
    }

    public void setModelCode(Short modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Long typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public boolean isToTiamMao() {
        return isToTiamMao;
    }

    public void setToTiamMao(boolean toTiamMao) {
        isToTiamMao = toTiamMao;
    }


    public boolean isSupportVivo() {
        return supportVivo;
    }

    public void setSupportVivo(boolean supportVivo) {
        this.supportVivo = supportVivo;
    }
}
