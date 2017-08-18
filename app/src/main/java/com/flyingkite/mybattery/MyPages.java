package com.flyingkite.mybattery;

import android.app.Fragment;

public enum MyPages {
    CHART,
    TEXTS,
    NONE;

    public Fragment getFragment() {
        switch (this) {
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
