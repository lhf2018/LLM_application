项目来源https://github.com/THUDM/ChatGLM-6B ，看到最近LLM比较火，碰巧该模型最新发布，且可以消费显卡部署，好奇一下

仓库代码为了尝试本地部署，但我的显卡显存只有10GB（RTX3080），故量化为4BIT

分别使用命令行+HTTP请求调用该模型（大部分实现来自文档，但做了部分修改）

* CUDA版本12.1

后面会尝试使用该模型和一些其他功能联动，如B站直播间弹幕互动

### 更新记录（3月26）
接入B站弹幕
