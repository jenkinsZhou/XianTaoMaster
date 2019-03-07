package com.emi.navi.core.frame.retrofit;

import android.content.Context;

import com.emi.navi.core.frame.util.NetworkUtil;
import com.emi.navi.core.log.NaViLogUtil;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author :zhoujian
 * @description : RxJava 重试机制--retryWhen操作符{@link Observable#retryWhen(Function)}
 * @company :翼迈科技
 * @date 2019年03月04日上午 11:40
 * @Email: 971613168@qq.com
 */
public class RetryWhen implements Function<Observable<? extends Throwable>, ObservableSource<?>> {
    private Context mContext;
    /**
     * 最大尝试次数--不包含原始请求次数
     */
    private int mRetryMaxTime;
    /**
     * 尝试时间间隔ms
     */
    private long mRetryDelay;
    /**
     * 记录已尝试次数
     */
    private int mRetryCount;
    private String TAG = getClass().getSimpleName();

    public RetryWhen(Context context, int retryMaxTime, long retryDelay) {
        this.mContext = context;
        this.mRetryMaxTime = retryMaxTime;
        this.mRetryDelay = retryDelay;
    }

    public RetryWhen(Context context) {
        this(context, 3, 500);
    }

    public RetryWhen() {
        this(null);
    }

    /**
     * 重试间隔
     *
     * @param delay
     * @return
     */
    public RetryWhen setRetryDelay(long delay) {
        mRetryDelay = delay;
        return this;
    }

    /**
     * 重试次数
     *
     * @param time
     * @return
     */
    public RetryWhen setRetryMaxTime(int time) {
        mRetryMaxTime = time;
        return this;
    }

    /**
     * Applies this function to the given argument.
     *
     * @param observable the function argument
     * @return the function result
     */
    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) {
                //未连接网络直接返回异常
                if (!NetworkUtil.isConnected(mContext)) {
                    return Observable.error(throwable);
                }
                //仅仅对连接失败相关错误进行重试
                if (throwable instanceof ConnectException
                        || throwable instanceof UnknownHostException
                        || throwable instanceof SocketTimeoutException
                        || throwable instanceof SocketException
                        || throwable instanceof TimeoutException) {
                    if (++mRetryCount <= mRetryMaxTime) {
                        NaViLogUtil.e(TAG, "网络请求错误,将在 " + mRetryDelay + " ms后进行重试, 重试次数 " + mRetryCount + ";throwable:" + throwable);
                        return Observable.timer(mRetryDelay, TimeUnit.MILLISECONDS);
                    }
                }
                return Observable.error(throwable);
            }
        });
    }


}
