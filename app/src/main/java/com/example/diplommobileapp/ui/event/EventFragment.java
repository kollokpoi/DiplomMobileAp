package com.example.diplommobileapp.ui.event;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.diplommobileapp.BuildConfig;
import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.models.division.Division;
import com.example.diplommobileapp.data.models.event.Event;
import com.example.diplommobileapp.data.models.organization.Organization;
import com.example.diplommobileapp.data.viewModels.MeasureViewModel;

import com.example.diplommobileapp.databinding.FragmentEventBinding;
import com.example.diplommobileapp.services.DateWorker;
import com.example.diplommobileapp.services.httpwork.IApi;
import com.example.diplommobileapp.services.httpwork.RetrofitFactory;
import com.example.diplommobileapp.services.imageworker.ImageUtils;
import com.example.diplommobileapp.ui.*;
import com.example.diplommobileapp.ui.custom_elements.DivisionElement;
import com.example.diplommobileapp.ui.custom_elements.MeasureElement;
import com.example.diplommobileapp.ui.custom_elements.OrganizationElement;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {
    private ProgressBar loadingProgressBar;
    private LinearLayout holder;
    private FragmentEventBinding binding;
    int eventId = 0;
    private Event event;

    public static EventFragment newInstance(int eventId) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putInt("eventId",eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY);
        MapKitFactory.initialize(getContext());
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        eventId = getArguments().getInt("eventId", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadingProgressBar = binding.loading;
        holder = binding.holder;

        showLoading();
        if (event==null){
            IApi retrofit = RetrofitFactory.getApiService(getContext());
            retrofit.GetEvent(eventId).enqueue(new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    if (response.isSuccessful()){
                        event = response.body();
                        createUi();
                        endLoading();
                    }else{
                        showFail();
                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {
                    showFail();
                }
            });
        }

        return root;
    }

    private void createMap(){
        binding.mapView.getMap().move(
                new CameraPosition(
                        new Point(event.getDivisions().get(0).getLatitude(), event.getDivisions().get(0).getLongitude()),
                        15f, 0.0f, 0.0f
                ),
                new Animation(Animation.Type.SMOOTH, 0f),
                null
        );

        PlacemarkMapObject placemark = binding.mapView.getMap().getMapObjects().addPlacemark(
                new Point(event.getDivisions().get(0).getLatitude(), event.getDivisions().get(0).getLongitude()),
                ImageProvider.fromResource(getContext(), R.drawable.ic_pin)
        );

        placemark.setIconStyle(new IconStyle().setAnchor(new PointF(0.5f, 1.0f))
                .setScale(0.07f)
                .setZIndex(10f)
        );
    }
    private void createUi(){
        List<Integer> divisionsIds = new ArrayList<>();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                byte[] preview = event.getPreviewImage();

                if (preview!=null)
                    ImageUtils.setImageViewFromByteArray(preview, binding.eventImageView);
                binding.nameTv.setText(event.getName());
                binding.descriptionTv.setText(event.getDescription());
                String resultDate = "C ";;
                try {
                    resultDate += DateWorker.getShortDate(event.getDateOfStart());
                    if (event.getDateOfEnd()!=null){
                        resultDate += " по " + DateWorker.getShortDate(event.getDateOfEnd());
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                binding.datesTv.setText(resultDate);

                if (event.isDivisionsExist())
                    for (Division d:event.getDivisions()) {
                        DivisionElement el = new DivisionElement(getContext());
                        el.setData(d);
                        binding.divisionLL.addView(el);
                        divisionsIds.add(d.getId());
                        el.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getContext(), DivisionActivity.class);
                                intent.putExtra("id",d.getId());
                                getActivity().startActivity(intent);
                            }
                        });
                    }
                else{
                    binding.divisionLL.setVisibility(View.GONE);
                    binding.mapHolderLL.setVisibility(View.VISIBLE);
                    createMap();
                }

                for (Organization o:event.getOrganizations()) {
                    OrganizationElement el = new OrganizationElement(getContext());
                    try {
                        el.setData(o);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    binding.organizationsLL.addView(el);
                }
            }
        });

        IApi retrofit = RetrofitFactory.getApiService(getContext());
        retrofit.GetMeasuresForDivision(divisionsIds).enqueue(new Callback<List<MeasureViewModel>>() {
            @Override
            public void onResponse(Call<List<MeasureViewModel>> call, Response<List<MeasureViewModel>> response) {
                if (response.isSuccessful()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (MeasureViewModel vm: response.body()) {
                                MeasureElement el = new MeasureElement(getContext());
                                try {
                                    el.setData(vm);
                                    binding.measuresLL.addView(el);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }
                    });

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
}