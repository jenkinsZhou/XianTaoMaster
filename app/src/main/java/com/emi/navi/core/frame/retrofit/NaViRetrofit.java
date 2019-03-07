package com.emi.navi.core.frame.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.emi.navi.core.frame.util.SslUtil;
import com.emi.navi.core.log.NaViLogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import javax.xml.transform.Transformer;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author :zhoujian
 * @description : Retrofit封装
 * @company :翼迈科技
 * @date 2019年03月04日下午 01:25
 * @Email: 971613168@qq.com
 */
public class NaViRetrofit {


    private static volatile NaViRetrofit sManager;
    private static volatile Retrofit sRetrofit;
    private static volatile Retrofit.Builder sRetrofitBuilder;
    private static OkHttpClient.Builder sClientBuilder;
    private static OkHttpClient sClient;
    /**
     * 重定向访问Url--通过设置header模式
     */
    public static final String BASE_URL_NAME_HEADER = MultiUrl.BASE_URL_NAME_HEADER;
    /**
     * 默认读、写、连接超时
     */
    private long mDelayTime = 20;
    /**
     * 是否打印json格式 通过Logger.json
     */
    private boolean mLogJsonEnable = true;
    /**
     * Service 缓存-避免重复创建同一个Service
     */
    private Map<String, Object> mServiceMap = new HashMap<>();
    /**
     * 证书配置
     */
    private SslUtil.SslParams mSslParams = new SslUtil.SslParams();
    /**
     * 日志拦截器
     */
    private HttpLoggingInterceptor mLoggingInterceptor;
    /**
     * 统一header
     */
    private Map<String, Object> mHeaderMap = new HashMap<>();
    /**
     * header拦截器
     */
    private Interceptor mHeaderInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder request = chain.request().newBuilder();
            //避免某些服务器配置攻击,请求返回403 forbidden 问题
            addHeader("User-Agent", "Mozilla/5.0 (Android)");
            if (mHeaderMap.size() > 0) {
                for (Map.Entry<String, Object> entry : mHeaderMap.entrySet()) {
                    request.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
            return chain.proceed(request.build());
        }
    };

    private NaViRetrofit() {
        sClientBuilder = new OkHttpClient.Builder();
        sClientBuilder.addInterceptor(mHeaderInterceptor);
        sRetrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        setTimeout(mDelayTime);
        MultiUrl.getInstance().with(sClientBuilder);
    }

    public static NaViRetrofit getInstance() {
        if (sManager == null) {
            synchronized (NaViRetrofit.class) {
                if (sManager == null) {
                    sManager = new NaViRetrofit();
                }
            }
        }
        return sManager;
    }

    /**
     * 对外暴露 OkHttpClient,方便自定义
     *
     * @return
     */
    public static OkHttpClient.Builder getOkHttpClientBuilder() {
        return sClientBuilder;
    }

    public static OkHttpClient getOkHttpClient() {
        if (sClient == null) {
            sClient = getOkHttpClientBuilder().build();
        }
        return sClient;
    }

    /**
     * 对外暴露 Retrofit,方便自定义
     *
     * @return
     */
    public static Retrofit.Builder getRetrofitBuilder() {
        sRetrofitBuilder.client(getOkHttpClient());
        return sRetrofitBuilder;
    }

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            sRetrofit = getRetrofitBuilder().build();
        }
        return sRetrofit;
    }

    /**
     * 获取请求service 默认缓存处理
     *
     * @param apiService 目标Service
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> apiService) {
        return createService(apiService, true);
    }

    /**
     * 创建Service
     *
     * @param apiService
     * @param useCacheEnable --是否使用缓存Service
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> apiService, boolean useCacheEnable) {
        if (useCacheEnable && apiService != null) {
            if (mServiceMap.containsKey(apiService.getName())) {
                NaViLogUtil.i("className:" + apiService.getName() + ";service取自缓存");
                return (T) mServiceMap.get(apiService.getName());
            }
            T tClass = getRetrofit().create(apiService);
            mServiceMap.put(apiService.getName(), tClass);
            return tClass;
        }
        return getRetrofit().create(apiService);
    }

    /**
     * @param fileUrl 下载全路径 配合{@link FastDownloadObserver}实现文件下载进度监听
     * @return
     */
    public Observable<ResponseBody> downloadFile(String fileUrl) {
        return downloadFile(fileUrl, null);
    }

    /**
     * 下载文件
     *
     * @param fileUrl 下载全路径 配合{@link FastDownloadObserver}实现文件下载进度监听
     * @return
     */
    public Observable<ResponseBody> downloadFile(String fileUrl, Map<String, Object> header) {
        //下载前获取当前日志是否开启
        final boolean logEnable = NaViRetrofit.getInstance().isLogEnable();
        //下载前关闭日志功能--日志拦截器会不停拦截文件流造成无法进入onNext回调本地进行保存文件操作给人感觉就是卡住
        NaViRetrofit.getInstance().setLogEnable(false);
        return NaViRetrofit.getRetrofit()
                .create(NaViRetrofitService.class)
                .downloadFile(fileUrl, header == null ? new HashMap<String, Object>(0) : header)
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        //onNext回调前还原log状态
                        NaViRetrofit.getInstance().setLogEnable(logEnable);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * 上传文件/参数 配合{@link FastUploadRequestBody}及{@link FastUploadRequestListener}实现上传进度监听
     *
     * @param uploadUrl
     * @param body
     * @return
     */
    public Observable<ResponseBody> uploadFile(String uploadUrl, @Nullable RequestBody body) {
        return uploadFile(uploadUrl, body, null);
    }

    /**
     * 上传文件/参数 配合{@link FastUploadRequestBody}及{@link FastUploadRequestListener}实现上传进度监听
     *
     * @param uploadUrl 请求全路径
     * @param body      请求body 可将文件及其他参数放进body
     * @param header    可设置额外请求头信息
     * @return
     */
    public Observable<ResponseBody> uploadFile(String uploadUrl, @Nullable final RequestBody body, Map<String, Object> header) {
        return getRetrofit()
                .create(NaViRetrofitService.class)
                .uploadFile(uploadUrl, body, header == null ? new HashMap<String, Object>() : header)
                .compose(NaViTransformer.<ResponseBody>switchSchedulers());
    }

    /**
     * 设置请求头{@link #mHeaderInterceptor}
     *
     * @param key
     * @param value
     * @return
     */
    public NaViRetrofit addHeader(String key, Object value) {
        if (!TextUtils.isEmpty(key) && value != null) {
            mHeaderMap.put(key, value);
        }
        return this;
    }

    /**
     * 添加统一的请求头
     *
     * @param map
     * @return
     */
    public NaViRetrofit addHeader(final Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            mHeaderMap.putAll(map);
        }
        return this;
    }

    /**
     * 清空全局请求头
     *
     * @param key
     * @return
     */
    public NaViRetrofit removeHeader(String key) {
        if (!TextUtils.isEmpty(key) && mHeaderMap.containsKey(key)) {
            mHeaderMap.remove(key);
        }
        return this;
    }

    /**
     * 清空所有全局请求头
     *
     * @return
     */
    public NaViRetrofit removeHeader() {
        mHeaderMap.clear();
        return this;
    }

    /**
     * 设置全局BaseUrl
     *
     * @param baseUrl
     * @return
     */
    public NaViRetrofit setBaseUrl(String baseUrl) {
        sRetrofitBuilder.baseUrl(baseUrl);
        MultiUrl.getInstance().setGlobalBaseUrl(baseUrl);
        return this;
    }

    public NaViRetrofit setRetryOnConnectionFailure(boolean enable) {
        sClientBuilder.retryOnConnectionFailure(enable);
        return this;
    }

    public NaViRetrofit setTimeout(long second) {
        mDelayTime = second;
        return setTimeout(second, TimeUnit.SECONDS);
    }

    /**
     * 设置所有超时
     *
     * @param second
     * @param unit
     * @return
     */
    public NaViRetrofit setTimeout(long second, TimeUnit unit) {
        setReadTimeout(second, unit);
        setWriteTimeout(second, unit);
        setConnectTimeout(second, unit);
        return this;
    }


    public NaViRetrofit setReadTimeout(long second) {
        return setReadTimeout(second, TimeUnit.SECONDS);
    }

    /**
     * 设置读取超时时间
     *
     * @param second
     * @param unit   单位
     * @return
     */
    public NaViRetrofit setReadTimeout(long second, TimeUnit unit) {
        sClientBuilder.readTimeout(second, unit);
        return this;
    }

    public NaViRetrofit setWriteTimeout(long second) {
        return setWriteTimeout(second, TimeUnit.SECONDS);
    }

    /**
     * 设置写入超时时间
     *
     * @param second
     * @param unit
     * @return
     */
    public NaViRetrofit setWriteTimeout(long second, TimeUnit unit) {
        sClientBuilder.writeTimeout(second, unit);
        return this;
    }

    public NaViRetrofit setConnectTimeout(long second) {
        return setConnectTimeout(second, TimeUnit.SECONDS);
    }

    /**
     * 设置连接超时时间
     *
     * @param second
     * @param unit
     * @return
     */
    public NaViRetrofit setConnectTimeout(long second, TimeUnit unit) {
        sClientBuilder.connectTimeout(second, unit);
        return this;
    }

    /**
     * 是否通过Logger.json打印json格式的返回日志
     *
     * @param enable
     * @return
     */
    public NaViRetrofit setLogJsonEnable(boolean enable) {
        this.mLogJsonEnable = enable;
        return this;
    }

    /**
     * 获取当前是否设置日志打印
     *
     * @return
     */
    public boolean isLogEnable() {
        return mLoggingInterceptor != null && mLoggingInterceptor.getLevel() != HttpLoggingInterceptor.Level.NONE;
    }

    /**
     * 设置日志打印
     *
     * @param enable
     * @return
     */
    public NaViRetrofit setLogEnable(boolean enable) {
        return setLogEnable(enable, this.getClass().getSimpleName(), HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * 设置是否打印日志
     *
     * @param enable
     * @param tag
     * @return
     */
    public NaViRetrofit setLogEnable(boolean enable, String tag, HttpLoggingInterceptor.Level level) {
        if (TextUtils.isEmpty(tag)) {
            tag = getClass().getSimpleName();
        }
        if (enable) {
            if (mLoggingInterceptor == null) {
                final String finalTag = tag;
                mLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        if (TextUtils.isEmpty(message)) {
                            return;
                        }
                        //json格式使用Logger.json打印
                        boolean isJson = message.startsWith("[") || message.startsWith("{");
                        isJson = isJson && mLogJsonEnable;
                        if (isJson) {
                            NaViLogUtil.json(finalTag, message);
                            return;
                        }
                        Log.d(finalTag, message);
                    }
                });
            }
            mLoggingInterceptor.setLevel(level);
            sClientBuilder.addInterceptor(mLoggingInterceptor);
        } else {
            if (mLoggingInterceptor != null) {
                mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }
        }
        return this;
    }

    /**
     * 获取证书配置
     *
     * @return
     */
    public SslUtil.SslParams getCertificates() {
        return mSslParams;
    }


    /**
     * 设置服务器证书 {@link OkHttpClient.Builder#sslSocketFactory(SSLSocketFactory, X509TrustManager)}
     *
     * @param sslSocketFactory
     * @param trustManager
     * @return
     */
    public NaViRetrofit setCertificates(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
        mSslParams.sslSocketFactory = sslSocketFactory;
        mSslParams.trustManager = trustManager;
        sClientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
        return this;
    }

    /**
     * 默认信任所有证书-不安全
     *
     * @return
     */
    public NaViRetrofit setCertificates() {
        SslUtil.SslParams sslParams = SslUtil.getSslSocketFactory();
        return setCertificates(sslParams.sslSocketFactory, sslParams.trustManager);
    }

    /**
     * @param trustManager 使用自己设置的X509TrustManager
     * @return
     */
    public NaViRetrofit setCertificates(X509TrustManager trustManager) {
        SslUtil.SslParams sslParams = SslUtil.getSslSocketFactory(trustManager);
        return setCertificates(sslParams.sslSocketFactory, sslParams.trustManager);
    }

    /**
     * 使用预埋证书,校验服务端证书(自签名证书)-单向认证
     *
     * @param certificates 用含有服务端公钥的证书校验服务端证书
     * @return
     */
    public NaViRetrofit setCertificates(InputStream... certificates) {
        SslUtil.SslParams sslParams = SslUtil.getSslSocketFactory(certificates);
        return setCertificates(sslParams.sslSocketFactory, sslParams.trustManager);
    }

    /**
     * 使用bks证书和密码管理客户端证书(双向认证),使用预埋证书,校验服务端证书(自签名证书)
     * 客户端使用bks证书校验服务端证书;
     *
     * @param bksFile
     * @param password
     * @param certificates 用含有服务端公钥的证书校验服务端证书
     * @return
     */
    public NaViRetrofit setCertificates(InputStream bksFile, String password, InputStream... certificates) {
        SslUtil.SslParams sslParams = SslUtil.getSslSocketFactory(bksFile, password, certificates);
        return setCertificates(sslParams.sslSocketFactory, sslParams.trustManager);
    }

    /**
     * 客户端使用bks证书校验服务端证书
     *
     * @param bksFile
     * @param password
     * @param trustManager 如果需要自己校验,那么可以自己实现相关校验;如果不需要自己校验,那么传null即可
     * @return
     */
    public NaViRetrofit setCertificates(InputStream bksFile, String password, X509TrustManager trustManager) {
        SslUtil.SslParams sslParams = SslUtil.getSslSocketFactory(bksFile, password, trustManager);
        return setCertificates(sslParams.sslSocketFactory, sslParams.trustManager);
    }

    /**
     * 设置多BaseUrl-解析器
     *
     * @param parser
     * @return
     */
    public NaViRetrofit setUrlParser(UrlParser parser) {
        MultiUrl.getInstance().setUrlParser(parser);
        return this;
    }

    /**
     * 控制管理器是否拦截,在每个域名地址都已经确定,不需要再动态更改时可设置为 false
     *
     * @param enable
     * @return
     */
    public NaViRetrofit setUrlInterceptEnable(boolean enable) {
        MultiUrl.getInstance().setIntercept(enable);
        return this;
    }

    /**
     * 是否Service Header设置多BaseUrl优先--默认method优先
     *
     * @param enable
     * @return
     */
    public NaViRetrofit setHeaderPriorityEnable(boolean enable) {
        MultiUrl.getInstance().setHeaderPriorityEnable(enable);
        return this;
    }

    /**
     * 存放多BaseUrl 映射关系service 设置header模式-需要才设置
     *
     * @param map
     * @return
     */
    public NaViRetrofit putHeaderBaseUrl(Map<String, String> map) {
        MultiUrl.getInstance().putHeaderBaseUrl(map);
        return this;
    }

    /**
     * 存放多BaseUrl 映射关系设置header模式-需要才设置
     *
     * @param urlKey
     * @param urlValue
     * @return
     */
    public NaViRetrofit putHeaderBaseUrl(String urlKey, String urlValue) {
        MultiUrl.getInstance().putHeaderBaseUrl(urlKey, urlValue);
        return this;
    }

    /**
     * 移除BaseUrl映射关系设置header模式{@link #putHeaderBaseUrl(String, String)} key对应
     *
     * @param urlKey
     * @return
     */
    public NaViRetrofit removeHeaderBaseUrl(String urlKey) {
        MultiUrl.getInstance().removeHeaderBaseUrl(urlKey);
        return this;
    }

    /**
     * 移除所有BaseUrl 映射关系设置header模式:即仅使用{@link #setBaseUrl(String)}中设置
     *
     * @return
     */
    public NaViRetrofit removeHeaderBaseUrl() {
        MultiUrl.getInstance().clearAllHeaderBaseUrl();
        return this;
    }

    /**
     * 存放多BaseUrl 映射关系method模式-需要才设置
     *
     * @param map
     * @return
     */
    public NaViRetrofit putBaseUrl(Map<String, String> map) {
        MultiUrl.getInstance().putBaseUrl(map);
        return this;
    }

    /**
     * 存放多BaseUrl 映射关系method模式-需要才设置
     *
     * @param method   retrofit service 除baseUrl外的部分即@POST或@GET里的内容
     * @param urlValue
     * @return
     */
    public NaViRetrofit putBaseUrl(String method, String urlValue) {
        MultiUrl.getInstance().putBaseUrl(method, urlValue);
        return this;
    }

    /**
     * 移除BaseUrl映射关系映射关系method模式{@link #putBaseUrl(String, String)} key对应
     *
     * @param method retrofit service 除baseUrl外的部分即@POST或@GET里的内容
     * @return
     */
    public NaViRetrofit removeBaseUrl(String method) {
        MultiUrl.getInstance().removeBaseUrl(method);
        return this;
    }

    /**
     * 移除所有BaseUrl 映射关系method模式:即仅使用{@link #setBaseUrl(String)}中设置
     *
     * @return
     */
    public NaViRetrofit removeBaseUrl() {
        MultiUrl.getInstance().clearAllBaseUrl();
        return this;
    }
}
