package sbingo.com.viewpagerindicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: Sbingo
 * Date:   2016/12/22
 */

public class TestFragment extends Fragment {

    @BindView(R.id.content)
    TextView content;

    private View mFragmentView;

    String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(R.layout.test_layout, container, false);
            ButterKnife.bind(this, mFragmentView);
            if (getArguments() != null) {
                text = getArguments().getString("TEXT", "默认内容");
            }
            content.setText(text);
        }
        return mFragmentView;
    }
}
