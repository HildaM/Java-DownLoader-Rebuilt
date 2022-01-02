package LogUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: Logging
 * @Description: 记录、输出日志
 * @author: Hilda   Hilda_quan@163.com
 * @date: 2022/1/2 17:54
 */
public class Logging {

    private static final boolean DEBUG = false;

    // 通用时间格式
    private static final DateTimeFormatter dateTimeFormatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");


    /**
     * @Author Hilda
     * @Description //TODO 输出常规信息
     * @Date 17:55 2022/1/2
     * @Param []
     * @returnValue java.lang.String
     **/
    public static void info(String msg, Object... args) {
        print(msg, "-INFO-", args);
    }
    
    
    /**
     * @Author Hilda
     * @Description //TODO 公共输出方法
     * @Date 17:57 2022/1/2
     * @Param []
     * @returnValue java.lang.String
     **/
    private static void print(String msg, String level, Object... args) {
        if (args != null && args.length > 0) {
            msg = String.format(msg.replace("{}", "%s"), args);
        }

        String thread = Thread.currentThread().getName();

        System.out.println(LocalDateTime.now().format(dateTimeFormatter) + " " + thread + level + msg);
    }
}
