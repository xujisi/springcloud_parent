package util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author: 许集思
 * @date: 2020/5/24 15:39
 */
public class TimeHelper {

    private static SimpleDateFormat _dateFormat;
    private static Date _currentDate;

    /**
     * @Description:获取当前的系统时间格式为yyyyddmm
     * @author: 许集思
     * @date: 2020/3/2 0:50
     */
    public static String date_yyyyddmm() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowDay = String.valueOf(Long.parseLong(df.format(System.currentTimeMillis())));
        String dqrq = (nowDay.substring(0, 8)); //获取系统当前日期
        return dqrq;
    }

    private synchronized static String timeFormate(String format) {
        _dateFormat = new SimpleDateFormat(format);
        //获取当前的时间
        try {
            _currentDate = new Date();
        } catch (Exception ex) {
            _currentDate = Calendar.getInstance().getTime();
        }
        return _dateFormat.format(_currentDate);
    }

    /**
     * @Description:获取当前的系统时间格式为yyyyMMddHHmmssSSS
     * @author: 许集思
     * @date: 2020/3/2 0:50
     */
    public static String getCurDateTime_yyyyMMddHHmmssSSS() {
        return timeFormate("yyyyMMddHHmmssSSS");
    }
}
