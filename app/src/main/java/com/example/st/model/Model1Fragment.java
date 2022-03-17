package com.example.st.model;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.st.R;
import com.example.st.SmartModel1Fragment;

public class Model1Fragment extends Fragment implements View.OnClickListener {
    private Button ivNext;
    private TextView tvSure;

    public static Model1Fragment newInstance() {
        Model1Fragment model1Fragment = new Model1Fragment();
        return model1Fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_model1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivNext = view.findViewById(R.id.iv_next);
        tvSure = view.findViewById(R.id.tv_sure);
        ivNext.setOnClickListener(this);
        tvSure.setOnClickListener(this);
    }

    private void navigateToModel2() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Model2Fragment fragment = Model2Fragment.newInstance();
        ft.replace(android.R.id.content, fragment, fragment.getClass().getName());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void navigateToPage() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        SmartModel1Fragment fragment = SmartModel1Fragment.newInstance();
        ft.replace(android.R.id.content, fragment, fragment.getClass().getName());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void saveModel() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("modelNum", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("num", 1).apply();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                saveModel();
                navigateToPage();
                break;
            case R.id.iv_next:
                navigateToModel2();
                break;
        }

    }
}
