package com.example.diplommobileapp.ui.main.ui.home;

import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.models.event.Event;
import com.example.diplommobileapp.data.viewModels.MeasureViewModel;

import com.example.diplommobileapp.databinding.FragmentHomeBinding;
import com.example.diplommobileapp.services.ScreenUtils;
import com.example.diplommobileapp.services.httpwork.IApi;
import com.example.diplommobileapp.services.httpwork.RetrofitFactory;
import com.example.diplommobileapp.ui.custom_elements.EventElement;
import com.example.diplommobileapp.ui.custom_elements.MeasureElement;
import com.example.diplommobileapp.ui.login.LoginActivity;
import com.example.diplommobileapp.ui.main.MainActivity;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    private ProgressBar loadingProgressBar;
    private LinearLayout holder;
    private View underline;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        loadingProgressBar = binding.loading;
        holder = binding.holder;
        underline = binding.underLine;

        int screenWidth = ScreenUtils.getScreenWidth(getActivity());

        ViewGroup.LayoutParams params = underline.getLayoutParams();
        params.width = (screenWidth / 3);
        underline.setLayoutParams(params);

        showLoading();

        loadEvents();

        assert binding.eventsLinear != null;
        binding.eventsLinear.setOnClickListener(this::eventsLinearClick);
        assert binding.allEventsLinear != null;
        binding.allEventsLinear.setOnClickListener(this::allEventsLinearClick);
        assert binding.measuresLinear != null;
        binding.measuresLinear.setOnClickListener(this::measuresLinearClick);

        return root;
    }

    private void eventsLinearClick(View view){
        assert binding.eventsTv != null;
        binding.eventsTv.setTypeface(null, Typeface.BOLD);
        assert binding.measuresTv != null;
        binding.measuresTv.setTypeface(null, Typeface.NORMAL);
        assert binding.allEventsTv != null;
        binding.allEventsTv.setTypeface(null, Typeface.NORMAL);
        underlineAnimator((LinearLayout) view);
        loadEvents();
    }
    private void allEventsLinearClick(View view){
        assert binding.eventsTv != null;
        binding.eventsTv.setTypeface(null, Typeface.NORMAL);
        assert binding.measuresTv != null;
        binding.measuresTv.setTypeface(null, Typeface.NORMAL);
        assert binding.allEventsTv != null;
        binding.allEventsTv.setTypeface(null, Typeface.BOLD);
        underlineAnimator((LinearLayout) view);
        loadAllEvents();
    }
    private void measuresLinearClick(View view){
        assert binding.eventsTv != null;
        binding.eventsTv.setTypeface(null, Typeface.NORMAL);
        assert binding.measuresTv != null;
        binding.measuresTv.setTypeface(null, Typeface.BOLD);
        assert binding.allEventsTv != null;
        binding.allEventsTv.setTypeface(null, Typeface.NORMAL);
        underlineAnimator((LinearLayout) view);
        loadMeasures();
    }
    private void loadEvents(){
        IApi retrofit = RetrofitFactory.getApiService(getContext());
        retrofit.GetEventsForUser().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()){
                    createEventElements(response.body());
                    endLoading();
                }else{
                    showFail();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                showFail();
            }
        });
    }
    private void loadAllEvents(){
        IApi retrofit = RetrofitFactory.getApiService(getContext());
        retrofit.GetEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()){
                    createEventElements(response.body());
                    endLoading();
                }else{
                    showFail();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                showFail();
            }
        });
    }
    private void loadMeasures(){
        IApi retrofit = RetrofitFactory.getApiService(getContext());
        retrofit.GetMeasuresForUser().enqueue(new Callback<List<MeasureViewModel>>() {
            @Override
            public void onResponse(Call<List<MeasureViewModel>> call, Response<List<MeasureViewModel>> response) {
                if (response.isSuccessful()){
                    createMeasureElements(response.body());
                    endLoading();
                }else{
                    showFail();
                }
            }

            @Override
            public void onFailure(Call<List<MeasureViewModel>> call, Throwable t) {
                showFail();
            }
        });
    }
    private void createEventElements(List<Event> events){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                holder.removeAllViews();
                for (Event event:events) {
                    EventElement el = new EventElement(getContext());
                    el.setData(event);
                    el.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getActivity() instanceof MainActivity) {
                                ((MainActivity) getActivity()).openEventFragment(event.getId());
                            }
                        }
                    });
                    holder.addView(el);
                }
            }
        });
    }
    private void createMeasureElements(List<MeasureViewModel> measures){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                holder.removeAllViews();
                for (MeasureViewModel measure:measures) {
                    MeasureElement el = new MeasureElement(getContext());
                    try {
                        el.setData(measure);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    holder.addView(el);
                }
            }
        });
    }
    private void showLoading(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingProgressBar.setVisibility(View.VISIBLE);
                holder.setVisibility(View.GONE);
            }
        });
    }
    private void endLoading(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingProgressBar.setVisibility(View.GONE);
                holder.setVisibility(View.VISIBLE);
            }
        });
    }
    private void showFail(){
        Toast toast = new Toast(getContext());
        toast.setText(R.string.loading_error);
        toast.show();
    }

    void underlineAnimator(@NonNull LinearLayout layout){
        int[] location = new int[2];
        layout.getLocationOnScreen(location);

        int x = location[0];

        int[] originalLocation = new int[2];
        underline.getLocationOnScreen(originalLocation);
        int startX = originalLocation[0];

        ObjectAnimator animation = ObjectAnimator.ofFloat(underline, "translationX", startX, x);
        animation.setDuration(500);
        animation.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}