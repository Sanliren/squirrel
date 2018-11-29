package cn.thirdpart.squirrel.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ResponseResult implements Serializable {
    /**
     * 状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;

    public ResponseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public ResponseResult() {}
}
