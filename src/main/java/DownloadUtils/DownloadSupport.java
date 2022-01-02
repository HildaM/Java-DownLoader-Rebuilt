package DownloadUtils;

import FileUtils.FileUtils;
import LogUtils.LogThread;
import LogUtils.Logging;
import NetworkUtils.HttpUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @ClassName: DownloadSupport
 * @Description: 下载核心类
 * @author: Hilda   Hilda_quan@163.com
 * @date: 2022/1/2 16:59
 */
public class DownloadSupport {
    // 默认下载线程数
    private static int DOWNLOAD_THREAD_NUM = 5;

    // 下载线程池
    private static ExecutorService executor = null;
    
    // 线程数组
    private static ArrayList<Future<Boolean>> futureList = null;

    // 临时文件后缀名
    private static String FILE_TEMP_SUFFIX = ".temp";

    // 下载文件名
    private static String fileName = null;

    // 本地下载下载文件长度
    private static Long localFileLength = null;

    // 网络文件长度
    private static Long downloadFileLength = null;


    /**
     * @Author Hilda
     * @Description //TODO 初始化数据
     * @Date 18:16 2022/1/2
     * @Param []
     * @returnValue void
     **/
    private void initDownload() {
        // 让用户决定下载线程数
        setDownloadThreadNum();

        // 初始化下载线程池
        executor = getExecutor();

        // 创建一个装载着线程的List
        futureList = new ArrayList<>();
    }


    /**
     * @Author Hilda
     * @Description //TODO 下载检测
     * @Date 16:59 2022/1/2
     * @Param
     * @returnValue
     **/
    public void downloadCheck(String url) {
        // 获取下载的文件名
        fileName = HttpUtils.getFileName(url);

        // 获取本地文件长度
        localFileLength = FileUtils.getFileLength(fileName);

        // 获取待下载文件长度
        downloadFileLength = HttpUtils.getFileLength(url);

        // 检测是否已经下载完毕了
        if (localFileLength >= downloadFileLength) {
            Logging.info("{} 已经下载完毕，无需重复下载", fileName);
            return;
        }


        // 若还没有下载完成
        if (localFileLength > 0) {
            Logging.info("{} 开始断点续传", fileName);
        }
        else {
            Logging.info("{} 开始下载文件", fileName);
        }

    }


    /**
     * @Author Hilda
     * @Description //TODO 下载核心
     * @Date 17:01 2022/1/2
     * @Param []
     * @returnValue void
     **/
    public void downloadFile(String url) {
        // 下载程序初始化
        initDownload();

        // 开始下载
        long startTime = System.currentTimeMillis();

        // 划分任务
        splitDownload(url, futureList);

        // 日志追踪
        logTrack(futureList);

        // 执行下载
        for (Future<Boolean> future : futureList) {
            try {
                future.get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


        // 输出日志
        Logging.info("文件下载完毕 {}, 本次下载耗时：{}",
                fileName, (System.currentTimeMillis() - startTime) / 1000 + "s");
        Logging.info("结束下载时间 {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));


        // 文件合并
        mergeFiles(fileName);

        // 结束下载
        Logging.info("本次下载完成");
    }


    /**
     * @Author Hilda
     * @Description //TODO 初始化线程数的静态函数
     * @Date 17:25 2022/1/2
     * @Param []
     * @returnValue java.util.concurrent.ExecutorService
     * Note：线程池参数的设定参考：
     *       https://blog.csdn.net/riemann_/article/details/104704197
     **/
    private static ExecutorService getExecutor() {
        int cpuCores = Runtime.getRuntime().availableProcessors();

        return new ThreadPoolExecutor(DOWNLOAD_THREAD_NUM + 1, DOWNLOAD_THREAD_NUM + 1,
                                        0L, TimeUnit.MILLISECONDS,
                                        new ArrayBlockingQueue<Runnable>(DOWNLOAD_THREAD_NUM + 1));
    }


    /**
     * @Author Hilda
     * @Description //TODO 设定线程池参数
     * @Date 17:28 2022/1/2
     * @Param [downloadThreadNum]
     * @returnValue void
     **/
    private static void setDownloadThreadNum() {
        Scanner in = new Scanner(System.in);
        int threadNum = 5;

        while (true) {
            System.out.println("请输入下载的线程数：(正整数)");
            threadNum = in.nextInt();

            if (threadNum <= 0 || threadNum > Integer.MAX_VALUE) {
                System.out.println("输入数值不合法！！！请重新输入！");
            }
            else break;
        }

        DOWNLOAD_THREAD_NUM = threadNum;
    }


    /**
     * @Author Hilda
     * @Description //TODO 多线程下载
     * @Date 18:27 2022/1/2
     * @Param [url, futureList]
     * @returnValue void
     **/
    private static void splitDownload(String url, ArrayList<Future<Boolean>> futureList) {
        
    }


    /**
     * @Author Hilda
     * @Description //TODO 下载日志追踪
     * @Date 18:39 2022/1/2
     * @Param [futureList]
     * @returnValue void
     **/
    private static void logTrack(ArrayList<Future<Boolean>> futureList) {
        LogThread logThread = new LogThread(downloadFileLength);
        Future<Boolean> logFuture = executor.submit(logThread);
        futureList.add(logFuture);
    }


    /**
     * @Author Hilda
     * @Description //TODO 合并多线程下载后的文件
     * @Date 18:40 2022/1/2
     * @Param [fileName]
     * @returnValue void
     **/
    private static void mergeFiles(String fileName) {

    }
}
