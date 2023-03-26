package lhf2018.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lhf2018.utils.HttpUtils;
import lhf2018.utils.ThreadPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 消费消息
 */
@Component
public class MessageConsumer {
    @PostConstruct
    public void init() {
        ThreadPoolUtil.run(new Task());
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            while (true) {
                while (!MessageRepo.MESSAGE_QUEUE.isEmpty()) {
                    try {
                        String msg = MessageRepo.MESSAGE_QUEUE.poll();
                        if (StringUtils.isEmpty(msg)) {
                            continue;
                        }
                        int emojiFirstIndex = msg.indexOf('[');
                        int emojiLastIndex = msg.lastIndexOf(']');
                        if (emojiFirstIndex == 0 && emojiLastIndex == msg.length() - 1) {
                            System.out.println("AI回答：" + "这是个表情，不予回答");
                            continue;
                        }
                        String req;
                        if (emojiFirstIndex == -1 || emojiLastIndex == -1) {
                            req = "{\"prompt\": \"" + msg + "\"}";
                        } else {
                            req = "{\"prompt\": \"" + msg.substring(0, emojiFirstIndex) + msg.substring(emojiLastIndex + 1) + "\"}";
                        }
                        String response = HttpUtils.sendPost("utf-8", "application/json", "http://127.0.0.1:8000", req);
                        System.out.println("==================================");
                        System.out.println("弹幕问题：" + msg);
                        JSONObject jsonObject = JSON.parseObject(response);
                        System.out.println("AI回答：" + jsonObject.get("response"));
                        System.out.println("==================================");
                    } catch (Throwable ignored) {
                        System.out.println("error" + ignored);
                    }
                }
            }
        }
    }
}
