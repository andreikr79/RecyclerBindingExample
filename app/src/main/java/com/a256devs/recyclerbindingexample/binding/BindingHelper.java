package com.a256devs.recyclerbindingexample.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.a256devs.recyclerbindingexample.R;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingHelper {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mm a", Locale.ENGLISH);

    public static String showDate(Date date) {
        return dateFormat.format(date);
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Context context = view.getContext();
        if(url!=null&&!url.isEmpty()) {
            Glide.with(context).load(url).into(view);
        } else {
            Glide.with(context).load(R.drawable.ic_placeholder).into(view);
        }
    }

    @BindingAdapter("app:visibility")
    public static void setVisibility(View view, int visibility) {
        view.setVisibility(visibility);
    }
}
