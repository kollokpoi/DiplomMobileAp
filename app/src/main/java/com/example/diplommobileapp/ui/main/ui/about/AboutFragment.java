package com.example.diplommobileapp.ui.main.ui.about;

import static android.app.Activity.RESULT_OK;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.models.User;
import com.example.diplommobileapp.databinding.FragmentAboutBinding;
import com.example.diplommobileapp.services.DateWorker;
import com.example.diplommobileapp.services.httpwork.IApi;
import com.example.diplommobileapp.services.httpwork.RetrofitFactory;
import com.example.diplommobileapp.services.imageworker.ImageUtils;
import com.example.diplommobileapp.ui.SendCodeActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutFragment extends Fragment {

    private FragmentAboutBinding binding;
    private User user;
    private static final int OPEN_FILE = 300;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getData();
        binding.imageView.setOnClickListener(this::updateImageClick);
        binding.saveBtn.setOnClickListener(this::saveChangesClick);
        return root;
    }

    void getData(){
        IApi retrofit = RetrofitFactory.getApiService(getContext());
        retrofit.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = response.body();
                    byte[] image = user.getImage();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (image!=null)
                                ImageUtils.setImageViewFromByteArray(image,binding.imageView);
                            binding.nameEt.setText(user.getFullName());
                            try {
                                binding.birthdayEt.setText(DateWorker.getFullDate(user.getBirthDay()));
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            binding.courseEt.setText(String.valueOf(user.getCourse()));
                            binding.numberEt.setText(user.getPhoneNumber());
                        }
                    });
                    endLoading();
                }else{
                    showFail();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showFail();
            }
        });
    }
    private void saveChangesClick(View view){
        if (user.setFullName(binding.nameEt.getText().toString())){
            if (user.setPhoneNumber(binding.numberEt.getText().toString())){
                if (user.setBirthDay(binding.birthdayEt.getText().toString())){
                    user.setCourse(binding.courseEt.getText().toString());
                    IApi retrofit = RetrofitFactory.getApiService(getContext());
                    retrofit.UpdateUser(user).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()){
                                if (user.isPhoneUpdated()){
                                    Intent intent = new Intent(getContext(),SendCodeActivity.class);
                                    intent.putExtra("phone",user.getPhoneNumber());
                                    getContext().startActivity(intent);
                                } else{
                                    Toast toast = new Toast(getContext());
                                    toast.setText("Успешно");
                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }else{
                    editTextError(binding.birthdayEt,"Укажите дату");
                }
            }else{
                editTextError(binding.numberEt,"Укажите номер");
            }
        }else{
            editTextError(binding.nameEt,"Укажите ФИО");
        }

    }
    private void updateImageClick(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_FILE);
    }
    private void editTextError(EditText editText, String text){

        float translationX = 4f;
        ObjectAnimator shakeAnimator = ObjectAnimator.ofFloat(editText, View.TRANSLATION_X, -translationX, translationX);
        shakeAnimator.setDuration(100);
        shakeAnimator.setRepeatCount(5);
        shakeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        shakeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                editText.setTranslationX(0f);
            }
        });
        shakeAnimator.start();

        editText.setError(text);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode == OPEN_FILE){

            }
                Uri ChosefileUri = data.getData();
                if(ChosefileUri !=null){
                    Uri fileUri = ChosefileUri;
                    if(fileUri != null)
                    {
                        binding.imageView.setImageURI(fileUri);
                        InputStream inputStream = null;
                        try {
                            inputStream = getContext().getContentResolver().openInputStream(fileUri);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        user.setImageBase64(Base64.encodeToString(byteArray, Base64.DEFAULT));
                    }
                }
            }
        }
    private void endLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.loading.setVisibility(View.GONE);
                binding.holder.setVisibility(View.VISIBLE);
            }
        });
    }
    private void showLoading(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.loading.setVisibility(View.VISIBLE);
                binding.holder.setVisibility(View.GONE);
            }
        });
    }
    private void showFail() {
        Toast toast = new Toast(getContext());
        toast.setText(R.string.loading_error);
        toast.show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}