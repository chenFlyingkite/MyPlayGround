package com.flyingkite.myplayground.page.host;

import android.app.Fragment;

import com.flyingkite.myplayground.page.ChartFragment;
import com.flyingkite.myplayground.page.JsonFragment;
import com.flyingkite.myplayground.page.TextsFragment;

public enum MyPages {
    CHART,
    TEXTS,
    JSON,
    NONE;

    public Fragment getFragment() {
        switch (this) {
            case JSON:
                return new JsonFragment();
            case CHART:
                return new ChartFragment();
            case TEXTS:
                return new TextsFragment();
            default:
            case NONE:
                return new BaseFragment();
        }
    }
}
