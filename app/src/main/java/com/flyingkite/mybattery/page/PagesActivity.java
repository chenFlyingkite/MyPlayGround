package com.flyingkite.mybattery.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.flyingkite.mybattery.BaseActivity;
import com.flyingkite.mybattery.R;
import com.flyingkite.mybattery.page.host.MyPagerAdapter;

public class PagesActivity extends BaseActivity {
    private TabLayout tabs;
    private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);

        PagerAdapter pa = new MyPagerAdapter(getFragmentManager());

        tabs = (TabLayout) findViewById(R.id.pageTabs);
        pager = (ViewPager) findViewById(R.id.pagePager);

        for (int i = 0; i < pa.getCount(); i++) {
            TabLayout.Tab t = tabs.newTab().setCustomView(R.layout.view_autotext);
            TextView txt = (TextView) t.getCustomView().findViewById(R.id.itsText);
            txt.setText(String.format("#%s : %s", i, MyPagerAdapter.PAGES[i].name()));
            tabs.addTab(t);
        }
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        pager.setAdapter(pa);
    }
}
