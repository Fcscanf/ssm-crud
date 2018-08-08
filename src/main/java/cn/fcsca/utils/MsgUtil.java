package cn.fcsca.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * MsgUtil
 *
 * @author Fcscanf@樊乘乘
 * @description 通用的返回的类
 * @date 下午 13:18 2018-08-07
 */
@Data
public class MsgUtil {

    /**
     * 状态码 100-成功 200-失败
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 用户返回给浏览器的数据
     */
    private Map<String, Object> extend = new HashMap<String, Object>();

    public static MsgUtil success() {
        MsgUtil msgUtil = new MsgUtil();
        msgUtil.setCode(100);
        msgUtil.setMsg("success!");
        return msgUtil;
    }

    public static MsgUtil fail() {
        MsgUtil msgUtil = new MsgUtil();
        msgUtil.setCode(200);
        msgUtil.setMsg("fail!");
        return msgUtil;
    }

    public MsgUtil add(String key, Object value) {
        this.getExtend().put(key, value);
        return this;
    }
}
