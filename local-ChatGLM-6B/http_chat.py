import os

import datetime
import json
import torch
import uvicorn
from fastapi import FastAPI, Request
from transformers import AutoTokenizer, AutoModel

app = FastAPI()

os.environ['CURL_CA_BUNDLE'] = ''
tokenizer = AutoTokenizer.from_pretrained("THUDM/chatglm-6b-int4-qe", trust_remote_code=True)
model = AutoModel.from_pretrained("THUDM/chatglm-6b-int4-qe", trust_remote_code=True).half().cuda()
model.eval()

history = []


@app.post("/")
async def query(request: Request):
    global model, tokenizer, history
    json_post_raw = await request.json()
    json_post = json.dumps(json_post_raw)
    json_post_list = json.loads(json_post)
    prompt = json_post_list.get('prompt')

    response, history = model.chat(tokenizer, prompt, history=history)

    if len(history) >= 100:
        history = []
        torch.cuda.empty_cache()

    answer = {
        "response": response,
        "status": 200,
        "time": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    }
    return answer


if __name__ == '__main__':
    uvicorn.run('http_chat:app', host='127.0.0.1', port=8000, workers=1)
