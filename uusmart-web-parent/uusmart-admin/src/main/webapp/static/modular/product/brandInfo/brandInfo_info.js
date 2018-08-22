/**
 * 初始化详情对话框
 */
var BrandInfoInfoDlg = {
    brandInfoInfoData : {}
};

/**
 * 清除数据
 */
BrandInfoInfoDlg.clearData = function() {
    this.brandInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BrandInfoInfoDlg.set = function(key, val) {
    this.brandInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BrandInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BrandInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.BrandInfo.layerIndex);
}

/**
 * 收集数据
 */
BrandInfoInfoDlg.collectData = function() {
    this
    .set('brandId')
    .set('brandCode')
    .set('brandName')
    .set('createTime');
}

/**
 * 提交添加
 */
BrandInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/brandInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.BrandInfo.table.refresh();
        BrandInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.brandInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BrandInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/brandInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.BrandInfo.table.refresh();
        BrandInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.brandInfoInfoData);
    ajax.start();
}

$(function() {

});
