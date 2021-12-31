import URLUtils.URLPreProcess;

import java.util.Scanner;

/**
 * @ClassName: DownLoadMain
 * @Description: 下载器的入口
 * @author: Hilda   Hilda_quan@163.com
 * @date: 2021/12/31 21:35
 */
public class DownLoadMain {
    private static Scanner in = new Scanner(System.in);

    /**
     * @Author Hilda
     * @Description //TODO 下载器的入口函数
     * @Date 21:43 2021/12/31
     * @Param [args]
     * @returnValue void
     **/
    public static void main(String[] args) {
        DownLoadMain downLoadMain = new DownLoadMain();

        // URL检查并进行必要的转换
        String url = downLoadMain.enterURL();

        // 开始下载
        downLoadMain.downLoad(url);
    }


    /**
     * @Author Hilda
     * @Description //TODO 输入URL
     * @Date 21:43 2021/12/31
     * @Param
     * @returnValue
     **/
    private String enterURL() {
        String url = null;
        URLPreProcess pre = new URLPreProcess();

        // 循环检查
        while (true) {
            // 输入有误
            if (!pre.checkAndProcessURL(url)) {
                System.out.println("输入的URL有误！请重新输入！！！(输入quit退出下载)");
                url = in.nextLine();
                // 退出程序
                if (url.equals("quit")) break;
            }
            // 输入正确
            else {
                return url;
            }
        }

        return null;
    }


    /**
     * @Author Hilda
     * @Description //TODO 下载核心模块
     * @Date 21:45 2021/12/31
     * @Param
     * @returnValue
     **/
    private void downLoad(String url) {

    }
}
