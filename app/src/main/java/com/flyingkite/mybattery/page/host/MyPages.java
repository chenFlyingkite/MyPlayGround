package com.flyingkite.mybattery.page.host;

import android.app.Fragment;

import com.flyingkite.mybattery.page.ChartFragment;
import com.flyingkite.mybattery.page.JsonFragment;
import com.flyingkite.mybattery.page.TextsFragment;

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
