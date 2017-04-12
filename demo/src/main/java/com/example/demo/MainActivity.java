package com.example.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rewechati_images_selector.ImagesSelectorActivity;
import com.example.rewechati_images_selector.SelectorSettings;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mResults = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = (Button) findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开图片多选
                Intent intent = new Intent(MainActivity.this, ImagesSelectorActivity.class);
                // 最大图片选择
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
                // min size of image which will be shown; to filter tiny images (mainly icons)
                intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                // 是否显示摄像头
                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, false);
                // pass current selected images as the initial value
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
//                 start the selector
                startActivity(intent);
            }
        });
    }
}
