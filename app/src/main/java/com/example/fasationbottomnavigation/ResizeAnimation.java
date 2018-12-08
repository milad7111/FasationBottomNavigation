package com.example.fasationbottomnavigation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ResizeAnimation extends Animation {

    View view;

    final int startWidth;
    final int targetWidth;

    final int startHeight;
    final int targetHeight;

    public ResizeAnimation(View view, int targetWidth, int targetHeight) {
        this.view = view;

        this.startWidth = view.getWidth();
        this.startHeight = view.getHeight();

        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        int newWidth = (int) (startWidth + (targetWidth - startWidth) * interpolatedTime);
        int newHeight = (int) (startHeight + (targetHeight - startHeight) * interpolatedTime);

        view.getLayoutParams().width = newWidth;
        view.getLayoutParams().height = newHeight;

        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
