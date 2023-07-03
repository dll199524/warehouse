package com.example.customview.view.colortrack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.customview.R;



public class ItemFragment extends Fragment {

    public static ItemFragment newInstance(String item) {
        ItemFragment instance = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", item);
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, null);
        TextView tv = view.findViewById(R.id.tv_item);
        Bundle bundle = getArguments();
        tv.setText(bundle.getString("title"));
        return view;
    }
}