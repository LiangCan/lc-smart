package com.sykj.uusmart.handle;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.mqtt.MqIotMessage;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.mqtt.cmd.MqIotDeviceLoginDTO;
import com.sykj.uusmart.mqtt.cmd.MqIotSysObjectDTO;
import com.sykj.uusmart.mqtt.cmd.resp.MqIotRespDeviceLoginDTO;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.pojo.Wisdom;
import com.sykj.uusmart.service.WisdomService;
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
    ServiceConfig serviceConfig;

    @Autowired
    WisdomService wisdomService;

    //第一步解析
    public MqIotMessage handle(MqIotMessageDTO mqIotMessageDTO) throws CustomRunTimeException {

        MqIotMessage mqIotMessage = mqIotUtils.handReq(mqIotMessageDTO ,serviceConfig.getTCP_VERSION(), ConfigGetUtils.serviceConfig.getMQTT_CLIENT_NAME());
        //业务处理
        switch (mqIotMessageDTO.getHeader().getActionType()) {
            case syn :
                MqIotSysObjectDTO mqIotSysObjectDTO = mqIotUtils.toObj(mqIotMessageDTO, MqIotSysObjectDTO.class);
                wisdomService.synWisdom(mqIotSysObjectDTO, mqIotMessage.getDeviceInfo());
                break;
            default:
                return mqIotMessage;
        }
        return mqIotMessage;
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
