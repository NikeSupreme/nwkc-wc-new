package com.example.st;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.example.st.model.Model1Fragment;
import com.example.st.util.Global;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private static String ARG_PARAM = "param_key";
    public static final String TAG = "LoginFragment";
    private EditText etIp;
    private EditText etId;
    private Button tvLogin;

    public static LoginFragment newInstance() {
        Bundle bundle = new Bundle();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        initData();

    }

    private void initData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("accountInfo", Context.MODE_PRIVATE);
//        String ip = sharedPreferences.getString("ip", "");
//        String id = sharedPreferences.getString("id", "");
//        etId.setText(id);
//        etIp.setText(ip);
    }

    private void initListener() {
        tvLogin.setOnClickListener(this);
    }

    private void initView(View root) {
        etId = root.findViewById(R.id.et_id);
        etIp = root.findViewById(R.id.et_ip);
        tvLogin = root.findViewById(R.id.tv_login);

    }

    private void saveIPAndID(String ip, String id) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("accountInfo", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("ip", ip).putString("id", id).apply();
    }

    private void navigateToModel1() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Model1Fragment fragment = Model1Fragment.newInstance();
        ft.replace(android.R.id.content, fragment, fragment.getClass().getName());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                WlinkSDKManger.getInstance().gw_LoginAndBindLan("123", etIp.getText().toString(), etId.getText().toString(), "", new WlinkSDKManger.Callback() {
                    @Override
                    public void handle(Object Obj) {
                        Log.i(TAG, "handle: " + JSON.toJSONString(Obj));
                        int res = (int) Obj;
                        if (res == 0) {
                            Global.gwId = etId.getText().toString();
                            Log.i(TAG, "局域网登录成功");
                            saveIPAndID(etIp.getText().toString(), etId.getText().toString());
                            navigateToModel1();
                        } else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "连接网关失败，请检查网络配置或重试", Toast.LENGTH_LONG).show();
                                }
                            });
                            Log.i(TAG, "局域网登录失败");
                        }
                    }

                    @Override
                    public void error(String errorCode, String msg) {
                        Log.i(TAG, "error: ");
                    }
                });
                break;
        }

    }
}
