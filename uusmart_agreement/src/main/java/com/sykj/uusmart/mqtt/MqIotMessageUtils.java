package com.sykj.uusmart.mqtt;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.mqtt.cmd.MqIotAddObjectGenDTO;
import com.sykj.uusmart.mqtt.cmd.MqIotAddObjectSubsDTO;
import com.sykj.uusmart.mqtt.cmd.MqIotDeviceControllerTO;
import com.sykj.uusmart.mqtt.cmd.input.MqIotConditionDTO;
import com.sykj.uusmart.mqtt.cmd.timing.MqIotAddTimingBaseDTO;
import com.sykj.uusmart.mqtt.cmd.timing.MqIotDeleteTimingBaseDTO;
import com.sykj.uusmart.mqtt.cmd.timing.input.MqIotTimerTaskDTO;
import com.sykj.uusmart.utils.GsonUtils;
import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Administrator on 2018/6/4 0004.
 */
@Component
public class MqIotMessageUtils {

    /**
     * 创建控制指令
     * @param sourceId
     * @param destId
     * @param parmMaps
     * @return
     */
    public static MqIotMessageDTO getControllor(String sourceId, String destId, Map<String, String> parmMaps) {
        MqIotDeviceControllerTO mqIotDeviceControllerTO = new MqIotDeviceControllerTO();
        mqIotDeviceControllerTO.setParmMaps(parmMaps);
        return  new MqIotMessageDTO(CmdListEnum.control, sourceId, destId, mqIotDeviceControllerTO);
    }
    public static MqIotMessageDTO getNotifyRefresh(String sourceId, String destId, Object parmMaps) {
        return  new MqIotMessageDTO(CmdListEnum.notifyRefresh, sourceId, destId, parmMaps);
    }
    public static MqIotMessageDTO getAddObject(String sourceId, String destId, Object parmMaps) {
        return  new MqIotMessageDTO(CmdListEnum.addObject, sourceId, destId, parmMaps);
    }

    public static MqIotMessageDTO getFactoryReset(String sourceId, String destId) {
        return  new MqIotMessageDTO(CmdListEnum.factoryReset, sourceId, destId, null);
    }

    public static MqIotAddObjectSubsDTO getAddObjectSubsCmd(String orAnd, List<String> eventCodeS, Map<String,String> trigger){
        MqIotAddObjectSubsDTO mqIotAddObjectSubsDTO = new MqIotAddObjectSubsDTO();
        mqIotAddObjectSubsDTO.setCombModel(orAnd);
        mqIotAddObjectSubsDTO.setEventCode(eventCodeS);
        mqIotAddObjectSubsDTO.setUpdateNum(0);
        mqIotAddObjectSubsDTO.setRole(Constants.wisdomRole.WISDOM_SUBS);
        mqIotAddObjectSubsDTO.setTrigger(trigger);
        return mqIotAddObjectSubsDTO;
    }

    public static MqIotAddObjectGenDTO getAddObjectGenCmd(String orAnd, String eventCode,List<MqIotConditionDTO> conditionDTOS){
        MqIotAddObjectGenDTO mqIotAddObjectGenDTO = new MqIotAddObjectGenDTO();
        mqIotAddObjectGenDTO.setUpdateNum(0);
        mqIotAddObjectGenDTO.setCombModel(orAnd);
        mqIotAddObjectGenDTO.setCondition(new ArrayList<>());
        mqIotAddObjectGenDTO.setCondition(conditionDTOS);
        mqIotAddObjectGenDTO.setEventCode(eventCode);
        mqIotAddObjectGenDTO.setRole(Constants.wisdomRole.WISDOM_GEN);
        return mqIotAddObjectGenDTO;
    }

    public static MqIotMessageDTO getNotify(String sourceId, String destId, Object parmMaps) {
        return  new MqIotMessageDTO(CmdListEnum.notify, sourceId, destId, parmMaps);
    }

    public static MqIotMessageDTO getDeletebject(String sourceId, String destId, Object parmMaps) {
        return  new MqIotMessageDTO(CmdListEnum.deleteObject, sourceId, destId, parmMaps);
    }

    public static MqIotAddTimingBaseDTO getAddTimingBody(String days, Long id, String mode, LinkedHashMap onStr, LinkedHashMap offStr, String role, Integer updateNum){
        MqIotAddTimingBaseDTO mqIotAddTimingBaseDTO = new MqIotAddTimingBaseDTO();

        MqIotTimerTaskDTO mqIotTimerTaskDTO = new MqIotTimerTaskDTO();
        mqIotTimerTaskDTO.setId(id);
        mqIotTimerTaskDTO.setMode(mode);
        mqIotTimerTaskDTO.setDays(days);
        mqIotTimerTaskDTO.setStart(onStr);
        mqIotTimerTaskDTO.setUpdateNum(updateNum);
        if(offStr != null){
            mqIotTimerTaskDTO.setFinish(offStr);
        }
        mqIotAddTimingBaseDTO.setRole(role);
        mqIotAddTimingBaseDTO.setTimertask(mqIotTimerTaskDTO);
        return mqIotAddTimingBaseDTO;
    }

    public static MqIotDeleteTimingBaseDTO getDeleteTimingBody(Long id, String role){
        MqIotDeleteTimingBaseDTO mqIotDeleteTimingBaseDTO = new MqIotDeleteTimingBaseDTO();
        MqIotTimerTaskDTO mqIotTimerTaskDTO = new MqIotTimerTaskDTO();
        mqIotTimerTaskDTO.setId(id);
        mqIotDeleteTimingBaseDTO.setRole(role);
        mqIotDeleteTimingBaseDTO.setTimertask(mqIotTimerTaskDTO);
        return mqIotDeleteTimingBaseDTO;
    }

    public static Map<String, String> getOnOffCmd(String value){
        Map<String, String> parmMap = new HashMap<>();
        parmMap.put("onoff", value);
        return parmMap;
    }

    public static Map<String, String> getNotifyRefreshCmd (String value){
        Map<String, String> parmMap = new HashMap<>();
        parmMap.put("type", "refresh");
        parmMap.put("value", value);
        return parmMap;
    }

    public static Map<String, String> getSetWindSpeedCmd(String windSpeed){
        Map<String, String> parmMap = new HashMap<>();
        if(WIND_SPEED.containsKey(windSpeed)){
            parmMap.put("wind_speed", WIND_SPEED.get(windSpeed));
        }else{
            parmMap.put("wind_speed", windSpeed);
        }
        return parmMap;
    }

    public static Map<String, String> getSetModeCmd(String mode){
        Map<String, String> parmMap = new HashMap<>();
        parmMap.put("model", mode);
        return parmMap;
    }

    public static Map<String, String> getDeleteWisdomCmd(Long  wid, String role){
        Map<String, String> parmMap =  new HashMap<>();
        parmMap.put("eventCode", "*/*," + wid);
        parmMap.put("role", role);
        return parmMap;
    }

    public static Map<String, String> getDeleteWisdomCondition(Long  wid){
        Map<String, String> parmMap =  new HashMap<>();
        parmMap.put("eventCode", "*/*," + wid);
        parmMap.put("role", Constants.wisdomRole.WISDOM_GEN);
        return parmMap;
    }
    public static Map<String, String> getDeleteWisdomImplement(Long  wid){
        Map<String, String> parmMap =  new HashMap<>();
        parmMap.put("eventCode", "*/*," + wid);
        parmMap.put("role", Constants.wisdomRole.WISDOM_SUBS);
        return parmMap;
    }

//    public static Map<String, String> getOpenSwingCmd(String openSwing){
//        Map<String, String> parmMap = new HashMap<>();
//        if(WIND_SPEED.containsKey(windSpeed)){
//            parmMap.put("swing", WIND_SPEED.get(windSpeed));
//        }else{
//            parmMap.put("swing", windSpeed);
//        }
//        return parmMap;
//    }

    public static Map<String, String> getSetColorCmd(String color){
        String [] rgbs = CAIHONGCOLOR.get(color).split(",");
        Map<String, String> result = new HashMap<>();
        result.put("R", rgbs[0]);
        result.put("G", rgbs[1]);
        result.put("B", rgbs[2]);
        return result;
    }



    public static Map<String, String> getSetBrightnessCmd(String w){
        Map<String, String> result = new HashMap<>();
        result.put("W", w);
        return result;
    }

    public static Map<String, String> CAIHONGCOLOR;


    public static Map<String, String> WIND_SPEED;
    static {
        CAIHONGCOLOR = new HashMap<>();
        CAIHONGCOLOR.put("Red", "255,0,0");
        CAIHONGCOLOR.put("Yellow", "255,255,0");
        CAIHONGCOLOR.put("Blue", "0,0,255");
        CAIHONGCOLOR.put("Green", "0,255,0");
        CAIHONGCOLOR.put("White", "255,255,255");
        CAIHONGCOLOR.put("Cyan", "0,255,255");
        CAIHONGCOLOR.put("Purple", "255,0,255");
        CAIHONGCOLOR.put("Orange", "255,140,0");

        WIND_SPEED = new HashMap<>();
        WIND_SPEED.put("low","1");
        WIND_SPEED.put("medium","3");
        WIND_SPEED.put("high","6");
        WIND_SPEED.put("六档","6");
        WIND_SPEED.put("五档","5");
        WIND_SPEED.put("四档","4");

    }

}
