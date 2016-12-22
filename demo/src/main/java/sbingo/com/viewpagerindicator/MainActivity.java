package sbingo.com.viewpagerindicator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sbingo.com.mylibrary.ViewPagerIndicator;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.item1)
    RadioButton item1;
    @BindView(R.id.item2)
    RadioButton item2;
    @BindView(R.id.item3)
    RadioButton item3;
    @BindView(R.id.item4)
    RadioButton item4;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.vp_indicator)
    ViewPagerIndicator vpIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    /**
     * 当前页码
     */
    private int currentIndex;

    private boolean fromRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    void initView() {
        initRadioGroup();
        initViewPager();
    }

    void initRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.item1:
                        if (viewPager.getCurrentItem() != 0) {
                            fromRadioGroup = true;
                            viewPager.setCurrentItem(0);
                        }
                        break;
                    case R.id.item2:
                        if (viewPager.getCurrentItem() != 1) {
                            fromRadioGroup = true;
                            viewPager.setCurrentItem(1);
                        }
                        break;
                    case R.id.item3:
                        if (viewPager.getCurrentItem() != 2) {
                            fromRadioGroup = true;
                            viewPager.setCurrentItem(2);
                        }
                        break;
                    case R.id.item4:
                        if (viewPager.getCurrentItem() != 3) {
                            fromRadioGroup = true;
                            viewPager.setCurrentItem(3);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    void initViewPager() {
        int[] colors = {Color.CYAN, Color.LTGRAY};
        vpIndicator.setPageCount(4);
        vpIndicator.setColors(colors);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), createFragments()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("onPageScrolled", "position =" + position + " \n offset =" + positionOffset);
                if (!fromRadioGroup) {
                    vpIndicator.drawPoint(position,positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("onPageSelected", String.valueOf(position));
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        Log.d("onPageScrollStateChanged", "SCROLL_STATE_DRAGGING");
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.d("onPageScrollStateChanged", "SCROLL_STATE_IDLE");
                        vpIndicator.setCurrentPageIndex(currentIndex);
                        fromRadioGroup = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        Log.d("onPageScrollStateChanged", "SCROLL_STATE_SETTLING");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private List<TestFragment> createFragments() {
        List<TestFragment> fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            TestFragment fragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TEXT", "内容" + (i + 1));
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        return fragments;
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        private List<TestFragment> fragments;

        public MyPagerAdapter(FragmentManager fm, List<TestFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


}
