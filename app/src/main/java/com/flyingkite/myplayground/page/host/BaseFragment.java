package com.flyingkite.myplayground.page.host;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyingkite.util.Say;

public class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Say.LogF("item onCreateView : " + this.getClass().getSimpleName() + ", " + this);
        int pageId = getPageLayoutId();
        if (pageId > 0) {
            return inflater.inflate(pageId, container, false);
        } else {
            return null;
        }
    }

    @LayoutRes
    protected int getPageLayoutId() {
        return -1;
    }

    public View findViewById(@IdRes int id) {
        if (getView() != null) {
            View v = getView().findViewById(id);
            if (v != null) {
                return v;
            }
        }

        return getActivity().getWindow().findViewById(id);
    }
}
