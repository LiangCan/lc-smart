package com.sykj.uusmart.exception;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.pojo.DeviceInfo;
import com.sykj.uusmart.pojo.NexusUserDevice;
import com.sykj.uusmart.utils.ServiceUtils;
import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class CustomRunTimeException extends RuntimeException {

    private static final Logger logger = Logger.getLogger(CustomRunTimeException.class);
    private String errorCode;
    private String errorMsg;
    private String errorDetail;
    private Exception exception;
    private Object result;

    public CustomRunTimeException() {

    }

    public CustomRunTimeException(String errorCode) {
        super();
        this.errorCode = errorCode;
//        logger.error(String.format("errorCode【%s】", errorCode));
    }


    public CustomRunTimeException(String errorCode, String errorMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = ServiceUtils.messageUtils.getMessage(errorMsg);
//        logger.error(String.format("errorCode【%s】errorMsg【%s】", errorCode, this.errorMsg));
    }

    public CustomRunTimeException(String errorCode, String errorMsg, Object[] para) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = ServiceUtils.messageUtils.getMessage(errorMsg, para);
//        logger.error(String.format("errorCode【%s】errorMsg【%s】", errorCode, this.errorMsg));
    }
    public CustomRunTimeException(String errorCode, String errorMsg, Object[] para, Object result) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = ServiceUtils.messageUtils.getMessage(errorMsg, para);
        this.result = result;
//        logger.error(String.format("errorCode【%s】errorMsg【%s】", errorCode, this.errorMsg));
    }
    public CustomRunTimeException(String errorCode, String errorMsg, String errorDetail) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorDetail = errorDetail;
//        logger.error(String.format("errorCode【%s】errorMsg【%s】errorDetail【%s】", errorCode, errorMsg, this.errorMsg));
    }

    public CustomRunTimeException(String errorCode, Object result) {
        super();
        this.errorCode = errorCode;
        this.result = result;
        logger.error(String.format("errorCode【%s】", errorCode));
        printMessage(result);
    }

    public CustomRunTimeException(String errorCode, String errorMsg, Exception exception) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.exception = exception;

//        logger.error(String.format("errorCode【%s】errorMsg【%s】detail【%s】", errorCode, errorMsg, exception.getMessage()));
        printMessage(exception);
    }

    public CustomRunTimeException(String errorCode, String errorMsg, Object result) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.result = result;

//        logger.error(String.format("errorCode【%s】errorMsg【%s】", errorCode, errorMsg));
        printMessage(result);
    }

    public CustomRunTimeException(String errorCode, String errorMsg, Object result, String errorDetail) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorDetail = errorDetail;
        this.result = result;

//        logger.error(String.format("errorCode【%s】errorMsg【%s】errorDetail【%s】", errorCode, errorMsg, errorDetail));
        printMessage(result);
    }

    private void printMessage(Object result) {
        if (null != result) {
            if (result instanceof Exception)
                logger.error(String.format("exception【%s】", getStackMsg((Exception) result)));
            else
                logger.error(String.format("result【%s】", result.toString()));
        }
    }

    private String getStackMsg(Exception e) {
        StringBuffer sb = new StringBuffer();
        sb.append(e.toString()).append("\n");
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append(element.toString() + "\n");
        }
        return sb.toString();
    }

    private String getStackMsg(Throwable e) {
        StringBuffer sb = new StringBuffer();
        sb.append(e.toString()).append("\n");
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append(element.toString() + "\n");
        }
        return sb.toString();
    }


    /**
     * 校验数据是否为空
     * @param object
     * @param objName
     */
    public static void checkNull(Object object, String objName){
        if(object == null){
            throw new CustomRunTimeException(Constants.resultCode.API_DATA_IS_NULL, Constants.systemError.API_DATA_IS_NULL, new Object[]{objName});
        }
    }


    /**
     * 校验数据权限
     */
    public static void checkDeviceJurisdiction(NexusUserDevice nexusUserDevice, boolean checkJurisdction){
        if(nexusUserDevice == null){
            throw new CustomRunTimeException(Constants.resultCode.API_DATA_IS_NULL, Constants.systemError.API_DATA_IS_NULL, new Object[]{"NexusUserDevice"});
        }

        if(checkJurisdction){
            if(nexusUserDevice.getRole() == Constants.shortNumber.TWO){
                throw new CustomRunTimeException(Constants.resultCode.API_USER_JURISDICTION_ERROR, Constants.systemError.API_USER_JURISDICTION_ERROR, new Object[]{"Device"});
            }
        }
    }


    /**
     * 校验数据是否为空
     * @param object
     * @param objName
     */
    public static void checkNull(String object, String objName){
        if(object == null){
            throw new CustomRunTimeException(Constants.resultCode.API_DATA_IS_NULL, Constants.systemError.API_DATA_IS_NULL, new Object[]{objName});
        }
    }

    /**
     * 参数错误
     * @param objName
     */
    public static void parameterError(String objName){
        throw new CustomRunTimeException(Constants.resultCode.PARAM_VALUE_INVALID, Constants.systemError.PARAM_VALUE_INVALID, new Object[]{objName});
    }
    /**
     * 校验数据是否为空
     * @param object
     * @param objName
     */
    public static void checkNull(Object object, String objName, Object result){
        if(object == null){
            throw new CustomRunTimeException(Constants.resultCode.API_DATA_IS_NULL, Constants.systemError.API_DATA_IS_NULL, new Object[]{objName}, result);
        }
    }


    /**
     * 校验数据是否不为空
     * @param object
     * @param objName
     */
    public static void checkNotNull(Object object, String objName){
        if(object != null){
            throw new CustomRunTimeException(Constants.resultCode.API_DATA_IS_NOT_NULL, Constants.systemError.API_DATA_IS_NOT_NULL, new Object[]{objName});
        }
    }


    /**
     * 校验设备是否离线
     */
    public static void checkDeviceIsOffLine(DeviceInfo deviceInfo, boolean checkOffline){

        if(deviceInfo == null){
            throw new CustomRunTimeException(Constants.resultCode.API_DATA_IS_NOT_NULL, Constants.systemError.API_DATA_IS_NOT_NULL, new Object[]{" Device "});
        }
        if(checkOffline){
            if(deviceInfo.getDeviceStatus() != 1){
                throw new CustomRunTimeException(Constants.resultCode.DEVICE_IS_OFF_LINE, Constants.systemError.DEVICE_IS_OFF_LINE, new Object[]{String.valueOf(deviceInfo.getDeviceId())});
            }
        }
    }

    public static void checkQX(Object object, String objName){
//         throw new CustomRunTimeException(ResultCode.API_NOT_JURISDICTION, messageUtils.getMessage(MessageKey.API_NOT_JURISDICTION));
    }

    @Override
    public Throwable fillInStackTrace() {

        return super.fillInStackTrace();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


}