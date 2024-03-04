package com.example.diplommobileapp.ui.custom_elements;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.models.division.Division;
import com.example.diplommobileapp.data.models.event.Event;
import com.example.diplommobileapp.services.imageworker.ImageUtils;

public class DivisionElement extends FrameLayout {
    private ImageView imageView;
    private TextView nameTv;

    private final Context context;
    Division division;
    public DivisionElement(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.event_view, this, true);
        imageView = findViewById(R.id.eventImageView);
        nameTv = findViewById(R.id.nameTv);
    }
    public void setData(Division division){
        this.division = division;

        byte[] preview = division.getPreviewImage();

        if (preview!=null)
            ImageUtils.setImageViewFromByteArray(preview, imageView);
        nameTv.setText(division.getName());
    }
}
