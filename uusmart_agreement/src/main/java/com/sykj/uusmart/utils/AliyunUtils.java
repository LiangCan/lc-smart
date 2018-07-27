package com.sykj.uusmart.utils;


import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.pojo.redis.UserCheckCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Liang on 2016/12/26.
 */
public class AliyunUtils {
    final static Map<Short, String> checkCodeType = new HashMap<Short, String>();
    static {
        checkCodeType.put((short) 1,"注册帐号");
        checkCodeType.put((short) 2,"重置密码");
    }

    final static String getEmailBody = "get.check_code.email.body";

    public static boolean sample(UserCheckCode userCheckCode, ResponseDTO responseDTO) {
        boolean result = false;
        /**
         * Step 1. 获取主题引用
         */
        CloudAccount account = new CloudAccount("LTAI8LDuMwQ5GqOe", "xCAbnvGgHD4pTPfTjzskDj4Kcxt4mt", "https://1049492741021570.mns.cn-shenzhen.aliyuncs.com/");
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef("sykj");
        /**
         * Step 2. 设置SMS消息体（必须）
         *
         * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
         */
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody("sms-message");
        /**
         * Step 3. 生成SMS消息属性
         */
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 3.1 设置发送短信的签名（SMSSignName）
        batchSmsAttributes.setFreeSignName("UUSmart");
        // 3.2 设置发送短信使用的模板（SMSTempateCode）
        batchSmsAttributes.setTemplateCode("SMS_67980142");
        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
        smsReceiverParams.setParam("title", checkCodeType.get(userCheckCode.getCodeType()));
        smsReceiverParams.setParam("code", String.valueOf(userCheckCode.getCheckCode()));
        // 3.4 增加接收短信的号码
        batchSmsAttributes.addSmsReceiver(userCheckCode.getAddress(), smsReceiverParams);
        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
        try {
            /**
             * Step 4. 发布SMS消息
             */
            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
            responseDTO.sethRA(String.valueOf(Constants.shortNumber.ONE));
            responseDTO.sethRD(null);
            result = true;
//            System.out.println("MessageId: " + ret.getMessageId());
//            System.out.println("MessageMD5: " + ret.getMessageBodyMD5());
        } catch (ServiceException se) {
//            System.out.println(se.getErrorCode() + se.getRequestId());
//            System.out.println(se.getMessage());
//            se.printStackTrace();
            responseDTO.sethRD(se.getMessage());
        } catch (Exception e) {
            responseDTO.sethRD("第三方短信平台异常");
//            e.printStackTrace();
        }
        client.close();
        return result;
    }



    /** 生成传入位的位数字校验码 */
    public static Integer getCheckUtil(int strLength) {
        --strLength;
        Random rm = new Random();
        int pross = (int)((1 + rm.nextDouble()) * Math.pow(10, strLength));
        return pross;
    }


    public static boolean sample(String address, Short  codeType, Integer emailCode) {
        boolean result = false;

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIFpQw3P8c8Adw", "fV4AvtkInnuWaVpGsGztVLsCkBMWmJ");
        IAcsClient client = new DefaultAcsClient(profile);


        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName("sykj-system@sykj-xzy.com");
            request.setFromAlias("UUsmart");
            request.setAddressType(1);
            request.setReplyToAddress(true);
            request.setToAddress(address);
            request.setSubject("UUSmart Email Check Code");
            System.out.println(ServiceUtils.messageUtils.getMessage(getEmailBody, new Object[]{checkCodeType.get(codeType) ,String.valueOf(emailCode)}));
            request.setHtmlBody(ServiceUtils.messageUtils.getMessage(getEmailBody, new Object[]{checkCodeType.get(codeType) ,String.valueOf(emailCode)}));
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            result = true;
        } catch (ServerException e) {
            e.printStackTrace();
            throw new CustomRunTimeException(Constants.resultCode.THIRD_PARTY_SERVER_ERROR, e.getMessage());
        }
        catch (ClientException e) {
            e.printStackTrace();
            throw new CustomRunTimeException(Constants.resultCode.PARAM_VALUE_INVALID, e.getMessage());
        }
        return result;
    }


//    public static void main(String [] args){
//        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
//        source.setBasename("message");
//        source.setDefaultEncoding("UTF-8");
//        source.setUseCodeAsDefaultMessage(true);
//
//        String s = source.getMessage(getEmailBody, new Object[]{checkCodeType.get((short)1) ,String.valueOf("123456")},Locale.CHINA);
//      for(int i= 0; i < 1000; i++){
//          System.out.println( AliyunUtils.getCheckUtil(6));
//
//      }
//    }

}
