package com.deeshop.bean;



import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class Market implements Serializable {

    /**
     * returncode : success
     * goodlist : [{"id":13,"goodid":"1014","goodname":"佳能 eos 70d 套机","shopid":1,"goodtype":1,"introduction":"佳能 eos 70d 套机","state":1,"price":"1000","profit":"10","discount":0,"img":"http://onegoods.nosdn.127.net/goods/340/d4f7fcc080a5c6e6428d496a3d9df6ba.png"},{"id":14,"goodid":"1015","goodname":"小米小盒子","shopid":1,"goodtype":1,"introduction":"小米小盒子","state":1,"price":"100","profit":"1","discount":0},{"id":15,"goodid":"1016","goodname":"周生生 18k金 白色黄金钻戒belief女款 指寸12","shopid":1,"goodtype":1,"introduction":"周生生 18k金 白色黄金钻戒belief女款 指寸12","state":1,"price":"10000","profit":"12","discount":0,"img":"http://onegoods.nosdn.127.net/goods/980/77780ff5a6d3ac6f505c6710da9837e0.png"},{"id":21,"goodid":"1022","goodname":"apple iphone6s plus 64g 颜色随机","shopid":1,"goodtype":1,"introduction":"apple iphone6s plus 64g 颜色随机","state":2,"price":"100","profit":"10","discount":0,"img":"http://onegoods.nosdn.127.net/goods/898/d3dc4b84825a35c50e2b5504d2b636cc.png"},{"id":22,"goodid":"1023","goodname":"三星 galaxy s6 edge 32g版 全网通公开版4g手机 g9250","shopid":1,"goodtype":1,"introduction":"三星 galaxy s6 edge 32g版 全网通公开版4g手机 g9250","state":2,"price":"100","profit":"10","discount":0,"img":"http://onegoods.nosdn.127.net/goods/411/cce28cfb6971fa488710c9ef65ffc2ba.png"},{"id":23,"goodid":"1024","goodname":"佳能 eos 70d 套机","shopid":1,"goodtype":1,"introduction":"佳能 eos 70d 套机","state":2,"price":"1000","profit":"10","discount":0,"img":"http://onegoods.nosdn.127.net/goods/340/d4f7fcc080a5c6e6428d496a3d9df6ba.png"},{"id":24,"goodid":"1025","goodname":"小米小盒子","shopid":1,"goodtype":1,"introduction":"小米小盒子","state":2,"price":"100","profit":"1","discount":0},{"id":25,"goodid":"1026","goodname":"周生生 18k金 白色黄金钻戒belief女款 指寸12","shopid":1,"goodtype":1,"introduction":"周生生 18k金 白色黄金钻戒belief女款 指寸12","state":2,"price":"10000","profit":"12","discount":0,"img":"http://onegoods.nosdn.127.net/goods/980/77780ff5a6d3ac6f505c6710da9837e0.png"},{"id":31,"goodid":"1032","goodname":"apple iphone6s plus 64g 颜色随机","shopid":1,"goodtype":1,"introduction":"apple iphone6s plus 64g 颜色随机","state":2,"price":"100","profit":"10","discount":0,"img":"http://onegoods.nosdn.127.net/goods/898/d3dc4b84825a35c50e2b5504d2b636cc.png"},{"id":32,"goodid":"1033","goodname":"三星 galaxy s6 edge 32g版 全网通公开版4g手机 g9250","shopid":1,"goodtype":1,"introduction":"三星 galaxy s6 edge 32g版 全网通公开版4g手机 g9250","state":2,"price":"100","profit":"10","discount":0,"img":"http://onegoods.nosdn.127.net/goods/411/cce28cfb6971fa488710c9ef65ffc2ba.png"}]
     */

    public final ObservableField<String> returncode = new ObservableField<>();
    public final ObservableArrayList<GoodlistBean> goodlist = new ObservableArrayList<>();

    public static class GoodlistBean implements Serializable{
        /**
         * id : 13
         * goodid : 1014
         * goodname : 佳能 eos 70d 套机
         * shopid : 1
         * goodtype : 1
         * introduction : 佳能 eos 70d 套机
         * state : 1
         * price : 1000
         * profit : 10
         * discount : 0
         * img : http://onegoods.nosdn.127.net/goods/340/d4f7fcc080a5c6e6428d496a3d9df6ba.png
         */

        public final ObservableField<Integer> id = new ObservableField<>();
        public final ObservableField<String> goodid = new ObservableField<>();
        public final ObservableField<String> goodname = new ObservableField<>();
        public final ObservableField<Integer> shopid = new ObservableField<Integer>();
        public final ObservableField<Integer> goodtype = new ObservableField<Integer>();
        public final ObservableField<String> introduction = new ObservableField<>();
        public final ObservableField<Integer> state = new ObservableField<Integer>();
        public final ObservableField<String> price = new ObservableField<>();
        public final ObservableField<String> profit = new ObservableField<>();
        public final ObservableField<Integer> discount = new ObservableField<Integer>();
        public final ObservableField<String> img = new ObservableField<>();

    }
}
