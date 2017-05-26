package com.deeshop.bean;

import android.databinding.ObservableField;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.deeshop.util.RxUtils;

import java.io.Serializable;

import rx.Observable;
import rx.functions.Func5;

/**
 * Created by zhiPeng.S on 2017/3/31.
 */

public class Goods implements Serializable {

    public final ObservableField<String> goodname = new ObservableField<>();
    public final ObservableField<CharSequence> content = new ObservableField<>();
    public final ObservableField<String> type = new ObservableField<>();
    public final ObservableField<Integer> typeId = new ObservableField<>();
    public final ObservableField<Boolean> isDis = new ObservableField<>(false);
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> profit = new ObservableField<>();

    public final ObservableField<Boolean> enabled = new ObservableField<>(false);
    public final ObservableField<Boolean> isShow = new ObservableField<>(false);

    public Goods() {
        Observable.combineLatest(RxUtils.toObservable(goodname), RxUtils.toObservable(content),
                RxUtils.toObservable(type), RxUtils.toObservable(price), RxUtils.toObservable(profit),
                (param1, param2, param3, param4, param5) ->
                        !TextUtils.isEmpty(param1) && !TextUtils.isEmpty(param2)
                        && !TextUtils.isEmpty(param3) && !TextUtils.isEmpty(param4)
                        && !TextUtils.isEmpty(param5))
                .subscribe(enabled::set, Throwable::printStackTrace);
    }

    public boolean onTouch(View v, MotionEvent event) {
        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }
}
