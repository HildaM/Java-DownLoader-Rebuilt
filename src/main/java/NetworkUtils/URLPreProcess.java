package NetworkUtils;

/**
 * @ClassName: URLPreProcess
 * @Description: URL预处理类
 * @author: Hilda   Hilda_quan@163.com
 * @date: 2021/12/31 22:22
 */
public class URLPreProcess {
    public boolean checkAndProcessURL(String url) {
        // 检测有误
        if (!check(url)) {
            return false;
        }
        // 检测无误
        else process(url);


        return false;
    }


    /**
     * @Author Hilda
     * @Description //TODO 检测url的正确性
     * @Date 22:25 2021/12/31
     * @Param
     * @returnValue
     **/
    private boolean check(String url) {

        return false;
    }


    /**
     * @Author Hilda
     * @Description //TODO 处理URL
     * @Date 22:25 2021/12/31
     * @Param
     * @returnValue
     **/
    private void process(String url) {

    }
}
