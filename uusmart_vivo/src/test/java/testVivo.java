import com.alibaba.fastjson.JSONObject;
import com.sykj.uusmart.http.vivo.VivoCommonRespDTO;
import com.sykj.uusmart.service.impl.VivoServiceImpl;
import com.sykj.uusmart.utils.RSAUtils;
import com.sykj.uusmart.utils.SYStringUtils;
import com.sykj.uusmart.utils.VivoHttpUtils;
import com.sykj.uusmart.utils.VivoSHA256Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class
testVivo {

    private static final String vivoAppId = "8559240518239140180";
    private static final String clientId = "100000009";
    private static final String clientSecret = "bd153afbc08de203449dc4a36ca7d9d6";
    private static final String appKey = "e8e07d98054d47b5bd96b3f6b8751d80";
    private static final String vivoPublicKey = "305c300d06092a864886f70d0101010500034b00304802410093538ff138e1aaa7abd394279079cde92fb117a4d5466b4d03e63362ecec66f5d47688df0ac76546f138b9fbc7f95adf3caa3d70577d5b927a0384c7f7d38fc90203010001";


    public static void main(String[] args) {

        VivoCommonRespDTO respDTO = new VivoCommonRespDTO();
        JSONObject reqJson = new JSONObject();
        reqJson.put("clientId",clientId);
//        bd153afbc08de203449dc4a36ca7d9d6
        byte[] bytes = RSAUtils.encryptByPublicKey( clientSecret.getBytes(), vivoPublicKey);
        String encryptedClientSecret = RSAUtils.binToHex(bytes);

        System.out.println("clientSecret加密后:"+encryptedClientSecret);

        reqJson.put("clientSecret",encryptedClientSecret);
        reqJson.put("code","9cee2fca07fcaba1649392b9014cc522");
        reqJson.put("encryptVersion","- RSA");
        reqJson.put("secretVersion","- V1");

        Map<String,String> headMap = new HashMap<>();
        headMap.put("appId",vivoAppId);
        headMap.put("timestamp",new Date().getTime()+"");
//        headMap.put("signature", VivoSHA256Util.encrytSHA256("",clientSecret));
        headMap.put("nonce", SYStringUtils.getUUIDNotExistSymbol());
        String result = VivoHttpUtils.httpPost(reqJson,"https://iot-api-test.vivo.com.cn/v1/user/getOpenId",headMap,appKey);
        System.out.println( result );
    }
//        public static void main(String[] args) {
//            String content = "appId=60595676284285126318&nonce=e05c0ab6dac64894a74a67dd0642a494&timestamp=1534298800601{\"openId\":\"09bccb38661ccc039a271f2249b1e5de\",\"accessToken\":\"98b15d227776469990e203ded055d2d9\",\"refreshToken\":\"7c2944aa06f04530986bc3670ed7aff6\",\"expireIn\":\"86400\",\"clientId\":\"100000005\",\"accessTokenCP\":\"31ffff27dad453905a438f6e687eea84\"}";
//            String dd =  VivoSHA256Util.encrytSHA256(content,"0c6c75a6a21dcdc3bcedf6b2628a7a9c");
//            System.out.println(dd);
//        }



}
