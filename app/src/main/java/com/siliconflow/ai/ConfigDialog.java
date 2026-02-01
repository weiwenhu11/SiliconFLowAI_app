package com.siliconflow.ai;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ConfigDialog {
    private Context context;
    private ConfigManager configManager;
    private String[][] apiPresets;
    private AlertDialog dialog;
    
    private Spinner providerSpinner;
    private EditText apiUrlEditText;
    private EditText apiKeyEditText;
    private EditText modelEditText;
    private Button saveButton;
    private Button cancelButton;
    
    public ConfigDialog(Context context, ConfigManager configManager, String[][] apiPresets) {
        this.context = context;
        this.configManager = configManager;
        this.apiPresets = apiPresets;
    }
    
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.config_dialog, null);
        
        providerSpinner = view.findViewById(R.id.provider_spinner);
        apiUrlEditText = view.findViewById(R.id.api_url_edit);
        apiKeyEditText = view.findViewById(R.id.api_key_edit);
        modelEditText = view.findViewById(R.id.model_edit);
        saveButton = view.findViewById(R.id.save_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        
        setupSpinner();
        loadCurrentConfig();
        
        saveButton.setOnClickListener(v -> saveConfig());
        cancelButton.setOnClickListener(v -> dismiss());
        
        builder.setView(view);
        builder.setTitle("API配置");
        builder.setCancelable(false);
        
        dialog = builder.create();
        dialog.show();
    }
    
    private void setupSpinner() {
        List<String> providerNames = new ArrayList<>();
        for (String[] preset : apiPresets) {
            providerNames.add(preset[0]);
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            context, android.R.layout.simple_spinner_item, providerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        providerSpinner.setAdapter(adapter);
        
        providerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 当选择预设时，自动填充URL和模型
                apiUrlEditText.setText(apiPresets[position][1]);
                modelEditText.setText(apiPresets[position][2]);
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    
    private void loadCurrentConfig() {
        Config config = configManager.getConfig();
        if (config != null) {
            // 尝试找到对应的预设位置
            int position = 0;
            for (int i = 0; i < apiPresets.length; i++) {
                if (apiPresets[i][0].equals(config.getProviderName())) {
                    position = i;
                    break;
                }
            }
            providerSpinner.setSelection(position);
            apiUrlEditText.setText(config.getApiUrl());
            apiKeyEditText.setText(config.getApiKey());
            modelEditText.setText(config.getModel());
        } else {
            // 默认选择第一个预设
            providerSpinner.setSelection(0);
            apiUrlEditText.setText(apiPresets[0][1]);
            modelEditText.setText(apiPresets[0][2]);
        }
    }
    
    private void saveConfig() {
        String providerName = (String) providerSpinner.getSelectedItem();
        String apiUrl = apiUrlEditText.getText().toString().trim();
        String apiKey = apiKeyEditText.getText().toString().trim();
        String model = modelEditText.getText().toString().trim();
        
        if (apiKey.isEmpty()) {
            Toast.makeText(context, "API密钥不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (apiUrl.isEmpty()) {
            Toast.makeText(context, "API地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Config config = new Config(apiUrl, apiKey, model, providerName);
        configManager.saveConfig(config);
        
        Toast.makeText(context, "配置已保存", Toast.LENGTH_SHORT).show();
        dismiss();
    }
    
    private void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}