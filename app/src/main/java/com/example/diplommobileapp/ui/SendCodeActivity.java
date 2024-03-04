package com.example.diplommobileapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.models.auth.AuthModel;
import com.example.diplommobileapp.data.models.auth.AuthResponseModel;
import com.example.diplommobileapp.databinding.ActivitySendCodeBinding;
import com.example.diplommobileapp.services.httpwork.IApi;
import com.example.diplommobileapp.services.httpwork.RetrofitFactory;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendCodeActivity extends AppCompatActivity {
    ActivitySendCodeBinding binding;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySendCodeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        phoneNumber = getIntent().getStringExtra("phone");
        binding.sendBtn.setOnClickListener(this::saveOnClick);
    }

    private void saveOnClick(View view){
        AuthModel authModel = new AuthModel();
        authModel.setCode(binding.codeEt.getText().toString());
        authModel.setPhone(phoneNumber);

        IApi retrofit = RetrofitFactory.getApiService(this);
        retrofit.UpdatePhone(authModel).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast toast = new Toast(SendCodeActivity.this);
                    toast.setText("Номер изменен");
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }else editTextError();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                editTextError();
            }
        });
    }
    private void editTextError(){

        float translationX = 4f;
        ObjectAnimator shakeAnimator = ObjectAnimator.ofFloat(binding.codeEt, View.TRANSLATION_X, -translationX, translationX);
        shakeAnimator.setDuration(100);
        shakeAnimator.setRepeatCount(5);
        shakeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        shakeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.codeEt.setTranslationX(0f);
            }
        });
        shakeAnimator.start();

        binding.codeEt.setError("Код указан не верно");
    }
    private void showLoading(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.loading.setVisibility(View.VISIBLE);
            }
        });
    }
    private void endLoading(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.loading.setVisibility(View.GONE);
            }
        });
    }
    private void showFail(){
        Toast toast = new Toast(this);
        toast.setText(R.string.loading_error);
        toast.show();
    }
}