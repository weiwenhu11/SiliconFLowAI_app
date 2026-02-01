package com.siliconflow.ai;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // API预设配置：{显示名称, API URL, 默认模型}
    private static final String[][] API_PRESETS = {
        {"DeepSeek API", "https://api.deepseek.com/v1/chat/completions", "deepseek-chat"},
        {"OpenAI API", "https://api.openai.com/v1/chat/completions", "gpt-3.5-turbo"},
        {"阿里通义千问", "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions", "qwen-turbo"},
        {"百度文心一言", "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions", "ernie-bot-turbo"},
        {"硅基流官方", "https://api.siliconflow.cn/v1/chat/completions", "deepseek-ai/DeepSeek-R1"}
    };
    
    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private Button sendButton;
    private ImageButton configButton;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;
    
    private ConfigManager configManager;
    private ApiService apiService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 初始化UI
        initUI();
        
        // 初始化服务
        configManager = new ConfigManager(this);
        apiService = new ApiService();
        
        // 加载聊天历史
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
        
        // 检查配置
        checkConfig();
    }
    
    private void initUI() {
        // 初始化聊天列表
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // 初始化输入框和按钮
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        configButton = findViewById(R.id.config_button);
        
        // 设置发送按钮点击事件
        sendButton.setOnClickListener(v -> sendMessage());
        configButton.setOnClickListener(v -> showConfigDialog());
        
        // 回车发送
        messageInput.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && 
                event.getAction() == KeyEvent.ACTION_DOWN) {
                sendMessage();
                return true;
            }
            return false;
        });
    }
    
    private void checkConfig() {
        Config config = configManager.getConfig();
        if (config == null || config.getApiKey().isEmpty()) {
            showConfigDialog();
        }
    }
    
    private void sendMessage() {
        String message = messageInput.getText().toString().trim();
        if (message.isEmpty()) return;
        
        // 添加到聊天列表
        addMessageToChat("user", message);
        messageInput.setText("");
        
        // 调用AI API
        callAIAPI(message);
    }
    
    private void addMessageToChat(String sender, String message) {
        ChatMessage chatMessage = new ChatMessage(sender, message);
        chatMessages.add(chatMessage);
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
    }
    
    private void callAIAPI(String message) {
        // 显示加载状态
        addMessageToChat("ai", "思考中...");
        int loadingIndex = chatMessages.size() - 1;
        
        // 获取配置
        Config config = configManager.getConfig();
        if (config == null || config.getApiKey().isEmpty()) {
            chatMessages.remove(loadingIndex);
            chatAdapter.notifyItemRemoved(loadingIndex);
            showConfigDialog();
            return;
        }
        
        // 调用API
        apiService.chat(message, config, new ApiService.Callback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    chatMessages.set(loadingIndex, new ChatMessage("ai", response));
                    chatAdapter.notifyItemChanged(loadingIndex);
                });
            }
            
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    chatMessages.set(loadingIndex, new ChatMessage("ai", "错误: " + error));
                    chatAdapter.notifyItemChanged(loadingIndex);
                });
            }
        });
    }
    
    private void showConfigDialog() {
        ConfigDialog dialog = new ConfigDialog(this, configManager, API_PRESETS);
        dialog.show();
    }
}

class ChatMessage {
    private String sender;
    private String message;
    
    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
    
    public String getSender() { return sender; }
    public String getMessage() { return message; }
}