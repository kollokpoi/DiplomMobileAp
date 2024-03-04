package com.example.diplommobileapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.models.measure.MeasureDates;
import com.example.diplommobileapp.data.models.measure.MeasureDays;
import com.example.diplommobileapp.data.models.measure.MeasureDivisionsInfo;
import com.example.diplommobileapp.databinding.ActivityDivisionBinding;
import com.example.diplommobileapp.databinding.ActivityMeasureBinding;
import com.example.diplommobileapp.services.DateWorker;
import com.example.diplommobileapp.services.httpwork.IApi;
import com.example.diplommobileapp.services.httpwork.RetrofitFactory;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasureActivity extends AppCompatActivity {

    ActivityMeasureBinding binding;
    int measureInfoId = 0;
    String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeasureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        measureInfoId =  getIntent().getIntExtra("id",0);
        name =  getIntent().getStringExtra("name");
        showLoading();
        loadData();
    }
    private void loadData(){
        IApi retrofit = RetrofitFactory.getApiService(this);
        retrofit.GetMeasure(measureInfoId).enqueue(new Callback<MeasureDivisionsInfo>() {
            @Override
            public void onResponse(Call<MeasureDivisionsInfo> call, Response<MeasureDivisionsInfo> response) {

                if (response.isSuccessful()){
                    MeasureDivisionsInfo measureDivisionsInfo = response.body();
                    endLoading();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.nameTv.setText(name);
                            binding.datesTv.setText("Продолжительность: " + measureDivisionsInfo.getLength());
                            binding.placeTv.setText(measureDivisionsInfo.getPlace());

                            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );

                            if (measureDivisionsInfo.isWeekDays()){
                                for (MeasureDays day:measureDivisionsInfo.getMeasureDays()) {
                                    FrameLayout layout = new FrameLayout(MeasureActivity.this);
                                    layout.setLayoutParams(linearParams);

                                    TextView dateText = new TextView(MeasureActivity.this);
                                    TextView placeText = new TextView(MeasureActivity.this);

                                    String weekDay;
                                    switch (day.getDayNumber()){
                                        case 0: weekDay = "Понедельник";
                                            break;
                                        case 1: weekDay = "Вторник";
                                            break;
                                        case 2: weekDay = "Среда";
                                            break;
                                        case 3: weekDay = "Четверг";
                                            break;
                                        case 4: weekDay = "Пятница";
                                            break;
                                        case 5: weekDay = "Суббота";
                                            break;
                                        case 6: weekDay = "Воскресение";
                                            break;
                                        default: weekDay = "";
                                    }
                                    weekDay+=" " + day.getTimeSpan();

                                    dateText.setText(weekDay);
                                    placeText.setText(day.getPlace());

                                    dateText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                                    placeText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

                                    layout.addView(dateText);
                                    layout.addView(placeText);

                                    binding.shudleLL.addView(layout);
                                }
                            }else{
                                for (MeasureDates date:measureDivisionsInfo.getMeasureDates()) {
                                    FrameLayout layout = new FrameLayout(MeasureActivity.this);
                                    layout.setLayoutParams(linearParams);

                                    TextView dateText = new TextView(MeasureActivity.this);
                                    TextView placeText = new TextView(MeasureActivity.this);

                                    try {
                                        dateText.setText(DateWorker.getShortDate(date.getDatetime()));
                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }
                                    placeText.setText(date.getPlace());

                                    dateText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                                    placeText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

                                    layout.addView(dateText);
                                    layout.addView(placeText);

                                    binding.shudleLL.addView(layout);
                                }
                            }
                        }
                    });
                }
                else{
                    showFail();
                }
            }

            @Override
            public void onFailure(Call<MeasureDivisionsInfo> call, Throwable t) {
                showFail();
                finish();
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
    private void endLoading(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.loading.setVisibility(View.GONE);
                binding.holder.setVisibility(View.VISIBLE);
            }
        });
    }
    private void showFail(){
        Toast toast = new Toast(this);
        toast.setText(R.string.loading_error);
        toast.show();
    }
}