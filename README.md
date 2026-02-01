# 硅胶流AI聊天机器人 - 原生Android应用

## 📱 应用概述
一个完整的原生Android聊天机器人应用，支持多个AI服务商API，所有端点URL已正确预填，只需输入API密钥即可使用。

## ✨ 核心特性
✅ **原生Android应用** - 无需Web服务器，直接运行在Android设备上  
✅ **所有API URL已预填** - 5个主流AI服务商端点已内置  
✅ **只需输入API密钥** - 零配置即可开始聊天  
✅ **现代化UI设计** - Material Design界面，响应式布局  
✅ **离线配置保存** - 使用SharedPreferences持久化配置  
✅ **实时API调用** - 使用OkHttp进行网络请求  
✅ **完整的错误处理** - 网络错误、API错误友好提示  

## 🔧 支持的AI服务商
1. **🟢 DeepSeek API** - `https://api.deepseek.com/v1/chat/completions`
2. **🟣 OpenAI API** - `https://api.openai.com/v1/chat/completions`
3. **🟡 阿里通义千问** - `https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions`
4. **🔵 百度文心一言** - `https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions`
5. **🔴 硅胶流官方** - `https://api.siliconflow.cn/v1/chat/completions`

## 📁 项目结构
```
SiliconFlowAI_App/
├── app/
│   ├── src/main/
│   │   ├── java/com/siliconflow/ai/
│   │   │   ├── MainActivity.java      # 主活动
│   │   │   ├── ChatAdapter.java       # 聊天适配器
│   │   │   ├── ConfigManager.java     # 配置管理
│   │   │   ├── Config.java            # 配置实体
│   │   │   ├── ApiService.java        # API服务
│   │   │   └── ConfigDialog.java      # 配置对话框
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml      # 主布局
│   │   │   │   ├── chat_message_item.xml  # 聊天项布局
│   │   │   │   └── config_dialog.xml      # 配置对话框
│   │   │   ├── values/
│   │   │   │   ├── strings.xml
│   │   │   │   └── colors.xml
│   │   └── AndroidManifest.xml
├── build.gradle                       # 应用构建配置
└── README.md
```

## 🚀 快速开始

### 环境要求
- Android Studio Arctic Fox 或更高版本
- JDK 11 或更高版本
- Android SDK API 21 或更高版本
- 有效的AI API密钥

### 构建步骤
1. **克隆项目**
   ```bash
   git clone https://github.com/weiwenhu11/SiliconFLowAI_app.git
   ```

2. **在Android Studio中打开项目**
   - 选择 `File` → `Open` → 选择项目文件夹

3. **构建并运行**
   - 连接Android设备或启动模拟器
   - 点击运行按钮（▶️）
   - 应用将自动安装并启动

## 📱 使用流程

### 首次使用
1. **启动应用** - 打开"硅基流AI助手"
2. **配置API** - 点击右上角配置按钮（⚙️）
3. **选择服务商** - 从下拉列表中选择（URL已预填）
4. **输入密钥** - 输入您的API密钥
5. **保存配置** - 点击保存，配置将永久保存

### 开始聊天
1. **进入聊天界面** - 配置完成后自动进入
2. **输入消息** - 在底部输入框中输入问题
3. **发送消息** - 点击发送按钮或按回车键
4. **查看回复** - AI回复将显示在聊天列表中

## 🔧 API配置说明

### 获取API密钥
1. **硅基流动**：访问 [siliconflow.cn](https://siliconflow.cn/) 注册并获取API密钥
2. **DeepSeek**：访问 [deepseek.com](https://platform.deepseek.com/) 注册并获取API密钥
3. **其他服务商**：访问相应官网注册获取

### 配置示例
- **服务商**：硅基流官方
- **API地址**：`https://api.siliconflow.cn/v1/chat/completions`
- **API密钥**：`sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`
- **模型名称**：`deepseek-ai/DeepSeek-R1`

## 📱 功能截图
（应用截图示例位置）

## 🔄 更新日志

### v1.0.0
- 初始版本发布
- 支持5个主流AI服务商
- 完整的聊天界面和配置管理
- 离线配置保存功能

## 🤝 贡献
欢迎提交Issue和Pull Request来改进项目！

## 📄 许可证
本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 📞 联系
- GitHub: [@weiwenhu11](https://github.com/weiwenhu11)
- 项目地址: [https://github.com/weiwenhu11/SiliconFLowAI_app](https://github.com/weiwenhu11/SiliconFLowAI_app)