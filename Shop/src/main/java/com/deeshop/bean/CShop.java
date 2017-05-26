package com.deeshop.bean;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.deeshop.util.RxUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import rx.Observable;

/**
 * Created by zhiPeng.S on 2017/4/5.
 */

public class CShop implements Serializable {
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> facebook = new ObservableField<>();
    public final ObservableField<String> avatar = new ObservableField<>();
    public ObservableBoolean enabled = new ObservableBoolean(false);

    public CShop() {
        Observable.combineLatest(RxUtils.toObservable(name), RxUtils.toObservable(facebook),
                (name, facebook) -> !TextUtils.isEmpty(name) && !TextUtils.isEmpty(facebook))
                .subscribe(result -> enabled.set(result), Throwable::printStackTrace);
    }

    /**
     * returncode : success
     * shopinfo : {"shopid":13,"shaopname":"125864","facebook":"155888888","userid":8,"logoimgurl":""}
     */

    public final ObservableField<String> returncode = new ObservableField<>();
    public ShopinfoBean shopinfo;

    public static class ShopinfoBean implements Serializable{
        /**
         * shopid : 13
         * shaopname : 125864
         * facebook : 155888888
         * userid : 8
         * logoimgurl :
         */

        public final ObservableField<Integer> shopid = new ObservableField<>();
        public final ObservableField<String> shaopname = new ObservableField<>();
        @SerializedName("facebook")
        public final ObservableField<String> facebookX = new ObservableField<>();
        public final ObservableField<Integer> userid = new ObservableField<>();
        public final ObservableField<String> logoimgurl = new ObservableField<>();
    }
}
