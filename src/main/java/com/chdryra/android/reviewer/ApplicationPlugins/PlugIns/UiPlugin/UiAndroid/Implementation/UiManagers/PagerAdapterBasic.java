/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Application.Implementation.Strings;

/**
 * Created by: Rizwan Choudrey
 * On: 08/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PagerAdapterBasic<T extends PagerAdapterBasic.PageableFragment> extends FragmentStatePagerAdapter {
    private final SparseArray<T> mFragments;

    public abstract String getId(int position);

    protected abstract T newFragmentInstance(int position);

    public PagerAdapterBasic(FragmentManager manager) {
        super(manager);
        mFragments = new SparseArray<>();
    }

    public String getTitle(PageableFragment fragment) {
        return newTitle(getItemPosition(fragment), getCount());
    }

    @NonNull
    protected String newTitle(int position, int count) {
        return Strings.Menu.PAGE + " " + String.valueOf(position + 1) + "/" + String.valueOf(count);
    }

    public void removeFragment(PageableFragment fragment) {
        int position = getItemPosition(fragment);
        mFragments.remove(position);
        onFragmentRemoved(position);
        notifyDataSetChanged();
    }

    protected void onFragmentRemoved(int position) {

    }


    @Nullable
    public T getFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        T fragment = getFragment(position);
        if (fragment == null) {
            fragment = newFragmentInstance(position);
            mFragments.put(position, fragment);
        }

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup group, int position, Object object) {
        super.destroyItem(group, position, object);
        mFragments.remove(position);
    }

    @Override
    public int getItemPosition(Object object) {
        PageableFragment fragment = (PageableFragment) object;
        int position = POSITION_NONE;
        int i;
        for (i = 0; i < getCount(); i++) {
            if (getId(i).equals(fragment.getPageId())) {
                position = i;
                break;
            }
        }

        return position;
    }

    protected SparseArray<T> getCachedFragments() {
        return mFragments;
    }

    public static abstract class PageableFragment extends Fragment {
        public abstract String getPageId();
    }
}
