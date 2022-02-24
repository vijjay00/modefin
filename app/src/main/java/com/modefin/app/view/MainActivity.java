package com.modefin.app.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modefin.app.FuncsVars;
import com.modefin.app.R;
import com.modefin.app.model.MakeupResponse;
import com.modefin.app.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private MainViewModel mainViewModel;
    private MainAdapter mainAdapter;
    private ArrayList<MakeupResponse> makeupList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        makeupList = new ArrayList<>();
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        progressDialog = FuncsVars.showDialog(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainAdapter = new MainAdapter(this, makeupList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mainAdapter);

        showProgress();
        mainViewModel.getList();
        mainViewModel.getMakeUpList().observe(this, new Observer<List<MakeupResponse>>() {
            @Override
            public void onChanged(List<MakeupResponse> makeup) {
                hideProgress();
                if(makeup.size()>0) {
                    updateRecyclerview(makeup);
                } else {
                    if(makeupList.size()>0) {
                        makeupList.clear();
                        mainAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(getApplicationContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                }

            }
        });
        mainViewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideProgress();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showProgress();
                mainViewModel.searchApi(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setQuery("", false);
                return false;
            }
        });
    }

    private void showProgress() {
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    private void hideProgress() {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    private void updateRecyclerview(List<MakeupResponse> updateList) {
        makeupList.clear();
        makeupList.addAll(updateList);
        mainAdapter.notifyDataSetChanged();
    }
}