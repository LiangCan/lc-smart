package com.sykj.uusmart.filter;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.ResponseDTO;
import com.sykj.uusmart.utils.GsonUtils;
import com.sykj.uusmart.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandlerController implements ErrorController {

    static String serverStr ;

    /**
     * 出异常后进入该方法，交由下面的方法处理
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @Autowired
    MessageUtils messageUtils;



    @RequestMapping("/error")
    public String error() {
        String errorMsg = messageUtils.getMessage(Constants.systemError.SERVER_ERROR, new Object[]{"请求的"});
        ResponseDTO responseDTO = new ResponseDTO(Constants.resultCode.SERVER_ERROR, errorMsg);
        return GsonUtils.toJSON(responseDTO);
    }
}