package com.sykj.uusmart.mqtt;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotErrorRespBodyDTO;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotRespCodeDTO;
import com.sykj.uusmart.mqtt.push.MqIotMsgCallBack;
import com.sykj.uusmart.mqtt.push.impl.MqIotMsgCallBackImpl;
import com.sykj.uusmart.pojo.CacheMessage;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.pojo.DeviceMesgLog;
import com.sykj.uusmart.pojo.DeviceVersionInfo;
import com.sykj.uusmart.repository.CacheMessageRepository;
import com.sykj.uusmart.repository.DeviceInfoRepository;
import com.sykj.uusmart.repository.DeviceMesgLogRepository;
import com.sykj.uusmart.repository.DeviceVersionInfoRepository;
import com.sykj.uusmart.utils.ExecutorUtils;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/6/4 0004.
 */
@Component
public class MqIotUtils {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    DeviceMesgLogRepository deviceMesgLogRepository;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    MqIotMsgCallBackImpl mqIotMsgCallBackImpl;

    @Autowired
    CacheMessageRepository cacheMessageRepository;

    /**
     * 消息等待秒数
     */
    private Integer MQTT_PUSH_MSG_TIMEOUT = 2000;

    /**
     * 发送消息队列
     */
    public static Map<Integer, MqIotMessage> PUSH_MSG_MAP;

    public static ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(50);

    static {
        PUSH_MSG_MAP = new ConcurrentHashMap<>();
    }

    /**
     * 获取角色 1APP， 2 设备， 3 服务器
     * @param type
     * @return
     */
    public static String getRole(int type){
        StringBuffer role = new StringBuffer();
        switch (type){
            case 1:
                role.append( Constants.role.APP);
                break;
            case 2:
                role.append( Constants.role.DEVICE);
                break;
            case 3:
                role.append( Constants.role.SERVICE);
                break;
            case 4:
                role.append( Constants.role.SERVICE);
                break;
        }
        role.append(Constants.specialSymbol.URL_SEPARATE);
        return role.toString();
    }

    /**
     * 判断是否有要回复的消息
     * @param mqIotMessage
     */
    public void isRespHandleMessage(MqIotMessage mqIotMessage){
        if (mqIotMessage != null && mqIotMessage.getRespBody() != null) {
            setRespDestId(mqIotMessage.getRespBody());
            mqIotPushMsg(mqIotMessage.getDeviceInfo(), mqIotMessage.getRespBody());
        }
    }


    /**
     * 发送透传的消息
     */
    public void pushDeviceTransferMsg(MqIotMessageDTO mqIotMessageDTO) throws CustomRunTimeException {
        MQTTUtils.push(mqIotMessageDTO.getHeader().getDestId(), GsonUtils.toJSON(mqIotMessageDTO));
    }


    /**
     * 发送智能家居消息格式的消息
     * 并且获取返回的数据
     */
    public void mqIotPushMsgAndGetResult(MqIotMessage mqttIotMessage) {

        // 清理额外数据，如果有返回body 就设置返回body 为发送body
        handleAdditionalData(mqttIotMessage);

        //从redis里面获取id 设置msgId
        if(mqttIotMessage.getReSendNumber() ==  Constants.shortNumber.ONE){
            setMsgId(mqttIotMessage.getMqIotMessageDTO());
        }
        //设置回调函数
        mqttIotMessage.setMqIotMsgCallBack(mqIotMsgCallBackImpl);

        //添加已经发送的队列
        PUSH_MSG_MAP.put(mqttIotMessage.getMqIotMessageDTO().getHeader().getTokenId(), mqttIotMessage);

        MQTTUtils.push(mqttIotMessage.getMqIotMessageDTO().getHeader().getDestId(), GsonUtils.toJSON(mqttIotMessage.getMqIotMessageDTO()).replace("\\",""));

        //设置消息超时处理
        HandeMqIotMsgTimeOut handeMqIotMsgTimeOut = new HandeMqIotMsgTimeOut();
        handeMqIotMsgTimeOut.setMqIotMessage(mqttIotMessage);
        ScheduledFuture<?> scheduledFuture = timer.schedule(handeMqIotMsgTimeOut, MQTT_PUSH_MSG_TIMEOUT, TimeUnit.MILLISECONDS);
        mqttIotMessage.setHandeMqIotMsgTimeOut(scheduledFuture);

        //睡眠
        try {
            mqttIotMessage.getLatch().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean handleCallBack(MqIotMessageDTO mqIotMessageDTO){
        //判断是否要回调函数处理的参数
        if (MqIotUtils.PUSH_MSG_MAP.containsKey(mqIotMessageDTO.getHeader().getTokenId())) {
            MqIotMessage mqIotMessage = MqIotUtils.PUSH_MSG_MAP.get(mqIotMessageDTO.getHeader().getTokenId());
            mqIotMessage.setRespBody(mqIotMessageDTO);
            MqIotErrorRespBodyDTO mqIotErrorRespBodyDTO = toObj(mqIotMessageDTO, MqIotErrorRespBodyDTO.class);

            //判断成功还是失败
            if (mqIotErrorRespBodyDTO.getCode() == Constants.mainStatus.SUCCESS) {
                MqIotUtils.PUSH_MSG_MAP.get(mqIotMessageDTO.getHeader().getTokenId()).getMqIotMsgCallBack().onSuccess(mqIotMessage);
            } else {
                MqIotUtils.PUSH_MSG_MAP.get(mqIotMessageDTO.getHeader().getTokenId()).setErrorCode(mqIotErrorRespBodyDTO.getCode());
                MqIotUtils.PUSH_MSG_MAP.get(mqIotMessageDTO.getHeader().getTokenId()).getMqIotMsgCallBack().onFail(mqIotMessage);
            }
            return true;
        }
        return false;
    }

    /**
     * 以事物格式发送智能家居
     * 并且获取返回的数据
     */
    public void mqIotPushThingMsg(MqIotThingMessage mqIotThingMessage) {
        //遍历发送消息队列 ， 执行发送等待响应
        for (int pushIndex = 0; pushIndex < mqIotThingMessage.getPushMsgs().size(); pushIndex++) {
            int index = pushIndex;
            ExecutorUtils.cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mqIotPushMsgAndGetResult(mqIotThingMessage.getPushMsgs().get(index));
                    mqIotThingMessage.latch.countDown();
                }
            });
        }

        //睡眠直到消息全部发送完毕
        try {
            mqIotThingMessage.latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //遍历发送的消息对象
        for(MqIotMessage mqIotMessage : mqIotThingMessage.getPushMsgs()){
            //判断是否发送成功
            if(mqIotMessage.getStatus() != Constants.mainStatus.SUCCESS || mqIotMessage.getRespBody() == null){
                mqIotThingMessage.setState(Constants.shortNumber.NINE);
                break;
            }
            //设置成功的数量
            mqIotThingMessage.setSuccessNumber(mqIotThingMessage.getSuccessNumber() +  Constants.shortNumber.ONE);
        }

        //判断是否全部成功
        if(mqIotThingMessage.getSuccessNumber() == mqIotThingMessage.getPushMsgs().size()){
            mqIotThingMessage.setState(Constants.shortNumber.ONE);
            return;
        }

        //发送回滚消息
        if(mqIotThingMessage.isCallback()){
            for (MqIotMessageDTO mqIotMessageDTO : mqIotThingMessage.getCallbackMsgs()) {
                ExecutorUtils.cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
//                        mqIotPushMsg(mqIotMessageDTO);
                    }
                });
            }
        }
    }

    /**
     * 处理设备的缓存消息
     * @param did
     */
    public void handleCacheMessage(Long did, String sourId){

        ExecutorUtils.cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                //从数据库里面获取设备的 缓存消息
                List<CacheMessage> list = cacheMessageRepository.queryByDestId(Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + did);
                for(CacheMessage cacheMessage : list){

                    MqIotMessageDTO mqIotMessageDTO = GsonUtils.toObj(cacheMessage.getCmd(), MqIotMessageDTO.class);
                    mqIotMessageDTO.getHeader().setTimestamp(String.valueOf(System.currentTimeMillis()));
                    mqIotMessageDTO.getHeader().setSourceId(sourId);
                    MqIotMessage pushMsg = new MqIotMessage(mqIotMessageDTO);
                    mqIotPushMsgAndGetResult(pushMsg);

                    if(pushMsg.getStatus() == Constants.mainStatus.SUCCESS){
                        cacheMessageRepository.delete(cacheMessage.getCid());

                    }else if(pushMsg.getErrorCode() ==  Constants.deviceErrorCode.SGACP_DEV_DEL_OBJ_DATA_ERROR){
                        cacheMessageRepository.delete(cacheMessage.getCid());

                    }else if(pushMsg.getErrorCode() ==  Constants.deviceErrorCode.SGACP_DEV_ADD_OBJ_OVERFLOW_ERROR){
                        cacheMessageRepository.delete(cacheMessage.getCid());
                    }
                }
            }
        });
    }

    /**
     * 发送智能家居消息格式的消息
     */
    public void mqIotPushMsg(MqIotMessage mqIotMessage) {
        // 处理额外数据，如果有返回body 就设置返回body 为发送body
        handleAdditionalData(mqIotMessage);
        //从redis里面获取id 设置msgId
        setMsgId(mqIotMessage.getMqIotMessageDTO());
        MQTTUtils.push(mqIotMessage.getMqIotMessageDTO().getHeader().getDestId(), GsonUtils.toJSON(mqIotMessage.getMqIotMessageDTO()));
    }

    /**
     * 发送智能家居消息格式的消息
     */
//    public void mqIotPushMsg(MqIotMessageDTO mqIotMessage) {
//        //从redis里面获取id 设置msgId
//        setMsgId(mqIotMessage);
//        MQTTUtils.push(mqIotMessage.getHeader().getDestId(), GsonUtils.toJSON(mqIotMessage));
//    }

    /**
     * 根据设备来发送消息
     */
    public void mqIotPushMsg(DeviceInfo deviceInfo, MqIotMessageDTO mqIotMessage) {
        //从redis里面获取id 设置msgId
        setMsgId(mqIotMessage);
        String pushTopic = mqIotMessage.getHeader().getDestId();
        if(deviceInfo.getClassification().equals(Constants.shortNumber.TWO) && deviceInfo.getMainDeviceId() != null){
            pushTopic = getRole(Constants.shortNumber.TWO) + deviceInfo.getMainDeviceId();
        }
        MQTTUtils.push(pushTopic, GsonUtils.toJSON(mqIotMessage));
    }

    /**
     * 从redis里面获取id 设置msgId
     *
     * @param mqIotMessageDTO
     */
    public void setMsgId(MqIotMessageDTO mqIotMessageDTO) {
        Long msgId = stringRedisTemplate.opsForValue().increment(Constants.DIY_REDIS_MSG_ID, 1);
        if (msgId > 100) {
            stringRedisTemplate.delete(Constants.DIY_REDIS_MSG_ID);
        }
        mqIotMessageDTO.getHeader().setMsgSeqId(msgId);
    }


    /**
     * 处理额外数据
     * 如果有返回body 就设置返回body 为发送body
     *
     * @return
     */
    public void handleAdditionalData(MqIotMessage mqIotMessage) {
        if (mqIotMessage.getRespBody() != null) {
            String src = mqIotMessage.getMqIotMessageDTO().getHeader().getSourceId();
            mqIotMessage.getMqIotMessageDTO().getHeader().setSourceId(mqIotMessage.getMqIotMessageDTO().getHeader().getDestId());
            mqIotMessage.getMqIotMessageDTO().getHeader().setDestId(src);
            mqIotMessage.getMqIotMessageDTO().getHeader().setPacketType(Constants.shortNumber.ONE);
            mqIotMessage.getMqIotMessageDTO().setBody(mqIotMessage.getRespBody());
        }
    }

    public void setRespDestId(MqIotMessageDTO mqIotMessage){
        String src = mqIotMessage.getHeader().getSourceId();
        mqIotMessage.getHeader().setSourceId(mqIotMessage.getHeader().getDestId());
        mqIotMessage.getHeader().setDestId(src);
        mqIotMessage.getHeader().setPacketType(Constants.shortNumber.ONE);
    }

    /**
     * 保存智能家居消息格式的消息
     *
     * @param mqttIotMessage
     */
    public void mqIotSaveMsg(MqIotMessageDTO mqttIotMessage) {
        MqIotHeaderDTO mqIotHeaderDTO = mqttIotMessage.getHeader();
        DeviceMesgLog deviceMesgLog = new DeviceMesgLog(mqIotHeaderDTO.getActionType().getName(), mqIotHeaderDTO.getTransferType(), mqIotHeaderDTO.getPacketType(), mqIotHeaderDTO.getTokenId(), mqIotHeaderDTO.getSourceId(), mqIotHeaderDTO.getDestId(), mqIotHeaderDTO.getEncryptType(), mqIotHeaderDTO.getMsgSeqId(), Long.parseLong(mqIotHeaderDTO.getTimestamp()), mqIotHeaderDTO.getVersion(), GsonUtils.toJSON(mqttIotMessage.getBody()));
        deviceMesgLog.setResultCode(String.valueOf(Constants.mainStatus.SUCCESS));
        deviceMesgLog.setRemarks(Constants.mainStatus.REQUEST_SUCCESS);
        deviceMesgLogRepository.save(deviceMesgLog);
    }

    /**
     * 保存智能家居消息格式的消息
     *
     * @param mqttIotMessage
     */
    public void mqIotSaveErrorMsg(MqIotMessageDTO mqttIotMessage, String code, String remarks) {
        MqIotHeaderDTO mqIotHeaderDTO = mqttIotMessage.getHeader();
        DeviceMesgLog deviceMesgLog = new DeviceMesgLog(mqIotHeaderDTO.getActionType().getName(), mqIotHeaderDTO.getTransferType(), mqIotHeaderDTO.getPacketType(), mqIotHeaderDTO.getTokenId(), mqIotHeaderDTO.getSourceId(), mqIotHeaderDTO.getDestId(), mqIotHeaderDTO.getEncryptType(), mqIotHeaderDTO.getMsgSeqId(), Long.parseLong(mqIotHeaderDTO.getTimestamp()), mqIotHeaderDTO.getVersion(), GsonUtils.toJSON(mqttIotMessage.getBody()));
        deviceMesgLog.setResultCode(code);
        deviceMesgLog.setRemarks(remarks);
        deviceMesgLogRepository.save(deviceMesgLog);
    }

    @Autowired
    DeviceVersionInfoRepository deviceVersionInfoRepository;

    /**
     * 创建设备升级推送指令
     */
    public DeviceVersionInfo createUpDeviceCMD(Long pid) {
        Sort sort = new Sort(Sort.Direction.DESC, "version");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<DeviceVersionInfo> page = deviceVersionInfoRepository.findPidNewVersion(pid, pageable);
        return page != null && page.getContent().size() > 0 ? page.getContent().get(0) : null;
    }

    /**
     * 获取body
     *
     * @return
     */
    public <T> T toObj(MqIotMessageDTO mqIotMessageDTO, Class<T> t) {
        //获取Body
        String bodyStr = GsonUtils.toJSON(mqIotMessageDTO.getBody());
        T body = GsonUtils.toObj(bodyStr, t);
        mqIotMessageDTO.setBody(body);
//        ValidatorUtils.validata(mqIotMessageDTO.getBody(), mqIotMessageDTO);
        return body;
    }


    /**
     * 1处理请求的headr, 转换body
     */
    public MqIotMessage handReq(MqIotMessageDTO mqIotMessageDTO, String mqIotVersion, String clientName) throws CustomRunTimeException {
        MqIotMessage result = new MqIotMessage();
        String[] srcs = mqIotMessageDTO.getHeader().getSourceId().split(Constants.specialSymbol.URL_SEPARATE);

        //判断发送者是否 是设备端,如果不是，将不会处理 抛出异常保存日志，
        if (!Constants.role.DEVICE.equals(srcs[0])) {
            throw new CustomRunTimeException(Constants.resultCode.PARAM_VALUE_INVALID, Constants.systemError.PARAM_VALUE_INVALID, new Object[]{"SourceId"}, mqIotMessageDTO);
        }

        //获取处理的Device
        Long did = Long.parseLong(srcs[1]);
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(did);
        if (deviceInfo == null) {
            throw new CustomRunTimeException(Constants.resultCode.API_DATA_IS_NULL, Constants.systemError.API_DATA_IS_NULL, new Object[]{"Device Info"}, mqIotMessageDTO);
        }
        result.setDeviceInfo(deviceInfo);

        //判断版本是否过低 推送设备升级,并且抛出异常保存日志
        if (mqIotMessageDTO.getHeader().getVersion().compareTo(mqIotVersion) < 0) {
            DeviceVersionInfo deviceVersionInfo = createUpDeviceCMD(deviceInfo.getProductId());

            MqIotMessageDTO mqIotMessageDTO1 = new MqIotMessageDTO(CmdListEnum.upgrade, clientName, mqIotMessageDTO.getHeader().getSourceId(), deviceVersionInfo);
            setRespDestId(mqIotMessageDTO1);
            mqIotPushMsg(deviceInfo, mqIotMessageDTO1);
            throw new CustomRunTimeException(Constants.resultCode.PARAM_AGREEMENT_ERROR, Constants.systemError.PARAM_AGREEMENT_ERROR, new Object[]{"tcp"}, mqIotMessageDTO);
        }

        result.setMqIotMessageDTO(mqIotMessageDTO);
        return result;
    }

    /**
     * 创建并且发送升级推送指令
     */
//    public void createUpDeviceCMDAndSend(MqIotMessage mqIotMessage) {
//        DeviceVersionInfo deviceVersionInfo = createUpDeviceCMD(mqIotMessage.getDeviceInfo().getProductId());
//        if (deviceVersionInfo != null) {
//            MqIotMessageDTO mqIotMessageDTO1 = new MqIotMessageDTO(CmdListEnum.upgrade,
//                    co,
//                    mqIotMessage.getMqIotMessageDTO().getHeader().getSourceId() ,
//                    deviceVersionInfo, null);
//            mqIotMessageDTO1.setDeviceInfo(mqIotMessage.getDeviceInfo());
//            mqIotPushMsg(mqIotMessageDTO1,mqIotMessageDTO1.getHeader().getVersion());
//        }
//    }

}
