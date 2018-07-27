package com.sykj.uusmart.handle;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.mqtt.cmd.MqIotDeviceLoginDTO;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotRespDeviceLoginDTO;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.pojo.DeviceVersionInfo;
import com.sykj.uusmart.service.DeviceService;
import com.sykj.uusmart.service.UserService;
import com.sykj.uusmart.utils.ConfigGetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Liang on 2017/1/3.
 */
@Component
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class CmdHandle {


    @Autowired
    MqIotUtils mqIotUtils;

    @Autowired
    DeviceService deviceService;

    @Autowired
    UserService userService;

    //第一步解析
    public MqIotMessage handle(MqIotMessageDTO mqIotMessageDTO) throws CustomRunTimeException {

        //处理透传
        if (Constants.shortNumber.ONE == mqIotMessageDTO.getHeader().getTransferType()) {
            mqIotUtils.pushDeviceTransferMsg(mqIotMessageDTO);
            return new MqIotMessage(mqIotMessageDTO);
        }

        MqIotMessage mqIotMessage = handReq(mqIotMessageDTO);
        //业务处理
        switch (mqIotMessageDTO.getHeader().getActionType()) {
            case login:
                userService.pushDeviceAllUser(mqIotMessage);
                MqIotRespDeviceLoginDTO mqIotRespDeviceLoginDTO = deviceService.handelDeviceLogin(mqIotUtils.toObj(mqIotMessage.getMqIotMessageDTO(), MqIotDeviceLoginDTO.class));
                handResp(mqIotMessage, mqIotRespDeviceLoginDTO);
                break;
            case inform:
                userService.isPushDeviceStatus(mqIotMessage);
                deviceService.handelDeviceInform(mqIotMessage);
                break;
            case disconn:
                deviceService.handelDeviceDiscontrol(mqIotMessage);
                userService.pushDeviceAllUser(mqIotMessage);
                break;
            default:
                return mqIotMessage;
        }
        return mqIotMessage;
    }


    /**
     * 1处理请求的headr, 转换body
     */
    public MqIotMessage handReq(MqIotMessageDTO mqIotMessageDTO) throws CustomRunTimeException {
        MqIotMessage result = new MqIotMessage();
        String[] srcs = mqIotMessageDTO.getHeader().getSourceId().split(Constants.specialSymbol.URL_SEPARATE);

        //判断发送者是否 是设备端,如果不是，将不会处理 抛出异常保存日志，
        if (!Constants.role.DEVICE.equals(srcs[0])) {
            throw new CustomRunTimeException(Constants.resultCode.PARAM_VALUE_INVALID, Constants.systemError.PARAM_VALUE_INVALID, new Object[]{"SourceId"}, mqIotMessageDTO);
        }

        //获取处理的Device
        Long did = Long.parseLong(srcs[1]);
        DeviceInfo deviceInfo = ConfigGetUtils.deviceInfoRepository.findOne(did);
        if (deviceInfo == null) {
            throw new CustomRunTimeException(Constants.resultCode.API_DATA_IS_NULL, Constants.systemError.API_DATA_IS_NULL, new Object[]{"Device Info"}, mqIotMessageDTO);
        }
        result.setDeviceInfo(deviceInfo);

        //判断版本是否过低 推送设备升级,并且抛出异常保存日志
        if (mqIotMessageDTO.getHeader().getVersion().compareTo(ConfigGetUtils.serviceConfig.getTCP_VERSION()) < 0) {
            DeviceVersionInfo deviceVersionInfo = mqIotUtils.createUpDeviceCMD(deviceInfo.getProductId());

            MqIotMessageDTO mqIotMessageDTO1 = new MqIotMessageDTO(CmdListEnum.upgrade, ConfigGetUtils.serviceConfig.getMQTT_CLIENT_NAME(), mqIotMessageDTO.getHeader().getSourceId(), deviceVersionInfo);
            mqIotUtils.setRespDestId(mqIotMessageDTO1);
            mqIotUtils.mqIotPushMsg(mqIotMessageDTO1);
            throw new CustomRunTimeException(Constants.resultCode.PARAM_AGREEMENT_ERROR, Constants.systemError.PARAM_AGREEMENT_ERROR, new Object[]{"tcp"}, mqIotMessageDTO);
        }

        result.setMqIotMessageDTO(mqIotMessageDTO);
        return result;
    }


    /**
     * 处理返回的 head, 并且发送出去
     *
     * @param t
     * @return
     */
    public void handResp(MqIotMessage mqIotMessage, Object t) {
        mqIotMessage.setRespBody(mqIotMessage.getMqIotMessageDTO());
        mqIotMessage.getRespBody().getHeader().setTimestamp(String.valueOf(System.currentTimeMillis()));
        mqIotMessage.getRespBody().setBody(t);
    }

//    public static void main(String[] args) {

//        Map<String, String> map = new HashMap<>();
//        map.put("code","1");
//        map.put("errorMsg","测试数据");
//        MqIotMessageDTO mqIotMessageDTO = new MqIotMessageDTO(CmdListEnum.login,
//                "d/123",
//                "s/devSer",map);
//        Gson gson = new Gson();


//        String ii = "{\"header\":{\"transferType\":0,\"packetType\":0,\"tokenId\":168296,\"sourceId\":\"d/123\",\"destId\":\"s/devSer\",\"encryptType\":0,\"timestamp\":\"1528966974983\",\"version\":\"0.0.1\",\"actionType\":\"login\"},\"body\":{\"code\":\"1\",\"errorMsg\":\"测试数据\"}}";
//        MqIotMessageDTO<MqIotErrorRespBodyDTO> mqIotMessageDTO = gson.fromJson(ii,MqIotMessageDTO.class);
//        System.out.println( mqIotMessageDTO.getBody().getCode());
//    }
}
