/**
 * 管理初始化
 */
var BrandInfo = {
    id: "BrandInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BrandInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '品牌id', field: 'brandId', visible: true, align: 'center', valign: 'middle'},
            {title: '品牌代码', field: 'brandCode', visible: true, align: 'center', valign: 'middle'},
            {title: '品牌名称', field: 'brandName', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BrandInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BrandInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
BrandInfo.openAddBrandInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/brandInfo/brandInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
BrandInfo.openBrandInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/brandInfo/brandInfo_update/' + BrandInfo.seItem.brandId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
BrandInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/brandInfo/delete", function (data) {
            Feng.success("删除成功!");
            BrandInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("brandInfoId",this.seItem.brandId);
        ajax.start();
    }
};

/**
 * 查询列表
 */
BrandInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BrandInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BrandInfo.initColumn();
    var table = new BSTable(BrandInfo.id, "/brandInfo/list", defaultColunms);
    table.setPaginationType("client");
    BrandInfo.table = table.init();
});
