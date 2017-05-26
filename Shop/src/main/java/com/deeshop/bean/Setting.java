package com.deeshop.bean;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/3/27.
 */

public class Setting implements Serializable {
    public final ObservableField<String> icon = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableBoolean isCircle = new ObservableBoolean(false);
}
