package com.example.customview.view.colortrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.customview.databinding.ActivityColorTrackBinding;

import java.util.ArrayList;
import java.util.List;

public class ColorTrackActivity extends AppCompatActivity {
    ActivityColorTrackBinding binding;
    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private final String TAG = this.getClass().getSimpleName();
    private List<ColorTrackTextView> indicators = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityColorTrackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initIndicator();
        initViewPager();
    }

    private void initIndicator() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        for (int i = 0; i < items.length; i++) {
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            colorTrackTextView.setTextSize(20);
            binding.ll.addView(colorTrackTextView);
            indicators.add(colorTrackTextView);
        }
    }

    private void initViewPager() {
        binding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ColorTrackTextView left = indicators.get(position);
                left.setDirection(ColorTrackTextView.Direction.DIRECTION_LEFT);
                left.setProgress(1 - positionOffset);

                try {
                    ColorTrackTextView right = indicators.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.DIRECTION_RIGHT);
                    right.setProgress(positionOffset);
                } catch (Exception e) {e.printStackTrace();}
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}