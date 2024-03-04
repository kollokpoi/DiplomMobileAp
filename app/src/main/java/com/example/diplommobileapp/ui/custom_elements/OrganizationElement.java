package com.example.diplommobileapp.ui.custom_elements;

import static com.example.diplommobileapp.services.DateWorker.getShortDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.models.organization.Organization;
import com.example.diplommobileapp.data.viewModels.MeasureViewModel;
import com.example.diplommobileapp.services.imageworker.ImageUtils;

import java.text.ParseException;

public class OrganizationElement extends FrameLayout {
    private ImageView imageView;
    private TextView nameTv;
    private TextView timeTv;

    private final Context context;
    Organization organization;

    public OrganizationElement(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.event_view, this, true);
        imageView = findViewById(R.id.eventImageView);
        nameTv = findViewById(R.id.nameTv);
    }

    public void setData(Organization organization) throws ParseException {
        this.organization = organization;

        byte[] preview = organization.getPreview();

        if (preview!=null)
            ImageUtils.setImageViewFromByteArray(preview, imageView);
        nameTv.setText(organization.getName());
    }
}
