package com.deeshop.bean;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.deeshop.util.RxUtils;

import java.io.Serializable;

import rx.Observable;

/**
 * Created by zhiPeng.S on 2017/3/23.
 */

public class Password implements Serializable {
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> verifyCode = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public ObservableBoolean enabled = new ObservableBoolean(false);

    public Password() {
        Observable.combineLatest(RxUtils.toObservable(verifyCode), RxUtils.toObservable(password),(verifyCode, password) ->
                !TextUtils.isEmpty(verifyCode) && !TextUtils.isEmpty(password))
                .subscribe(result -> enabled.set(result), Throwable::printStackTrace);
    }
}
