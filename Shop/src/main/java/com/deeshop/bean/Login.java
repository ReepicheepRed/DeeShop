package com.deeshop.bean;



import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.widget.TextView;

import com.deeshop.util.RxUtils;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;


/**
 * Created by zhiPeng.S on 2017/3/23.
 */

public class Login {
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public  ObservableBoolean enabled = new ObservableBoolean(false);


    public Login() {
        Observable.combineLatest(RxUtils.toObservable(phoneNum), RxUtils.toObservable(password),
                new Func2<String, String, Boolean>() {
                    @Override
                    public Boolean call(String phoneNum1, String password1) {
                        return !TextUtils.isEmpty(phoneNum1) && !TextUtils.isEmpty(password1);
                    }
                })
                .subscribe(result -> enabled.set(result), Throwable::printStackTrace);
    }

}
