/**
 * @Title: Constants.java
 * @Package com.yijucloud.menu
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Henry
 * @date 2015年7月28日 下午2:11:54
 * @version V1.0
 */
package com.sykj.uusmart;



public interface Constants
{

    String CHARSET = "UTF-8";

    /** 服务端通用主状态 */
    interface mainStatus{
        /** 成功 */
        int SUCCESS = 1;

        /** 失败 */
        int FAIL = 9;

        /** 未知 */
        int UNKNOWN = 0;

        String SYSTEM_ERROR = "系统异常";

        String REQUEST_SUCCESS = "请求成功";
    }

    /** 主协议版本 */
    String  AGREEMENT_VERSION_INFO = "1.0.0";

    /** 特殊符号*/
    interface specialSymbol{

        /** url分割线  */
        String URL_SEPARATE = "/";

        /** 逗号分割  */
        String COMMA = ",";

        /**  点  */
        String POINT = ".";

        /** 分号  */
        String SEMICOLON = ";";

        String COLOM = ":";

        String UNDERLINE = "_";

        String ADD="+";

    }

    /** short 1~10 */
    interface shortNumber{

        short ZERO = 0;

        short ONE = 1;

        short TWO =2;

        short THREE = 3;

        short FOUR = 4;

        short FIVE = 5;

        short SIX = 6;

        short SEVEN = 7;

        short EIGHT = 8;

        short NINE = 9;

        short TEN = 10;
    }

    /** 返回CODE */
    interface resultCode{
        /** 未知系统错误  0 */
        String SYSTEM_ERROR = "0";
        /** 缺少必备参数  10001*/
        String PARAM_MISS = "10001";
        /** 参数长度错误  10002*/
        String PARAM_VALUE_LENGTH = "10002";
        /** 参数值错误 10003*/
        String PARAM_VALUE_INVALID = "10003";
        /** 参数格式错误  10004*/
        String PARAM_FORMAT_ERROR = "10004";
        /** 协议版本过低，请升级   10005*/
        String PARAM_AGREEMENT_ERROR = "10005";
        /** http请求类型错误   10006*/
        String PARAM_HTTP_REQ_TYPE_ERROR = "10006";

        /** 登录token无效   10100*/
        String API_LOGIN_TOKEN_INVALID = "10100";
        /** 邮箱发送频繁 24小时后重试   10101*/
        String API_PUSH_EMAIL_INVALID = "10101";
        /** 数据不存在   10102*/
        String API_DATA_IS_NULL = "10102";
        /** 数据已经存在   10103*/
        String API_DATA_IS_NOT_NULL = "10103";
        /** 用户对 XX 权限不够  10104*/
        String API_USER_JURISDICTION_ERROR = "10104";
        /** 创建的智能会形成闭环 10105*/
        String API_CREATE_WISDOM_ERROR = "10105";

        /** 设备不在线   20000*/
        String DEVICE_IS_OFF_LINE = "20000";
        /** 设备响应指令超时   20001*/
        String MSG_NOT_RESPONSE = "20001";


        /** 服务宕机了  30000*/
        String SERVER_ERROR = "30000";
        /** 第三方服务器异常 30001*/
        String THIRD_PARTY_SERVER_ERROR = "30001";
        /** 发送设备失败   30002*/
        String PUSH_MSG_ERROR = "30002";
    }

    /** 返回CODE 的说明 */
    interface systemError{
        /** 未知系统错误  0 */
        String SYSTEM_ERROR = "system.error";
        /** 缺少必备参数  10001*/
        String PARAM_MISS = "valid.param_miss";
        /** 参数长度错误  10002*/
        String PARAM_VALUE_LENGTH = "valid.param_value_length_error";
        /** 参数值错误 10003*/
        String PARAM_VALUE_INVALID = "valid.param_value_invalid";
        /** 参数格式错误  10004*/
        String PARAM_FORMAT_ERROR = "valid.param_format_error";
        /** http协议版本过低，请升级  10005*/
        String PARAM_AGREEMENT_ERROR = "valid.param_agreement_error";
        /** http请求类型错误   10006*/
        String PARAM_HTTP_REQ_TYPE_ERROR = "valid.param_http_req_type_error";


        /** 登录token无效   10100*/
        String API_LOGIN_TOKEN_INVALID = "api.login_token_invalid";
        /** 邮箱发送频繁 24小时后重试   10101*/
        String API_PUSH_EMAIL_INVALID = "api.push_email_invalid";
        /** 数据不存在   10102*/
        String API_DATA_IS_NULL = "api.data_is_null";
        /** 数据已经存在   10103*/
        String API_DATA_IS_NOT_NULL = "api.data_is_not_null";
        /** 用户对 XX 权限不够   10104*/
        String API_USER_JURISDICTION_ERROR = "api.user_jurisdiction_error";
        /** 创建的智能会形成闭环 10105*/
        String API_CREATE_WISDOM_ERROR = "api.create_wisdom_error";

        /** 设备不在线   20000*/
        String DEVICE_IS_OFF_LINE = "device.is_off_line";
        /** 设备响应指令超时   20001*/
        String MSG_NOT_RESPONSE = "device.msg_not_response";


        /** 服务宕机了  30000*/
        String SERVER_ERROR = "ser.server_error";
        /** 第三方服务器异常 30001*/
        String THIRD_PARTY_SERVER_ERROR = "ser.third_party_server_error";
        /** 发送设备失败   30002*/
        String PUSH_MSG_ERROR = "ser.push_msg_fail";
    }

    /**
     * 项目角色
     */
    interface role{
        String APP = "u";
        String DEVICE = "d";
        String SERVICE = "s";
        String WISDOM = "w";
        String TIMING = "t";
    }

    /**
     * 智能角色
     */
    interface wisdomRole{
        String WISDOM_GEN= "gen";
        String WISDOM_SUBS = "subs";
    }

    /**
     * 定时角色
     */
    String TIMING_ROLE = "task";

    interface deviceErrorCode{
        /**
         * 设备返回的格式错误
         */
        int SGACP_DEV_ADD_OBJ_OVERFLOW_ERROR = 22003;

        /**
         * 设备删除数据错误
         */
        int SGACP_DEV_DEL_OBJ_DATA_ERROR = 22004;
    }

    String DIY_REDIS_MSG_ID = "mqtt:msg:id";

    int MSG_TIME_OUT_MAX =2;
}
