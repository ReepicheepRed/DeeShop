package com.deeshop.bean;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiPeng.S on 2017/4/20.
 */

public class Common implements Serializable {

    /**
     * returncode : success
     * tipslist : [{"titile":"测试","system":0,"content":"测数据","time":"2017-04-20t00:00:00"},{"titile":"测试其它","system":2,"content":"测试其它","time":"2017-04-21t10:50:00"},{"titile":"测试测试","system":0,"content":"v那on个哭咯老婆不管丰富的","time":"2017-04-20t16:14:20"}]
     */

    public final ObservableField<String> returncode = new ObservableField<>();
    public final ObservableField<String> msg = new ObservableField<>();

    public final ObservableArrayList<Message> tipslist = new ObservableArrayList<>();
}
