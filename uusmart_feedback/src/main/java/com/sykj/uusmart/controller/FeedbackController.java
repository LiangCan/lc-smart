package com.sykj.uusmart.controller;


import com.sykj.uusmart.http.ReqBaseDTO;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.http.req.UserAddFeedbackDTO;
import com.sykj.uusmart.service.FeedbackInfoService;
import com.sykj.uusmart.utils.GsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "反馈API")
@RestController
@RequestMapping(value = "/auth/feedback/", method = RequestMethod.POST)
public class FeedbackController extends BaseController{

    @Autowired
    FeedbackInfoService feedbackInfoService;

    @ApiOperation(value="提交反馈")
    @RequestMapping(value="/add/feedback.do", method = RequestMethod.POST)
    public String userAddFeedback (@RequestBody ReqBaseDTO<UserAddFeedbackDTO> reqBaseDTO, BindingResult bindingResult)throws CustomRunTimeException {
        validataBind(bindingResult,reqBaseDTO.gethG());
        return GsonUtils.toJSON(  feedbackInfoService.userAddFeedback( reqBaseDTO.gethG() ) );
    }

}
