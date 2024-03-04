package com.example.diplommobileapp.ui.custom_elements;

import static com.example.diplommobileapp.services.DateWorker.getShortDate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.models.event.Event;
import com.example.diplommobileapp.data.models.measure.Measure;
import com.example.diplommobileapp.data.viewModels.MeasureViewModel;
import com.example.diplommobileapp.services.imageworker.ImageUtils;
import com.example.diplommobileapp.ui.MeasureActivity;

import java.text.ParseException;

public class MeasureElement extends FrameLayout {
    private ImageView imageView;
    private TextView nameTv;
    private TextView timeTv;

    private final Context context;
    MeasureViewModel measure;

    public MeasureElement(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.measure_view, this, true);
        imageView = findViewById(R.id.eventImageView);
        nameTv = findViewById(R.id.nameTv);
        timeTv = findViewById(R.id.measureTimeTv);
        
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MeasureActivity.class);
                intent.putExtra("id",measure.getId());
                intent.putExtra("name",measure.getEventName());
                context.startActivity(intent);
            }
        });
    }

    public void setData(MeasureViewModel measure) throws ParseException {
        this.measure = measure;

        byte[] preview = null;
        String date = getShortDate(measure.getDatetime());

        if (preview!=null)
            ImageUtils.setImageViewFromByteArray(preview, imageView);
        nameTv.setText(measure.getEventName());
        timeTv.setText(date);

    }
}
