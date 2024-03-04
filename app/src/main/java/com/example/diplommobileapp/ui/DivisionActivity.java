package com.example.diplommobileapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.diplommobileapp.BuildConfig;
import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.models.division.Division;
import com.example.diplommobileapp.data.viewModels.MeasureViewModel;
import com.example.diplommobileapp.databinding.ActivityDivisionBinding;
import com.example.diplommobileapp.services.DateWorker;
import com.example.diplommobileapp.services.httpwork.IApi;
import com.example.diplommobileapp.services.httpwork.RetrofitFactory;
import com.example.diplommobileapp.services.imageworker.ImageUtils;
import com.example.diplommobileapp.ui.custom_elements.MeasureElement;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DivisionActivity extends AppCompatActivity {
    ActivityDivisionBinding binding;
    int divisionId;
    Division division;
    MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDivisionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapView = binding.mapView;

        divisionId = getIntent().getIntExtra("id", 0);
        showLoading();
        loadData();
    }
    private void loadData(){
        List<Integer> idList = new ArrayList<>();
        idList.add(divisionId);
        IApi retrofit = RetrofitFactory.getApiService(this);
        retrofit.GetDivision(divisionId).enqueue(new Callback<Division>() {
            @Override
            public void onResponse(Call<Division> call, Response<Division> response) {
                if (response.isSuccessful()) {
                    endLoading();
                    division = response.body();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                createUi();
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Division> call, Throwable t) {
                endLoading();
                showFail();
            }
        });

        IApi measuresRetrofit = RetrofitFactory.getApiService(this);
        measuresRetrofit.GetMeasuresForDivision(idList).enqueue(new Callback<List<MeasureViewModel>>() {
            @Override
            public void onResponse(Call<List<MeasureViewModel>> call, Response<List<MeasureViewModel>> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (MeasureViewModel vm:response.body()) {
                                MeasureElement el = new MeasureElement(DivisionActivity.this);
                                try {
                                    el.setData(vm);
                                    binding.measuresLL.addView(el);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                } else {
                    showFail();
                }
            }

            @Override
            public void onFailure(Call<List<MeasureViewModel>> call, Throwable t) {
                showFail();
            }
        });
    }
    private void createUi() throws ParseException {
        byte[] preview = division.getPreviewImage();
        if (preview != null) ImageUtils.setImageViewFromByteArray(preview, binding.eventImageView);
        String resultDate = "C ";
        resultDate += DateWorker.getShortDate(division.getDateOfStart());
        if (division.getDateOfEnd() != null) {
            resultDate += " по " + DateWorker.getShortDate(division.getDateOfEnd());
        }

        binding.datesTv.setText(resultDate);
        binding.nameTv.setText(division.getName());
        binding.descriptionTv.setText(division.getDescription());
        binding.placeNameTv.setText(division.getPlaceName());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            createMap();
        }
    }
    private void createMap(){
        mapView.getMap().move(
                new CameraPosition(
                        new Point(division.getLatitude(),division.getLongitude()), 15f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0f),
                null
        );

        PlacemarkMapObject placemark = mapView.getMap().getMapObjects().addPlacemark();
        placemark.setGeometry(new Point(division.getLatitude(),division.getLongitude()));
        placemark.setIcon(ImageProvider.fromResource(this, R.drawable.ic_pin));
        IconStyle iconStyle = new IconStyle(new PointF(0.5f, 1.0f), RotationType.NO_ROTATION,10f,false,true,0.07f,null);
        placemark.setIconStyle(iconStyle);
    }
    private void endLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.loading.setVisibility(View.GONE);
                binding.holder.setVisibility(View.VISIBLE);
            }
        });
    }
    private void showLoading(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.loading.setVisibility(View.VISIBLE);
                binding.holder.setVisibility(View.GONE);
            }
        });
    }
    private void showFail() {
        Toast toast = new Toast(this);
        toast.setText(R.string.loading_error);
        toast.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        binding.mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }
    @Override
    protected void onStop() {
        binding.mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}