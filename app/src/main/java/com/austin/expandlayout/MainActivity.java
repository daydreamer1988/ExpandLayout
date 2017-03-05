package com.austin.expandlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ExpandLayout expandLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandLayout = (ExpandLayout) findViewById(R.id.expandLayout);

        expandLayout.getmExpandTextView().setText("　第一想法当然是用多个View组合来实现。那么久定义一个LinearLayout布局分别嵌套TextView和ImageView来做。 \n" +
                "　　大概步骤： \n" +
                "- 定义布局，垂直的线性LinearLayout布局、TextView和ImageView。 在layout中定义基本组件。 \n" +
                "- 设置TextView的高度为指定行数*行高。 不使用maxLine的原因是maxLine会控制显示文本的行数，不方便后边使用动画展开全部内容。因此这里TextView的高度也因该为wrap_content。 \n" +
                "- 给整个布局添加点击事件，绑定动画。 点击时，若TextView未展开则展开至其实际高度，imageView 旋转；否则回缩至 指定行数*行高 , imageView 旋转缩回。 \n" +
                "　　");

        expandLayout.setBackgroundResource(R.drawable.bg_error);


        ImageView imageView = expandLayout.getmIndicatorView();
        imageView.setImageResource(R.mipmap.ic_launcher);

    }
}
