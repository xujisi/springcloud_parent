package entity;

public class StatusCode {

    public static final int OK = 20000; //成功
    public static final int ERROR = 20001;//失败
    public static final int LOGINERROR = 20001;//用户名或者密码错误
    public static final int ACCESSERROR = 20001;//权限
    public static final int REMOTEERROR = 20001;//远程调用失败
    public static final int REPERROR = 20001;//重复操作

}
