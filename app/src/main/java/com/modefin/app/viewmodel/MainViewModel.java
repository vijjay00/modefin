package com.modefin.app.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.modefin.app.api.ApiClient;
import com.modefin.app.api.ApiService;
import com.modefin.app.model.MakeupResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private Application application;
    private ArrayList<MakeupResponse> makeupResponses = new ArrayList<>();
    private MutableLiveData<List<MakeupResponse>> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public MutableLiveData<List<MakeupResponse>> getMakeUpList() {
        return mutableLiveData;
    }

    public void searchApi(String text) {
        Call<List<MakeupResponse>> call = ApiClient.getRetrofit().create(ApiService.class).getMakeupList(text);
        call.enqueue(new Callback<List<MakeupResponse>>() {
            @Override
            public void onResponse(Call<List<MakeupResponse>> call, Response<List<MakeupResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    makeupResponses = (ArrayList<MakeupResponse>) response.body();
                    mutableLiveData.setValue(makeupResponses);
                } else if(response.code() == 500) {
                    error.postValue("Internal server error");
                }
            }

            @Override
            public void onFailure(Call<List<MakeupResponse>> call, Throwable t) {
                error.postValue(t.getMessage());
                Log.d("ListSize", " - > Error    " + t.getMessage());

            }
        });
    }
}
