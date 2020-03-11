package cn.lianhy.demo.constant;

import java.util.HashMap;
import java.util.Map;

public interface OpenConstant {
    String ENCRYPT_KEY= "qD$_wXcZjil_9J_2020_key";
    // 初始化Map
    Map<String, Object> ANONYMOUS_URI_MAP = new HashMap<String, Object>() { // 匿名访问的uri（区分大小写）

        private static final long serialVersionUID = 7442385973283937361L;

        {
            //拦截的URL
            put("/demo/sayHello.json", true);
        }
    };


}
