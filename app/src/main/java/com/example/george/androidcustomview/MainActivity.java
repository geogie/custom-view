package com.example.george.androidcustomview;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private PullDrawable mPullDrawable;
    private ValueAnimator mPullValueAnimator;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mImageView = (ImageView) findViewById(R.id.image_view);
        mPullDrawable = new PullDrawable();// 初始化
        mImageView.setImageDrawable(mPullDrawable);

        Button btn1 = (Button) findViewById(R.id.btn_anim);
        btn1.setOnClickListener(this);
        Button btn2 = (Button) findViewById(R.id.btn_clear);
        btn2.setOnClickListener(this);
        SeekBar seekBar = (SeekBar) findViewById(R.id.sb);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_anim:
                startPullAnim();
                break;
            case R.id.btn_clear:
                clear();
                break;
            default:
                break;
        }
    }

    private void clear() {
        if (mPullValueAnimator != null && mPullValueAnimator.isRunning()) {
            mPullValueAnimator.cancel();
        }
        mPullDrawable.clear();
    }

    private void startPullAnim() {
        if (mPullValueAnimator == null) {
            mPullValueAnimator = ValueAnimator.ofFloat(0, 1);
            mPullValueAnimator.setInterpolator(new LinearInterpolator());
            mPullValueAnimator.setDuration(4000);
            mPullValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    mPullDrawable.update(value);// 更新drawable
                }
            });
        } else {
            clear();
        }
        mPullValueAnimator.start();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mPullDrawable.update(progress / 100f);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}