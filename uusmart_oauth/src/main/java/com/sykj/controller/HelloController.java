package com.sykj.controller;//package com.sykj.controller;
//
//import com.sykj.dto.req.GoogleReqDTO;
//import com.sykj.dto.req.PushDevuceMsgDTO;
//import com.sykj.dto.resp.GoogleRespDTO;
////import com.sykj.utils.GsonUtils;
////import com.sykj.utils.PoolHttpClientUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * Created by Administrator on 2017/8/25 0025.
// */
//@Controller
//public class HelloController {
//
//    @ResponseBody
//    @RequestMapping(value="/hello",  produces="application/json")
//    public GoogleRespDTO heelo(@RequestBody GoogleReqDTO googleReqDTO){
//        System.out.println("---->>>>>"+ googleReqDTO);
//        PushDevuceMsgDTO pushDevuceMsgDTO = new PushDevuceMsgDTO();
//        pushDevuceMsgDTO.setKey("30000");
//        pushDevuceMsgDTO.setCmd( googleReqDTO.getResult().getParameters().get("cmd"));
////        pushDevuceMsgDTO.setToken();
//        pushDevuceMsgDTO.setDeviceName(googleReqDTO.getResult().getParameters().get("deviceName"));
//        pushDevuceMsgDTO.setRequestTime(String.valueOf(System.currentTimeMillis()));
////        String reqData = GsonUtils.toJSON(pushDevuceMsgDTO);
////        PoolHttpClientUtils.post("http://sykj-xzy.com:8090/goohome/push/device/msg",reqData);
//        return new GoogleRespDTO();
//    }
//
////    public static void main(String [] args){
////        String jsoStr = "{" +
////                "  \"id\": \"c1515325-fe6c-4f67-88ec-4fa997fed4c3\"," +
////                "  \"timestamp\": \"2017-08-29T07:26:33.576Z\"," +
////                "  \"lang\": \"en\"," +
////                "  \"result\": {" +
////                "    \"source\": \"agent\"," +
////                "    \"resolvedQuery\": \"turn on paste\"," +
////                          "    \"action\": \"\"," +
////                "    \"actionIncomplete\": false," +
////                "    \"parameters\": {" +
////                "      \"cmd\": \"on\"," +
////                "      \"deviceName\": \"paste\"" +
////                "    }," +
////                "    \"contexts\": []," +
////                "    \"metadata\": {" +
////                "      \"intentId\": \"762a6c87-dbc4-4d5c-9d9c-3a8b17bcef13\"," +
////                "      \"webhookUsed\": \"true\"," +
////                "      \"webhookForSlotFillingUsed\": \"true\"," +
////                "      \"webhookResponseTime\": 360," +
////                "      \"intentName\": \"ulexa\"" +
////                "    }," +
////                "    \"fulfillment\": {" +
////                "      \"speech\": \"\"," +
////                "      \"messages\": [" +
////                "        {" +
////                "          \"type\": 0," +
////                "          \"speech\": \"\"" +
////                "        }" +
////                "      ]" +
////                "    }," +
////                "    \"score\": 0.8999999761581421" +
////                "  }," +
////                "  \"status\": {" +
////                "    \"code\": 206," +
////                "    \"errorType\": \"partial_content\"," +
////                "    \"errorDetails\": \"Webhook call failed. Error: Webhook response was empty.\"" +
////                "  }," +
////                "  \"sessionId\": \"7b7c651b-f08c-4390-895c-9c6c88d9f416\"" +
////                "}";
////
////        GoogleReqDTO googleReqDTO = GsonUtils.toObj(jsoStr, GoogleReqDTO.class);
////        System.out.print(googleReqDTO.getResult().getParameters().get("cmd"));
////    }
//}
