package com.example.bai1training.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.bai1training.R;


/**
 * Created by HungNX on 7/16/16.
 */
public class LoadingDialog extends LinearLayout {
    View loaderCircle;
    boolean stopAnimation = false;
    Animation rotation, animationOut;

    public LoadingDialog(Context context) {
        super(context);
        rotation = AnimationUtils.loadAnimation(context, R.anim.vtecom_loader_rotation_repeat);
        animationOut = AnimationUtils.loadAnimation(context, R.anim.vtecom_loader_out);
        animationOut.setAnimationListener(listener);
        inflate(context, R.layout.vtecom_dialog_loading, this);
        loaderCircle = findViewById(R.id.loader_circle);
        runAnimation();
    }

    public LoadingDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        rotation = AnimationUtils.loadAnimation(context, R.anim.vtecom_loader_rotation_repeat);
        animationOut = AnimationUtils.loadAnimation(context, R.anim.vtecom_loader_out);
        animationOut.setAnimationListener(listener);
        inflate(context, R.layout.vtecom_dialog_loading, this);
        loaderCircle = findViewById(R.id.loader_circle);
        runAnimation();
    }

    public LoadingDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rotation = AnimationUtils.loadAnimation(context, R.anim.vtecom_loader_rotation_repeat);
        animationOut = AnimationUtils.loadAnimation(context, R.anim.vtecom_loader_out);
        animationOut.setAnimationListener(listener);
        inflate(context, R.layout.vtecom_dialog_loading, this);
        loaderCircle = findViewById(R.id.loader_circle);
        runAnimation();
    }

    private void runAnimation() {
        loaderCircle.startAnimation(rotation);
    }

    Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            rotation.cancel();
            setVisibility(GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public void startAnimationOut() {
        startAnimation(animationOut);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE) {
            super.setVisibility(visibility);
            stopAnimation = false;
            runAnimation();
        } else {
            if (stopAnimation) {
                super.setVisibility(visibility);
            } else {
                stopAnimation = true;
                startAnimationOut();
            }
        }
    }
}
