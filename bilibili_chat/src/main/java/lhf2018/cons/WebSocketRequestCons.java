package lhf2018.cons;

public class WebSocketRequestCons {
    public static final String WEBSOCKET_REQUEST_URL = "ws://broadcastlv.chat.bilibili.com:2244/sub";
    //需要向该api请求来获取
    private static final String ROOM_ID = "690";
    public static final String ROOM_API_URL = "https://api.live.bilibili.com/room/v1/Room/room_init?id=" + ROOM_ID;
    public static final String CERTIFICATION_REQUEST_JSON = """
            {
                "uid": 0,
                "roomid": %s,
                "protover": 1,
                "platform": "web",
                "clientver": "1.4.0"
            }""";
    //每两个十六进制数为一个字节，格式见https://blog.csdn.net/xfgryujk/article/details/80306776
    public static final String CERTIFICATION_REQUEST_HEAD_HEX = "000000{replce}001000010000000700000001";
    //此为十六进制字符串，但需要转换成byte数组
    public static final String HEART_BEAT_BYTE = "00000010001000010000000200000001";
}
