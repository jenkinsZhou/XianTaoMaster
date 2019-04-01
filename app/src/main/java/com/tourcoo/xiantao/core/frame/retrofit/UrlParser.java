package com.tourcoo.xiantao.core.frame.retrofit;

import okhttp3.HttpUrl;

/**
 * @author :zhoujian
 * @description : 多BaseUrl解析器
 * @company :途酷科技
 * @date 2019年03月04日上午 11:14
 * @Email: 971613168@qq.com
 */
public interface UrlParser {

    /**
     * 将 {@link MultiUrl#mBaseUrlMap} 中映射的 Url 解析成完整的{@link HttpUrl}
     * 用来替换 @{@link Request#url} 里的BaseUrl以达到动态切换 Url的目的
     *
     * @param domainUrl 目标请求(base url)
     * @param url       需要替换的请求(原始url)
     * @return
     */
    HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url);
}
