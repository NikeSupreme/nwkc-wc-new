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
import com.example.st.SmartModel3Fragment;

public class Model3Fragment extends Fragment implements View.OnClickListener {
    private Button ivNext;
    private Button ivPre;
    private TextView tvSure;

    public static Model3Fragment newInstance() {
        Model3Fragment fragment = new Model3Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_model3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivNext = view.findViewById(R.id.iv_next);
        ivPre = view.findViewById(R.id.iv_pre);
        tvSure = view.findViewById(R.id.tv_sure);
        ivPre.setOnClickListener(this);
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

    private void navigateToModel4() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Model4Fragment fragment = Model4Fragment.newInstance();
        ft.replace(android.R.id.content, fragment, fragment.getClass().getName());
        ft.addToBackStack(null);
        ft.commit();
    }
    private void saveModel() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("modelNum", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("num", 3).apply();
    }

    private void navigateToPage() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        SmartModel3Fragment fragment = SmartModel3Fragment.newInstance();
        ft.replace(android.R.id.content, fragment, fragment.getClass().getName());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                saveModel();
                navigateToPage();
                break;
            case R.id.iv_next:
                navigateToModel4();
                break;
            case R.id.iv_pre:
                navigateToModel2();
                break;
        }

    }
}
