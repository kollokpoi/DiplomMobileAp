package com.example.diplommobileapp.services.httpwork;

import com.example.diplommobileapp.data.models.User;
import com.example.diplommobileapp.data.models.auth.AuthResponseModel;
import com.example.diplommobileapp.data.models.auth.AuthModel;
import com.example.diplommobileapp.data.models.division.Division;
import com.example.diplommobileapp.data.models.event.Event;
import com.example.diplommobileapp.data.models.measure.Measure;
import com.example.diplommobileapp.data.models.measure.MeasureDivisionsInfo;
import com.example.diplommobileapp.data.viewModels.MeasureViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApi {
    //Authorize
    @POST("User/CheckPhone")
    Call<AuthResponseModel> CheckPhone(@Body AuthModel model);
    @POST("User/LoginByCode")
    Call<Void> LoginByCode(@Body AuthModel model);
    @POST("User/ResendCode")
    Call<Void> ResendCode(@Body AuthModel model);
    @POST("User/CheckAuthorize")
    Call<Void> CheckAuthorize();

    //user
    @POST("User/GetUser")
    Call<User> getUser();
    @POST("User/UpdateUser")
    Call<Void> UpdateUser(@Body User model);
    @POST("User/UpdatePhone")
    Call<Void> UpdatePhone(@Body AuthModel model);
    //Events
    @GET("Events/GetEvents")
    Call<List<Event>> GetEvents();
    @GET("Events/GetEventsForUser")
    Call<List<Event>> GetEventsForUser();
    @GET("Events/GetEvent/{Id}")
    Call<Event> GetEvent(@Path("Id")int id);

    //divisions
    @GET("Divisions/GetDivisions")
    Call<List<Division>> GetDivisions();
    @GET("Divisions/GetDivisionForUser/{eventId}")
    Call<List<Division>> GetDivisionForUser(@Path("eventId")int eventId);
    @GET("Divisions/GetDivision/{Id}")
    Call<Division> GetDivision(@Path("Id")int id);

    //Measures
    @GET("Measures/GetMeasures")
    Call<List<Measure>> GetMeasures();
    @GET("Measures/GetMeasuresForUser")
    Call<List<MeasureViewModel>> GetMeasuresForUser();
    @POST("Measures/GetMeasuresDivision")
    Call<List<MeasureViewModel>> GetMeasuresForDivision(@Body List<Integer> divisions);
    @GET("Measures/GetMeasure/{Id}")
    Call<MeasureDivisionsInfo> GetMeasure(@Path("Id")int id);
}
