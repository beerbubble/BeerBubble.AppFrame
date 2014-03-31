package com.beerbubble.application;

import java.lang.Thread.UncaughtExceptionHandler;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Looper;

public class ErrorHandler implements UncaughtExceptionHandler
{
    /** 是否开启日志输出, 在Debug状态下开启, 在Release状态下关闭以提升程序性能 */
    public static final boolean     DEBUG   = true;
    /** CrashHandler实例 */
    private static ErrorHandler     INSTANCE;
    private static volatile boolean onError = false;
    /** Debug Log Tag */
    public static final String      TAG     = "CrashHandler";

    /** 获取CrashHandler实例 ,单例模式 */
    public static ErrorHandler getInstance()
    {
        if (ErrorHandler.INSTANCE == null)
        {
            ErrorHandler.INSTANCE = new ErrorHandler();
        }
        return ErrorHandler.INSTANCE;
    }

    /** 程序的Context对象 */
    // private Context mContext;

    /** 保证只有一个CrashHandler实例 */
    private ErrorHandler()
    {
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     * 
     * @param ctx
     */
    public void setToErrorHandler(final Context ctx)
    {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */

    public void uncaughtException(final Thread thread, final Throwable ex)
    {
        ex.printStackTrace();
        new Thread()
        {
            @TargetApi(Build.VERSION_CODES.CUPCAKE)
            public void run()
            {
                if (ErrorHandler.onError)
                {
                    return;
                }
                ErrorHandler.onError = true;
                // Toast 显示需要出现在一个线程的消息队列中
                Looper.prepare();
                BeerBubbleApplication.exitApp();
                Looper.loop();
            }
        }.start();
    }
}
