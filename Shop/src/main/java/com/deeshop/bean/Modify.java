package com.deeshop.bean;

import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/4/1.
 */

public class Modify implements Serializable {
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> type = new ObservableField<>();
    public final ObservableField<String> id = new ObservableField<>();

}
