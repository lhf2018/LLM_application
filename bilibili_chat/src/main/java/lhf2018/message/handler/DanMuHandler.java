package lhf2018.message.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lhf2018.message.MessageRepo;
import lhf2018.model.DanMu;

public class DanMuHandler implements Handler {

    @Override
    public void handler(String msg) {
        DanMu danMu = JSON.parseObject(msg, new TypeReference<DanMu>() {});
        String content = danMu.getInfo().get(1);
        MessageRepo.MESSAGE_QUEUE.offer(content);
    }
}
