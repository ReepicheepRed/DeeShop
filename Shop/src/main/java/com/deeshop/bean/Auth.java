package com.deeshop.bean;

import android.content.ContentResolver;
import android.databinding.Observable;
import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/3/31.
 */

public class Auth implements Serializable {
    public final ObservableField<String> url = new ObservableField<>();
    public final ObservableField<Boolean> isShow = new ObservableField<>(false);
    public final ObservableField<Boolean> enabled = new ObservableField<>(false);

    public Auth(){
        url.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                isShow.set(!url.get().contains(ContentResolver.SCHEME_ANDROID_RESOURCE));
            }
        });
    }
}
