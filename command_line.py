import os
import platform

import torch
from transformers import AutoTokenizer, AutoModel

os.environ['CURL_CA_BUNDLE'] = ''

tokenizer = AutoTokenizer.from_pretrained("THUDM/chatglm-6b-int4-qe", trust_remote_code=True)
model = AutoModel.from_pretrained("THUDM/chatglm-6b-int4-qe", trust_remote_code=True).half().cuda()
model = model.eval()

os_name = platform.system()
clear_command = 'cls' if os_name == 'Windows' else 'clear'


def build_prompt(history):
    prompt = "欢迎使用 ChatGLM-6B 模型，输入内容即可进行对话，clear 清空对话历史，stop 终止程序"
    for query, response in history:
        prompt += f"\n\n用户：{query}"
        prompt += f"\n\nChatGLM-6B：{response}"
    return prompt


def main():
    history = []
    print("欢迎使用 ChatGLM-6B 模型，输入内容即可进行对话，clear 清空对话历史，stop 终止程序")
    # 保持一直询问
    while True:
        query = input("\n用户：")
        if query == "stop":
            break
        if query == "clear":
            history = []
            os.system(clear_command)
            continue
        response, history = model.chat(tokenizer, query, history=history)
        print(f"\n\nChatGLM-6B：{response}")
        build_prompt(history)
        if len(history) >= 100:
            history = []
            torch.cuda.empty_cache()


if __name__ == "__main__":
    main()
