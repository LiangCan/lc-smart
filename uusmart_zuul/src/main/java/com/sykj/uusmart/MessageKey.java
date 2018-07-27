/**   
 * @Title: MessageKey.java 
 * @Package com.yijucloud.menu 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author Henry  
 * @date 2015年7月28日 下午3:43:20 
 * @version V1.0   
 */
package com.sykj.uusmart;

/**
 * @ClassName: MessageKey
 * @Description: 返回错误key
 * @author lichao
 * @date 2015年7月28日 下午3:43:20
 * @version 1.0
 */
public interface MessageKey
{
    /** 未知系统错误  0 */
    String SYSTEM_ERROR = "system.error";

    /** 参数值错误 10001*/
    String PARAM_VALUE_INVALID = "valid.param_value_invalid";
    /** 参数长度错误 10001*/
    String PARAM_VALUE_LENGTH = "valid.param_value_length_error";
    /** 缺少必备参数  10001*/
    String MISS_REQUIRE_PARAM = "valid.require_param_miss";
    /** 参数格式错误  10002*/
    String PARAM_FORMAT_ERROR = "valid.param_format_error";
    /** token不存在或者已过期 10003 */
    String PARAM_TOKEN_ERROR = "valid.param_token_error";

    /** 数据保存异常 20001*/
    String SERVICE_DATA_SAVE_EXCEPTION = "servie.data_save_excepiton";

    /** 修改保存异常 20002*/
    String SERVICE_DATA_UPDATE_EXCEPTION = "servie.data_update_excepiton";

    /** 删除保存异常 20003*/
    String SERVICE_DATA_DELETE_EXCEPTION = "servie.data_delete_excepiton";

    /** 查看数据异常 20004*/
    String SERVICE_DATA_QUERY_EXCEPTION = "servie.data_query_excepiton";

    /** mqtt 链接异常 */
    String SERVICE_MQTT_CONNETC_ERROR = "servie.mqtt_connetc_error";

    /** 文件上传异常 */
    String SERVICE_FILE_UPLOAD_ERROR = "servie.file_upload_error";

    /** 数据不存在 */
    String SERVICE_DATA_IS_NULL = "service.data_is_null";

    /**  第三方服务错误 20008 */
    String SERVICE_THIRD_PARTY_ERROR = "servie.third_party_error";
    interface api{
        /** 没有权限 */
        String API_NOT_JURISDICTION = "api.not_jurisdiction";

        /** 该手机号码已经存在 */
        String API_DATA_EXISTENCE = "api.data_existence";

        /** 帐号或者密码错误 */
        String API_MOBILE_OR_PASSWORD_ERROR = "api.mobile_or_password_error";

        /** 校验码过期或者不存在 */
        String API_CHECK_CODE_ERROR = "api.check_code_error";

        /** 设备已经被绑定了 */
        String API_DEVICE_IS_BINDING = "api.devie_is_binding";

        /** 找不到该用户的登录信息 */
        String API_USER_NOT_LOGIN = "api.user_not_login";

        /** 设备不存在 */
        String API_DEVICE_NOT_EXISTENCE = "api.device_not_existence";

        /** 用户未绑定设备 */
        String API_USER_NOT_BINDING_DEVICE = "api.user_not_binding_device";

        /** 数据不存在 */
        String API_DATA_NOT_EXISTENCE = "api.data_not_existence";

        /** 房间不存在 */
        String API_ROOM_NOT_EXISTENCE = "api.room_not_existence";

        /** 已经分享过给该用户了 */
        String API_ALREADY_SHARE = "api.already_share";

        /** 家庭不存在 */
        String API_HOME_NOT_EXISTENCE = "api.home_not_existence";

        /** 积分不足 */
        String API_INTEGRAL_INSUFFICIENT = "api.integral_insufficient";

        /** 订单不存在或者已经撤销 */
        String API_WORK_ORDER_NOT_EXISTENCE = "api.work_order_not_existence";

        /** 任务不存在或者已经撤销 */
        String API_TASK_NOT_EXISTENCE = "api.task_not_existence";

        /** 场景不存在 */
        String API_SCENE_NOT_EXISTENCE = "api.scene_not_existence";

        /** 场景没有执行任务 */
        String API_SCENE_NOT_TASK = "api.scene_not_task";

        /** 添加积分超出上限 */
        String API_ADD_INTEGRAL_ERROR = "api.add_integral_error";
    }
}
