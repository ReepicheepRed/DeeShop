package com.deeshop.bean;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/4/1.
 */

public class Message implements Serializable {
    /**
     * titile : 测试
     * system : 0
     * content : 测数据
     * time : 2017-04-20t00:00:00
     */

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<Integer> system = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> time = new ObservableField<>();

}
