package com.sykj.uusmart.service.impl;


import com.codingapi.tx.annotation.TxTransaction;
import com.sykj.uusmart.conf.ServiceConfig;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.NameAndIdDTO;
import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.http.IdDTO;
import com.sykj.uusmart.http.req.UserAddDeviceDTO;
import com.sykj.uusmart.http.req.UserUpdateDeviceDTO;
import com.sykj.uusmart.http.req.input.AddDeivceDTO;
import com.sykj.uusmart.http.resp.RespDeviceListDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.hystric.Demo2Client;
import com.sykj.uusmart.hystric.TimingServiceAPI;
import com.sykj.uusmart.hystric.WisdomServiceAPI;
import com.sykj.uusmart.mqtt.MqIotMessageDTO;
import com.sykj.uusmart.mqtt.MqIotMessageUtils;
import com.sykj.uusmart.mqtt.MqIotUtils;
import com.sykj.uusmart.pojo.*;
import com.sykj.uusmart.repository.*;
import com.sykj.uusmart.service.DeviceInfoService;
import com.sykj.uusmart.service.UserInfoService;
import com.sykj.uusmart.utils.ExecutorUtils;
import com.sykj.uusmart.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Liang on 2016/12/23.
 */
@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class DeviceaInfoServiceImpl implements DeviceInfoService {


    private  Logger log = LoggerFactory.getLogger(DeviceaInfoServiceImpl.class);

    @Autowired
    RoomInfoRepository roomInfoRepository;

    @Autowired
    UserHomeInfoRepository userHomeInfoRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    NexusUserDeviceRepository nexusUserDeviceRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;


    @Autowired
    MqIotUtils mqIotUtils;

    @Autowired
    TimingServiceAPI timingServiceAPI;

    @Autowired
    WisdomServiceAPI wisdomServiceAPI;

    @Override
    @TxTransaction(isStart = true)
    @Transactional
    public ResponseDTO userRisterDevice(UserAddDeviceDTO userAddDeviceDTO , ReqBaseDTO<IdDTO> timingReq) {
        Long uid =userInfoService.getUserId(true);

        Long hid =   userAddDeviceDTO.getId();
        //家庭鉴权
        CustomRunTimeException.checkNull(userHomeInfoRepository.byUserIdAndHidQueryOne(uid, hid),"home");

        //房间鉴权
        CustomRunTimeException.checkNull(roomInfoRepository.byUserIdAndRoomId(uid, userAddDeviceDTO.getRoomId()),"room");
         Map <Long , MqIotMessageDTO > notifyMap = new HashMap< Long,  MqIotMessageDTO>();
        Map<String,Long> dids = new HashMap<>();
        for(AddDeivceDTO addDeivceDTO : userAddDeviceDTO.getAddDeivceDTOList()){
            DeviceInfo deviceInfo =  deviceInfoRepository.findDeviceInfoByAddress(addDeivceDTO.getDeviceAddress());

            //判断设备是否存在
            if (deviceInfo != null) {
                NexusUserDevice nexusUserDevice = nexusUserDeviceRepository.findDeviceIdAndRoleQueryUserId(deviceInfo.getDeviceId(), (short) 1);
                //判断设备用户绑定关系是否存在
                if (nexusUserDevice != null) {
                    //判断是被自己绑定
//                    使用 “==” 造成的bug， == 会去判断 对象的内存位置。改用equals  lgf ，2018年7月20日14:00:27
//                    if (nexusUserDevice.getUserId()  == uid && nexusUserDevice.getHid() == hid) {
                    if (nexusUserDevice.getUserId().equals( uid )  && nexusUserDevice.getHid().equals( hid )) {
                        dids.put(addDeivceDTO.getDeviceAddress(), deviceInfo.getDeviceId());
                        continue;
                    }

                    Map<String , String> natityRefresh = MqIotMessageUtils.getNotifyRefreshCmd("dervice");
                    MqIotMessageDTO mqIotMessage = MqIotMessageUtils.getNotify( serviceConfig.getMQTT_CLIENT_NAME(),MqIotUtils.getRole(Constants.shortNumber.ONE) + nexusUserDevice.getUserId(), natityRefresh );
//                            mqIotUtils.mqIotPushMsg( mqIotMessage );
                    notifyMap.put( nexusUserDevice.getUserId() , mqIotMessage);
//                    删除设备的定时和设备的智能；
                    deleteWisdomAndTime( deviceInfo.getDeviceId(), timingReq );

                    //删除绑定关系
                    nexusUserDeviceRepository.deleteByDeviceId(deviceInfo.getDeviceId());

//                    NoticeUtils.pushDataRefresh(nexusUserDevice.getUserId(), 2);
                }
            } else {
                deviceInfo = new DeviceInfo();
            }
            deviceInfo.setCreateTime(System.currentTimeMillis());
            deviceInfo.setDeviceAddress(addDeivceDTO.getDeviceAddress());
            deviceInfo.setDeviceName(addDeivceDTO.getName());
            deviceInfo.setVersionInfo(addDeivceDTO.getDeviceVersion());
            deviceInfo.setUserId(uid);
            deviceInfo.setDeviceStatus( Constants.shortNumber.ZERO);
            deviceInfo.setDeviceWifiSSID(userAddDeviceDTO.getDeviceWifiSSID());
            deviceInfo.setProductId(addDeivceDTO.getPid());
            deviceInfo.setClassification(userAddDeviceDTO.getClassification());
            deviceInfo.setHid(hid);
            deviceInfoRepository.save(deviceInfo);

            String deviceIcon = productInfoRepository.queryProductIconById(addDeivceDTO.getPid());

            NexusUserDevice nexusUserDevic = new NexusUserDevice();
            nexusUserDevic.setDeviceId(deviceInfo.getDeviceId());
            nexusUserDevic.setUserId(uid);
            nexusUserDevic.setCreateTime(System.currentTimeMillis());
            nexusUserDevic.setNudStatus(Constants.shortNumber.ONE);
            nexusUserDevic.setHid(hid);
            nexusUserDevic.setDeviceIcon(deviceIcon);
            nexusUserDevic.setRoomId(userAddDeviceDTO.getRoomId());
            nexusUserDevic.setRole(Constants.shortNumber.ONE);
            nexusUserDevic.setRemarks(deviceInfo.getDeviceName());
            nexusUserDeviceRepository.save(nexusUserDevic);
            dids.put(addDeivceDTO.getDeviceAddress(), deviceInfo.getDeviceId());


        }
        ExecutorUtils.cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<Long, MqIotMessageDTO>> iterator = notifyMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Long, MqIotMessageDTO> entry = iterator.next();
                    mqIotUtils.mqIotPushMsg(  entry.getValue()  );
                }
            }
        });

        return new ResponseDTO(dids);
    }

    @Override
    public ResponseDTO userUpdateDevice(UserUpdateDeviceDTO userUpdateDeviceDTO) {
        Long uid =userInfoService.getUserId(true);

        NexusUserDevice nexusUserDevice = nexusUserDeviceRepository.findByUserIdAndDeviceId(uid, userUpdateDeviceDTO.getId());
        CustomRunTimeException.checkNull(nexusUserDevice," Nexus ");

        DeviceInfo deviceInfo = deviceInfoRepository.findOne(userUpdateDeviceDTO.getId());
        CustomRunTimeException.checkNull(deviceInfo," Device ");

        nexusUserDeviceRepository.updateRemarksAndIcon(userUpdateDeviceDTO.getRemarkes(), userUpdateDeviceDTO.getDeviceIcon(), nexusUserDevice.getNudId());

        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Override
    public ResponseDTO userGetDeviceList(IdDTO idDTO) {
        Long uid =userInfoService.getUserId(true);

        //家庭鉴权
        CustomRunTimeException.checkNull(userHomeInfoRepository.byUserIdAndHidQueryOne(uid, idDTO.getId()),"home");

        List<NexusUserDevice> nexusUserDeviceList = nexusUserDeviceRepository.findByUserIdAndHomeID(uid, idDTO.getId());

        List<RespDeviceListDTO> deviceInfoList = new ArrayList<>();
        for(NexusUserDevice nexusUserDevice : nexusUserDeviceList){
            DeviceInfo deviceInfo = deviceInfoRepository.findOne(nexusUserDevice.getDeviceId());
            RespDeviceListDTO respDeviceListDTO = new RespDeviceListDTO();
            respDeviceListDTO.setShareInfoId(nexusUserDevice.getShareInfoId());
            respDeviceListDTO.setBinDingTime(nexusUserDevice.getCreateTime());
            respDeviceListDTO.setDeviceIcon(nexusUserDevice.getDeviceIcon());
            respDeviceListDTO.setRole(nexusUserDevice.getRole());
            respDeviceListDTO.setRemarks(nexusUserDevice.getRemarks());
            respDeviceListDTO.setDeviceInfo(deviceInfo);
            respDeviceListDTO.setRoomId(nexusUserDevice.getRoomId());
            deviceInfoList.add(respDeviceListDTO);
        }

        return new ResponseDTO(deviceInfoList);
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional
    public ResponseDTO userDelete(IdDTO idDTO  , ReqBaseDTO<IdDTO> reqDTO) {
        Long uid =userInfoService.getUserId(true);
        NexusUserDevice nexusUserDevice = nexusUserDeviceRepository.findByUserIdAndDeviceId(uid, idDTO.getId());
        CustomRunTimeException.checkNull(nexusUserDevice,"nexus");

        if(nexusUserDevice.getRole() == Constants.shortNumber.ONE){
            nexusUserDeviceRepository.deleteByDeviceId(idDTO.getId());
        }else{
            nexusUserDeviceRepository.delete(nexusUserDevice.getNudId());
        }

//        deleteWisdomAndTime(nexusUserDevice.getDeviceId() , reqDTO );

        reqDTO.sethG( idDTO );
        //调用删除定时的服务
//        String result = demo2Client.deleteDeviceTiming( reqDTO );
//        String result = timingServiceAPI.byDeviceDeleteAllTiming( reqDTO );
//
//        result =  wisdomServiceAPI.byDeviceDeleteAllWisdom( reqDTO );

//        ResponseDTO responseDTO = GsonUtils.toObj(result, ResponseDTO.class);
//        if(responseDTO.gethRA().equals( String.valueOf(Constants.mainStatus.SUCCESS))){
////            调用删除智能的服务
//            result =  wisdomServiceAPI.byDeviceDeleteAllWisdom( reqDTO );
//            responseDTO = GsonUtils.toObj(result, ResponseDTO.class);
//            if( !responseDTO .gethRA() .equals(  String.valueOf(Constants.mainStatus.SUCCESS) )){
//                throw new CustomRunTimeException(responseDTO.gethRA(), responseDTO.gethRD() );
//            }
//        }else {
//            throw new CustomRunTimeException(responseDTO.gethRA(), responseDTO.gethRD() );
//        }
//       TODO 测试 ！ 2018年7月27日16:56:58 lgf
        if( uid .equals( 1L ) ){
            TestInfo testInfo = new TestInfo();
            testInfo.setTestName("wtf");
            testInfoRepository.save(testInfo  );

            String wisdomResult = wisdomServiceAPI.test( reqDTO );
            log.info( wisdomResult );

            log.error( "抛出异常！");
            String result = timingServiceAPI.test( reqDTO );
            log.info( result);



            String voiceResult = demo2Client.voiceTest( reqDTO );
            log.info( voiceResult );
//
//            String wisdomResult = wisdomServiceAPI.test( reqDTO );
//            log.info( wisdomResult );

            throw new CustomRunTimeException( Constants.resultCode.SYSTEM_ERROR, Constants.systemError.SYSTEM_ERROR );
        }
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    @Autowired
    TestInfoRepository testInfoRepository;


    @Autowired
    Demo2Client demo2Client;

    @Override
    @TxTransaction(isStart = true)
    @Transactional
    public ResponseDTO test(ReqBaseDTO reqBaseDTO) {

        TestInfo testInfo = new TestInfo();
        testInfo.setTestName("wtf");
        testInfoRepository.save(testInfo  );


        log.error( "抛出异常！");
        String result = timingServiceAPI.test( reqBaseDTO );
        log.info( result);
//        String result = demo2Client.voiceTest( reqBaseDTO );
//        log.info( result );

        String wisdomResult = wisdomServiceAPI.test( reqBaseDTO );
        log.info( wisdomResult );

//        CustomRunTimeException.checkNull(null, "Nexus");
        return new ResponseDTO(Constants.mainStatus.REQUEST_SUCCESS);
    }

    public void  deleteWisdomAndTime(Long devId , ReqBaseDTO reqBaseDTO ){
        IdDTO idDTO  = new IdDTO();
        idDTO.setId(devId );
        reqBaseDTO.sethG( idDTO );
        String result = timingServiceAPI.byDeviceDeleteAllTiming( reqBaseDTO );
        ResponseDTO responseDTO = GsonUtils.toObj(result, ResponseDTO.class);
        if(responseDTO.gethRA().equals( String.valueOf(Constants.mainStatus.SUCCESS))){
            result =  wisdomServiceAPI.byDeviceDeleteAllWisdom( reqBaseDTO );
            responseDTO = GsonUtils.toObj(result, ResponseDTO.class);
            if( !responseDTO .gethRA() .equals(  String.valueOf(Constants.mainStatus.SUCCESS) )){
                throw new CustomRunTimeException(responseDTO.gethRA(), responseDTO.gethRD() );
            }
        }else {
            throw new CustomRunTimeException(responseDTO.gethRA(), responseDTO.gethRD() );
        }
    }
}
