package top.kwseeker.www.ssm_island.common;

public class ServerResponse<T> {

    // 状态码
    private int statusCode; // 0-Success 1-fail
    // 返回消息
    private String retMsg;
    // 返回数据
    private T retData;

    // 情景1：错误、正常只需返回消息无需返回数据
    public ServerResponse(int statusCode, String retMsg) {
        this.statusCode = statusCode;
        this.retMsg = retMsg;
    }
    // 情景2：正常只需返回数据无需消息
    public ServerResponse(int statusCode, T retData) {
        this.statusCode = statusCode;
        this.retData = retData;
    }
    // 情景3：正常返回且需要返回消息和数据
    public ServerResponse(int statusCode, String retMsg, T retData) {
        this.statusCode = statusCode;
        this.retMsg = retMsg;
        this.retData = retData;
    }

    public boolean isSuccess() {
        return this.statusCode == ResponseCode.SUCCESS.getCode();
    }

    public int getStatusCode() {
        return statusCode;
    }
    public String getRetMsg() {
        return retMsg;
    }
    public T getRetData() {
        return retData;
    }

    public static <T>ServerResponse<T> createBySuccessMessage(String retMsg) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), retMsg);   //Constructor 1
    }
    public static <T> ServerResponse<T> createBySuccessData(T retData) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), retData);
    }
    public static <T> ServerResponse<T> createBySuccessMsgAndData(String retMsg, T retData){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), retMsg, retData); //Constructor 3
    }
    public static <T> ServerResponse<T> createByErrorMessage(String retMsg) {
        return  new ServerResponse<>(ResponseCode.ERROR.getCode(), retMsg);
    }
    public static ServerResponse createByErrorCodeAndMsg(int errCode, String errMsg) {
        return new ServerResponse(errCode, errMsg);
    }

}
