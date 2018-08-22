package com.sykj.uusmart.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
@TableName("sys_role")
@Table(name="sys_role")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id", length = 16)
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 序号
     */
	@Column(name = "num", columnDefinition = "序号' ")
	private Integer num;
    /**
     * 父角色id
     */
	@Column(name = "pid", columnDefinition = "父角色id' ")
	private Integer pid;
    /**
     * 角色名称
     */
	@Column(name = "name", columnDefinition = "角色名称' ")
	private String name;
    /**
     * 部门名称
     */
	@Column(name = "deptid", columnDefinition = "部门名称' ")
	private Integer deptid;
    /**
     * 提示
     */
	@Column(name = "tips", columnDefinition = "提示' ")
	private String tips;
    /**
     * 保留字段(暂时没用）
     */
	@Column(name = "pid", columnDefinition = "保留字段' ")
	private Integer version;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Role{" +
			"id=" + id +
			", num=" + num +
			", pid=" + pid +
			", name=" + name +
			", deptid=" + deptid +
			", tips=" + tips +
			", version=" + version +
			"}";
	}
}
