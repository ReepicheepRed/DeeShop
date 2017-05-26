package com.deeshop.bean;

import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/3/29.
 */

public class Title implements Serializable {
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> left = new ObservableField<>();
    public final ObservableField<String> right = new ObservableField<>();
}
