package com.tourcoo.xiantao.retrofit.repository;

import android.accounts.NetworkErrorException;


import com.tourcoo.xiantao.core.frame.retrofit.RetryWhen;
import com.tourcoo.xiantao.core.frame.retrofit.TourCooTransformer;
import com.tourcoo.xiantao.entity.BaseEntity;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * @author :JenkinsZhou
 * @description : retrofit使用基类封装
 * @company :途酷科技
 * @date 2019年03月27日15:48
 * @E-Mail: 971613168@qq.com
 */
public abstract class BaseRepository {

    /**
     * @param observable 用于解析 统一返回实体统一做相应的错误码--如登录失效
     * @param <T>
     * @return
     */
    protected <T> Observable<T> transform(Observable<BaseEntity<T>> observable) {
//        io.reactivex.functions;
        return TourCooTransformer.switchSchedulers(
                observable.retryWhen(new RetryWhen())
                        .flatMap((Function<BaseEntity<T>, ObservableSource<T>>) result -> {
                            if (result == null) {
                                return Observable.error(new NetworkErrorException());
                            } else {
                                return Observable.just(result.data);
                            }
                        }));
    }
}
