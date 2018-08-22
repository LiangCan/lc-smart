package com.sykj.uusmart.modular.product.controller;

import com.sykj.uusmart.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.sykj.uusmart.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.sykj.uusmart.modular.system.model.BrandInfo;
import com.sykj.uusmart.modular.product.service.IBrandInfoService;

/**
 * 品牌管理控制器
 *
 * @author fengshuonan
 * @Date 2018-08-06 09:39:28
 */
@Controller
@RequestMapping("/brandInfo")
public class BrandInfoController extends BaseController {

    private String PREFIX = "/product/brandInfo/";

    @Autowired
    private IBrandInfoService brandInfoService;

    /**
     * 跳转到品牌管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "brandInfo.html";
    }

    /**
     * 跳转到添加品牌管理
     */
    @RequestMapping("/brandInfo_add")
    public String brandInfoAdd() {
        return PREFIX + "brandInfo_add.html";
    }

    /**
     * 跳转到修改品牌管理
     */
    @RequestMapping("/brandInfo_update/{brandInfoId}")
    public String brandInfoUpdate(@PathVariable Integer brandInfoId, Model model) {
        BrandInfo brandInfo = brandInfoService.selectById(brandInfoId);
        model.addAttribute("item",brandInfo);
        LogObjectHolder.me().set(brandInfo);
        return PREFIX + "brandInfo_edit.html";
    }

    /**
     * 获取品牌管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return brandInfoService.selectList(null);
    }

    /**
     * 新增品牌管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BrandInfo brandInfo) {
        brandInfo.setCreateTime( System.currentTimeMillis() );
        brandInfoService.insert(brandInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除品牌管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long brandInfoId) {
        brandInfoService.deleteById(brandInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改品牌管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BrandInfo brandInfo) {
        brandInfoService.updateById(brandInfo);
        return SUCCESS_TIP;
    }

    /**
     * 品牌管理详情
     */
    @RequestMapping(value = "/detail/{brandInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("brandInfoId") Integer brandInfoId) {
        return brandInfoService.selectById(brandInfoId);
    }
}
