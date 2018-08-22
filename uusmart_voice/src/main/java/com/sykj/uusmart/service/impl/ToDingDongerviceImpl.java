package com.sykj.uusmart.service.impl;


import com.sykj.uusmart.Constants;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.dingdong.*;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotMessageUtils;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.pojo.NexusUserDevice;
import com.sykj.uusmart.pojo.UserHomeInfo;
import com.sykj.uusmart.pojo.UserInfo;
import com.sykj.uusmart.repository.*;
import com.sykj.uusmart.service.ToDingDongService;
import com.sykj.uusmart.utils.BoyerMoore;
import com.sykj.uusmart.utils.DingDongAesUtil;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class ToDingDongerviceImpl implements ToDingDongService {


    private Logger log = LoggerFactory.getLogger(ToDingDongerviceImpl.class);

    @Autowired
    UserHomeInfoRepository userHomeInfoRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    MqIotUtils mqIotUtils;

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;

    private final String DINGDONG_KEY = "cf504d6a-f32a-4ec2-992c-645692286b8fcf504d6a-f32a-4ec2-992c-645692286b8fcf504d6a-f32a-4ec2-992c-645692286b8fcf504d6a-f32a-4ec2-9";


    @Override
    public ResponseDTO badingDingDong(ReqDDBaDingUserDTO reqDDBaDingUserDTO) {
        UserInfo userInfo = userInfoRepository.findUserInfoByEmailAndPassword(reqDDBaDingUserDTO.getAccount(), MD5Utils.toMD5(reqDDBaDingUserDTO.getPassword()));
        String stateStr = DingDongAesUtil.decrypt(DINGDONG_KEY, reqDDBaDingUserDTO.getState());
        DingDongStateDTO dingDongStateDTO = GsonUtils.toObj(stateStr, DingDongStateDTO.class);
        String redisKey = serviceConfig.getDINGDONG_TOKEN() + Constants.specialSymbol.COLOM + dingDongStateDTO.getUserid();
        stringRedisTemplate.opsForValue().set(redisKey, String.valueOf(userInfo.getUserId()));
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    public RespDingDongCmd dingDongPushCmd(DingDongPushCmdDTO dingDongPushCmdDTO) {
        String redisKey = serviceConfig.getDINGDONG_TOKEN() + Constants.specialSymbol.COLOM + dingDongPushCmdDTO.getUser().getUser_id();
        String userIdStr = stringRedisTemplate.opsForValue().get(redisKey);
        CustomRunTimeException.checkNull(userIdStr, " UserID ");

        Long uid = Long.parseLong(userIdStr);
        UserHomeInfo homeInfo = userHomeInfoRepository.byUserIdQueryUseOne(uid);
        List<NexusUserDevice> nexusUserDeviceList = nexusUserDeviceRepository.findByUserIdAndHomeID(uid, homeInfo.getHid());
        NexusUserDevice nexusUserDevice = BoyerMoore.matchingDingDongNexusUserDevice(nexusUserDeviceList, dingDongPushCmdDTO.getSlots().getDeviceName());
        if (nexusUserDevice == null) {
            CustomRunTimeException.checkNull(userIdStr, " Nexus ");
        }
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(nexusUserDevice.getDeviceId());
        CustomRunTimeException.checkDeviceIsOffLine(deviceInfo, true);


        Map contBody = MqIotMessageUtils.getOnOffCmd(this.getOnOff.get(dingDongPushCmdDTO.getSlots().getCmd()), null);
        MqIotMessageDTO mqIotMessageDTO = MqIotMessageUtils.getControllor(serviceConfig.getMQTT_CLIENT_NAME(), MqIotUtils.getRole(Constants.shortNumber.TWO) + deviceInfo.getDeviceId(), contBody);
        mqIotUtils.mqIotPushMsg(deviceInfo, mqIotMessageDTO);

        return new RespDingDongCmd(dingDongPushCmdDTO.getVersionid(), System.currentTimeMillis(), new RespDingDongDirective("1", "好的," + deviceInfo.getDeviceName() + "已经" + dingDongPushCmdDTO.getSlots().getCmd()), true, dingDongPushCmdDTO.getSequence());
    }

    private static Map<String, String> getOnOff;

    static {
        getOnOff = new HashMap<>();
        getOnOff.put("打开", "1");
        getOnOff.put("关闭", "0");
    }
}
