package com.deeshop.bean;

import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/3/29.
 */

public class Pop implements Serializable{
    public final ObservableField<String> string1 = new ObservableField<>();
    public final ObservableField<String> string2 = new ObservableField<>();
    public final ObservableField<String> string3 = new ObservableField<>();
}
