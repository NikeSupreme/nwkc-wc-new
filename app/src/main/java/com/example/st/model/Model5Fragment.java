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
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.st.R;
import com.example.st.SmartModel5Fragment;

public class Model5Fragment extends Fragment implements View.OnClickListener {
    private Button ivPre;
    private TextView tvSure;

    public static Model5Fragment newInstance() {
        Model5Fragment fragment = new Model5Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_model5, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivPre = view.findViewById(R.id.iv_pre);
        tvSure = view.findViewById(R.id.tv_sure);
        ivPre.setOnClickListener(this);
        tvSure.setOnClickListener(this);
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
        sharedPreferences.edit().putInt("num", 5).apply();
    }

    private void navigateToPage() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        SmartModel5Fragment fragment = SmartModel5Fragment.newInstance();
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
            case R.id.iv_pre:
                navigateToModel4();
                break;
        }

    }
}
