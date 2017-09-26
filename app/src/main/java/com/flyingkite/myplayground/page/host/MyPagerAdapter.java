package com.flyingkite.myplayground.page.host;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {
    public static final MyPages[] PAGES = {MyPages.JSON, MyPages.TEXTS, MyPages.CHART, MyPages.CHART, MyPages.NONE, MyPages.CHART};

    private static final String TAG = "MyPagerAdapter";
    private static final boolean DEBUG = false;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentPrimaryItem = null;

    public MyPagerAdapter(FragmentManager fm) {
        mFragmentManager = fm;
    }

    @Override
    public int getCount() {
        return PAGES.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment)object).getView() == view;
    }

    public Fragment getItem(int position) {
        return PAGES[position].getFragment();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final long itemId = getItemId(position);

        // Do we already have this fragment?
        String name = makeFragmentName(container.getId(), itemId);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            if (DEBUG) Log.v(TAG, "Attaching item #" + itemId + ": f=" + fragment);
            // No adding again since it already exists
//            mFragmentManager.beginTransaction()
//                    .replace(container.getId(), fragment, name)
//                    .commitAllowingStateLoss();
        } else {
            if (DEBUG) Log.v(TAG, "Adding item #" + itemId + ": f=" + fragment);
            fragment = getItem(position);
            mFragmentManager.beginTransaction()
                    .add(container.getId(), fragment, name)
                    .commitAllowingStateLoss();
        }
        if (fragment != mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (DEBUG) Log.v(TAG, "Detaching item #" + getItemId(position) + ": f=" + object
                + " v=" + ((Fragment)object).getView());
        mFragmentManager.beginTransaction()
                .remove((Fragment)object)
                .commitAllowingStateLoss();
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment)object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    public long getItemId(int position) {
        return position;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
