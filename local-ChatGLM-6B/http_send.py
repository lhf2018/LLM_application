import requests

url = 'http://127.0.0.1:8000/'
# 以字典的形式构造数据
data = '{"prompt": "你好,请告诉我去北京一天有多少趟高铁"}'
# 与 get 请求一样，r 为响应对象
requests.get(url)

r = requests.post(url, data=data.encode("utf-8"))
# 查看响应结果
print(r.json())
