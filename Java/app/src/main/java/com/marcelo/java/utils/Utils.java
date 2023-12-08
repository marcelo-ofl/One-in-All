package com.marcelo.java.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.marcelo.java.R;


public class Utils {

    public static void setAnimation(Context context, int visibility, View view) {
        int slide;
        if (visibility == View.VISIBLE) {
            slide = R.anim.slide_up;
        } else {
            slide = R.anim.slide_low;
        }
        Animation animation = AnimationUtils.loadAnimation(context, slide);
        view.setVisibility(visibility);
        view.startAnimation(animation);
    }
}