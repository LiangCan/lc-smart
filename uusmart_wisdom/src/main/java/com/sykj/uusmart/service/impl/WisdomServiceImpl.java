package com.sykj.uusmart.service.impl;


//import com.codingapi.tx.annotation.TxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.NameAndIdDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.req.UserAddWisdomDTO;
import com.sykj.uusmart.http.req.input.AddWisdomConditionDTO;
import com.sykj.uusmart.http.req.input.AddWisdomImplementDTO;
import com.sykj.uusmart.http.resp.RespQueryWisdomListDTO;
import com.sykj.uusmart.mqtt.*;
import com.sykj.uusmart.mqtt.cmd.CmdListEnum;
import com.sykj.uusmart.mqtt.cmd.MqIotAddObjectGenDTO;
import com.sykj.uusmart.mqtt.cmd.MqIotAddObjectSubsDTO;
import com.sykj.uusmart.mqtt.cmd.input.MqIotConditionDTO;
import com.sykj.uusmart.pojo.*;
import com.sykj.uusmart.repository.*;
import com.sykj.uusmart.service.UserInfoService;
import com.sykj.uusmart.service.WisdomService;
import com.sykj.uusmart.utils.ExecutorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
//@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class WisdomServiceImpl implements WisdomService {


    private Logger log = LoggerFactory.getLogger(WisdomServiceImpl.class);

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    RoomInfoRepository roomInfoRepository;

    @Autowired
    UserHomeInfoRepository userHomeInfoRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    WisdomRepository wisdomRepository;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    WisdomConditionRepository wisdomConditionRepository;

    @Autowired
    WisdomImplementRepository wisdomImplementRepository;

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;

    @Autowired
    MqIotUtils mqIotUtils;

    @Autowired
    TestInfoRepository testInfoRepository;

    @Override
//    @TxTransaction
    @Transactional
    public ResponseDTO test() {
        TestInfo testInfo = new TestInfo();
        testInfo.setTestName("123");
        testInfoRepository.save(testInfo);
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    public ResponseDTO userAddWisdom(UserAddWisdomDTO userAddWisdomDTO) {
        Long uid = userInfoService.getUserId(true);

        //递归校验是否
        checkWisdomFollowTheBad(userAddWisdomDTO.getWisdomImplementDTOList(), userAddWisdomDTO.getWisdomConditionDTOList(), userAddWisdomDTO.getAndOrRun());
        //鉴权
        CustomRunTimeException.checkNull(userHomeInfoRepository.byUserIdAndHidQueryOne(uid, userAddWisdomDTO.getId()), "home");
        Wisdom wisdom = new Wisdom();
        wisdom.setUserId(uid);
        wisdom.setHid(userAddWisdomDTO.getId());
        wisdom.setCreateTime(System.currentTimeMillis());
        wisdom.setWisdomName(userAddWisdomDTO.getName());
        wisdom.setWisdomStatus(Constants.shortNumber.ONE);
        wisdom.setWisdomType(userAddWisdomDTO.getType());
        wisdom.setWisdomIcon(userAddWisdomDTO.getWisdomIcon());
        wisdom.setAndOr(userAddWisdomDTO.getAndOrRun());
        wisdomRepository.save(wisdom);

        //处理条件
        List<WisdomCondition> conditionIds = new ArrayList();
        for (AddWisdomConditionDTO wisdomConditionDTO : userAddWisdomDTO.getWisdomConditionDTOList()) {
            WisdomCondition wisdomCondition = new WisdomCondition();
            wisdomCondition.setWid(wisdom.getWid());
            wisdomCondition.setConditionStatus(Constants.shortNumber.NINE);
            wisdomCondition.setAppointment(wisdomConditionDTO.getAppointment());
            wisdomCondition.setConditionType(wisdomConditionDTO.getConditionType());
            wisdomCondition.setConditionName(wisdomConditionDTO.getConditionName());
            wisdomCondition.setCreateTime(System.currentTimeMillis());
            wisdomCondition.setConditionValue(wisdomConditionDTO.getConditionValue());
            wisdomCondition.setUserId(uid);
            wisdomCondition.setId(wisdomConditionDTO.getId());
            wisdomConditionRepository.save(wisdomCondition);
            conditionIds.add(wisdomCondition);
        }

        //处理执行
        List<WisdomImplement> implementIds = new ArrayList();
        for (AddWisdomImplementDTO wisdomImplementDTO : userAddWisdomDTO.getWisdomImplementDTOList()) {
            WisdomImplement wisdomImplement = new WisdomImplement();
            wisdomImplement.setWid(wisdom.getWid());
            wisdomImplement.setImplementStatus(Constants.shortNumber.NINE);
            wisdomImplement.setImplementType(wisdomImplementDTO.getImplementType());
            wisdomImplement.setImplementName(wisdomImplementDTO.getImplementName());
            wisdomImplement.setCreateTime(System.currentTimeMillis());
            wisdomImplement.setImplementValue(wisdomImplementDTO.getImplementValue());
            wisdomImplement.setUserId(uid);
            wisdomImplement.setId(wisdomImplementDTO.getId());
            wisdomImplementRepository.save(wisdomImplement);
            implementIds.add(wisdomImplement);
        }

        int result = pushAddObjectDTO(wisdom, conditionIds, implementIds);
        //判断发送是否成功
        if (result != Constants.mainStatus.SUCCESS) {
            throw new CustomRunTimeException(Constants.resultCode.PUSH_MSG_ERROR, Constants.systemError.PUSH_MSG_ERROR);
        }
        return new ResponseDTO(new RespQueryWisdomListDTO(wisdom, conditionIds, implementIds));
    }

    /**
     * 执行名称，条件名称
     */
    static Map<String, String> map;

    static {
        map = new HashMap<>();
        map.put("onoff", "status");
    }

    public String getConditionName(String implementName) {
        return map.containsKey(implementName) ? map.get(implementName) : implementName;
    }

    /**
     * 校验智能是否会造成闭环
     * @param addWisdomImplementDTOS
     * @param addWisdomConditionDTOS
     * @param andOrRun
     */
    public void checkWisdomFollowTheBad(List<AddWisdomImplementDTO> addWisdomImplementDTOS, List<AddWisdomConditionDTO> addWisdomConditionDTOS, Short andOrRun) {
        System.out.println(" -------------- >>>");
        List<AddWisdomConditionDTO> wisdomConditionlist = new ArrayList<>(addWisdomConditionDTOS);
        List<AddWisdomImplementDTO> newAddWisdomIm = new ArrayList<>();
        for (AddWisdomImplementDTO addWisdomImplementDTO : addWisdomImplementDTOS) {
            String conditionName = getConditionName(addWisdomImplementDTO.getImplementName());

            // 把 执行 当 条件 查找其他情景的 执行！
            List<Object[]> list = wisdomImplementRepository.findImplementByCondition(addWisdomImplementDTO.getId(), conditionName, addWisdomImplementDTO.getImplementType(), addWisdomImplementDTO.getImplementValue());
            List<WisdomImplement> wisdomImplementList = new ArrayList();
            for (Object[] objects : list) {
                WisdomImplement wisdomImplement = new WisdomImplement((BigInteger) objects[0], (Short) objects[1], (String) objects[2], (String) objects[3]);
                wisdomImplementList.add(wisdomImplement);
            }

            //遍历查找出来的执行 是否和当前条件有重复的
            for (WisdomImplement wisdomImplement : wisdomImplementList) {
                int i = 0;
                //遍历执行比较
                for (AddWisdomConditionDTO addWisdomConditionDTO : wisdomConditionlist) {

                    if (addWisdomConditionDTO.getConditionType() == wisdomImplement.getImplementType() &&
                            addWisdomConditionDTO.getConditionName().equals(getConditionName(wisdomImplement.getImplementName())) &&
                            addWisdomConditionDTO.getConditionValue().equals(wisdomImplement.getImplementValue())) {
                        //如果添加的是 条件其中之一满足就会运行智能 那么就不行了
                        if (andOrRun == Constants.shortNumber.TWO) {
                            throw new CustomRunTimeException(Constants.resultCode.API_CREATE_WISDOM_ERROR, Constants.systemError.API_CREATE_WISDOM_ERROR);
                        }
                        //如果是 条件全部满足后就会运行的话  那
                        wisdomConditionlist.remove(addWisdomConditionDTO);
                        if (wisdomConditionlist.size() == Constants.shortNumber.ZERO) {
                            throw new CustomRunTimeException(Constants.resultCode.API_CREATE_WISDOM_ERROR, Constants.systemError.API_CREATE_WISDOM_ERROR);
                        }

                    }
                }
                newAddWisdomIm.add(new AddWisdomImplementDTO(wisdomImplement.getId() ,wisdomImplement.getImplementType(), wisdomImplement.getImplementName(), wisdomImplement.getImplementValue()));
            }
        }
        if (newAddWisdomIm.size() > 0) {
            checkWisdomFollowTheBad(newAddWisdomIm, wisdomConditionlist, andOrRun);
        }
        return;
    }


    @Override
    public ResponseDTO userGetWisdomList() {
        Long uid = userInfoService.getUserId(true);
        List<Wisdom> wisdoms = wisdomRepository.byUserIdQueryList(uid);
        List<RespQueryWisdomListDTO> respQueryWisdomListDTOS = new ArrayList<>();
        for (Wisdom wisdom : wisdoms) {
            List<WisdomCondition> wisdomConditions = wisdomConditionRepository.findAllByWid(wisdom.getWid());
            List<WisdomImplement> wisdomImplements = wisdomImplementRepository.findAllByWid(wisdom.getWid());
            respQueryWisdomListDTOS.add(new RespQueryWisdomListDTO(wisdom, wisdomConditions, wisdomImplements));
        }

        return new ResponseDTO(respQueryWisdomListDTOS);
    }

    @Override
    public ResponseDTO userDeleteWisdom(IdDTO idDTO) {
        Long uid = userInfoService.getUserId(true);

        Wisdom wisdom = wisdomRepository.byUserIdAndWidQuery(uid, idDTO.getId());

        CustomRunTimeException.checkNull(wisdom, "wisdom");
        List<WisdomCondition> conditions = wisdomConditionRepository.findIdsAllByWid(wisdom.getWid());
        for (WisdomCondition condition : conditions) {
            wisdomConditionRepository.deleteByIdAndWid(condition.getId(), wisdom.getWid());
            if (condition.getConditionType() == Constants.shortNumber.TWO) {
                ExecutorUtils.cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> deleteObjectMsg = MqIotMessageUtils.getDeleteWisdomCondition(wisdom.getWid());
                        MqIotMessage mqIotMessage = new MqIotMessage(CmdListEnum.deleteObject, serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + condition.getId(), deleteObjectMsg);
                        mqIotMessage.setCache(true);
                        mqIotUtils.mqIotPushMsgAndGetResult(mqIotMessage);
                    }
                });
            }
        }
        List<WisdomImplement> implementList = wisdomImplementRepository.findIdsAllByWid(wisdom.getWid());
        for (WisdomImplement wisdomImplement : implementList) {
            wisdomImplementRepository.deleteByIdAndWid(wisdomImplement.getId(), wisdom.getWid());
            if (wisdomImplement.getImplementType() == Constants.shortNumber.TWO) {
                ExecutorUtils.cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> deleteObjectMsg = MqIotMessageUtils.getDeleteWisdomImplement(wisdom.getWid());
                        MqIotMessage mqIotMessage = new MqIotMessage(CmdListEnum.deleteObject, serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(wisdomImplement.getImplementType()) + wisdomImplement.getId(), deleteObjectMsg);
                        mqIotMessage.setCache(true);
                        mqIotUtils.mqIotPushMsgAndGetResult(mqIotMessage);
                    }
                });
            }
        }
        wisdomRepository.delete(wisdom.getWid());
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    @TxTransaction
    public ResponseDTO userDeleteDeviceWisdom(IdDTO idDTO) {
        Long uid = userInfoService.getUserId(true);
//        CustomRunTimeException.checkDeviceJurisdiction(nexusUserDeviceRepository.findByUserIdAndDeviceId(uid, idDTO.getId()), true);
        List<Long> wids = wisdomConditionRepository.findWidByDid(idDTO.getId());
        for (Long wid : wids) {
            Map<String, String> deleteObjectMsg = MqIotMessageUtils.getDeleteWisdomCondition(wid);
            MqIotMessage mqIotMessage = new MqIotMessage(CmdListEnum.deleteObject, serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + idDTO.getId(), deleteObjectMsg);
            mqIotMessage.setCache(true);
            ExecutorUtils.cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mqIotUtils.mqIotPushMsgAndGetResult(mqIotMessage);
                }
            });
            wisdomRepository.updateStatus(Constants.shortNumber.NINE, wid);
        }
        wisdomConditionRepository.deleteByDid(idDTO.getId());

        wids = wisdomImplementRepository.findWidByDid(idDTO.getId());
        for (Long wid : wids) {
            Map<String, String> deleteObjectMsg = MqIotMessageUtils.getDeleteWisdomImplement(wid);
            MqIotMessage mqIotMessage = new MqIotMessage(CmdListEnum.deleteObject, serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + idDTO.getId(), deleteObjectMsg);
            mqIotMessage.setCache(true);
            ExecutorUtils.cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mqIotUtils.mqIotPushMsgAndGetResult(mqIotMessage);
                }
            });
            wisdomRepository.updateStatus(Constants.shortNumber.NINE, wid);
        }
        wisdomImplementRepository.deleteByDid(idDTO.getId());
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    public void notifyWisdom(String msg, Long wid) {
        Wisdom wisdom = wisdomRepository.findOne(wid);
        if(wisdom != null){
            List<WisdomImplement> implementList = wisdomImplementRepository.findIdsAllByWid(wisdom.getWid());
            for (WisdomImplement wisdomImplement : implementList) {
                MQTTUtils.push(MqIotUtils.getRole(wisdomImplement.getImplementType()) + wisdomImplement.getId(), msg);
            }
        }
    }

    public static Map<Short, String> orAndS;

    static {
        orAndS = new HashMap<>();
        orAndS.put(Constants.shortNumber.ONE, "and");
        orAndS.put(Constants.shortNumber.TWO, "or");
    }

    /**
     * 创建和推送情景指令
     *
     * @param wisdom
     * @param conditions
     * @param impiements
     */

    public int pushAddObjectDTO(Wisdom wisdom, List<WisdomCondition> conditions, List<WisdomImplement> impiements) {
        List<MqIotMessageDTO> pushMsgs = new ArrayList<>();
        List<MqIotMessageDTO> callbackMsgs = new ArrayList<>();
        List<String> eventCodeS = new ArrayList<>();
        Map<Long, MqIotAddObjectGenDTO> pushCondition = new HashMap<>();
        //处理条件端
        for (WisdomCondition wisdomCondition : conditions) {
            MqIotConditionDTO mqIotConditionDTO = new MqIotConditionDTO();
            mqIotConditionDTO.setName(wisdomCondition.getConditionName());
            mqIotConditionDTO.setCompModel(wisdomCondition.getAppointment());
            mqIotConditionDTO.setValue(wisdomCondition.getConditionValue());
            if (pushCondition.containsKey(wisdomCondition.getId())) {
                pushCondition.get(wisdomCondition.getId()).getCondition().add(mqIotConditionDTO);
            } else {
                String eventCode = getEventCode(wisdomCondition.getConditionType(), wisdomCondition.getId(), wisdom.getWid());
                eventCodeS.add(eventCode);
                if (wisdomCondition.getConditionType() == Constants.shortNumber.ONE) {
                    break;
                }
                MqIotAddObjectGenDTO mqIotAddObjectGenDTO = new MqIotAddObjectGenDTO();
                mqIotAddObjectGenDTO.setCombModel(orAndS.get(wisdom.getAndOr()));
                mqIotAddObjectGenDTO.setCondition(new ArrayList<>());
                mqIotAddObjectGenDTO.getCondition().add(mqIotConditionDTO);
                mqIotAddObjectGenDTO.setEventCode(eventCode);
                mqIotAddObjectGenDTO.setRole(Constants.wisdomRole.WISDOM_GEN);
                pushCondition.put(wisdomCondition.getId(), mqIotAddObjectGenDTO);
            }
        }


        //生成条件指令
        for (Long id : pushCondition.keySet()) {
            //创建发送数据
            MqIotMessageDTO mqIotMessageDTO = new MqIotMessageDTO(CmdListEnum.addObject, serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + id, pushCondition.get(id), wisdom.getWid());
            pushMsgs.add(mqIotMessageDTO);
            //创建回滚数据
            Map<String, String> callback = new HashMap<>();
            callback.put("eventCode", "*/*," + wisdom.getWid());
            callback.put("role", Constants.wisdomRole.WISDOM_GEN);
            MqIotMessageDTO mqIotMessageDTO1 = new MqIotMessageDTO(CmdListEnum.deleteObject, serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + id, callback, wisdom.getWid());
            callbackMsgs.add(mqIotMessageDTO1);
        }

        //处理触发集合
        Map<Long, MqIotAddObjectSubsDTO> pushTrigger = new HashMap<>();
        for (WisdomImplement wisdomImplement : impiements) {
            if (pushTrigger.containsKey(wisdomImplement.getId())) {
                pushTrigger.get(wisdomImplement.getId()).getTrigger().put(wisdomImplement.getImplementName(), wisdomImplement.getImplementValue());
            } else {
                MqIotAddObjectSubsDTO mqIotAddObjectSubsDTO = new MqIotAddObjectSubsDTO();
                mqIotAddObjectSubsDTO.setCombModel(orAndS.get(wisdom.getAndOr()));
                mqIotAddObjectSubsDTO.setEventCode(eventCodeS);
                mqIotAddObjectSubsDTO.setRole(Constants.wisdomRole.WISDOM_SUBS);
                Map<String, String> trigger = new HashMap<>();
                trigger.put(wisdomImplement.getImplementName(), wisdomImplement.getImplementValue());
                mqIotAddObjectSubsDTO.setTrigger(trigger);
                pushTrigger.put(wisdomImplement.getId(), mqIotAddObjectSubsDTO);
            }
        }
        //生成触发指令
        for (Long id : pushTrigger.keySet()) {

            //创建发送数据
            MqIotMessageDTO mqIotMessageDTO = new MqIotMessageDTO(CmdListEnum.addObject, serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + id, pushTrigger.get(id), wisdom.getWid());
            pushMsgs.add(mqIotMessageDTO);

            //创建回滚数据
            Map<String, String> callback = new HashMap<>();
            callback.put("eventCode", "*/*," + wisdom.getWid());
            callback.put("role", Constants.wisdomRole.WISDOM_SUBS);
            MqIotMessageDTO mqIotMessageDTO1 = new MqIotMessageDTO(CmdListEnum.deleteObject, serviceConfig.getMQTT_CLIENT_NAME(), Constants.role.DEVICE + Constants.specialSymbol.URL_SEPARATE + id, callback, wisdom.getWid());
            callbackMsgs.add(mqIotMessageDTO1);
        }


        MqIotThingMessage mqIotThingMessage = new MqIotThingMessage(pushMsgs, callbackMsgs);
        mqIotUtils.mqIotPushThingMsg(mqIotThingMessage);

        return mqIotThingMessage.getState();

    }

    /**
     * 获取设备的eventCode
     *
     * @param id
     * @param wid
     * @return
     */
    private String getEventCode(Short condType, Long id, Long wid) {
        String role = Constants.role.DEVICE;
        if (condType == 1) {
            role = Constants.role.APP;
//            CustomRunTimeException.checkNull(userInfoRepository.findOne(id), "user");
        } else {
            CustomRunTimeException.checkDeviceIsOffLine(deviceInfoRepository.findOne(id), true);
        }

        StringBuilder eventCode = new StringBuilder();
        eventCode.append(role);
        eventCode.append(Constants.specialSymbol.URL_SEPARATE);
        eventCode.append(id);
        eventCode.append(Constants.specialSymbol.COMMA);
        eventCode.append(wid);
        return eventCode.toString();
    }

    /**
     //     * 获取用户的eventCode
     //     *
     //     * @param uid
     //     * @param wid
     //     * @return
     //     */
//    private String getAppEventCode(Long uid, Long wid) {
//        StringBuilder eventCode = new StringBuilder();
//        eventCode.append(Constants.role.APP);
//        eventCode.append(Constants.specialSymbol.URL_SEPARATE);
//        eventCode.append(uid);
//        eventCode.append(Constants.specialSymbol.COMMA);
//        eventCode.append(wid);
//        return eventCode.toString();
//    }
}