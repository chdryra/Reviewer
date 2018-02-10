/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
class FactoryFragment {
    private static final String TAG = TagKeyGenerator.getTag(FactoryFragment.class);

    public static <T extends Fragment, P extends Parcelable> T newFragment(Class<T> fragmentClass,
                                                                           String key, @Nullable P data) {
        T fragment = newInstance(fragmentClass);

        Bundle args = new Bundle();
        args.putParcelable(key, data);
        fragment.setArguments(args);

        return fragment;
    }

    private static <T extends Fragment>  T newInstance(Class<T> fragmentClass) {
        try {
            return fragmentClass.newInstance();
        } catch (InstantiationException e) {
            Log.e(TAG, "Couldn't create fragment for " + fragmentClass.getName(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException: trying to create " + fragmentClass.getName(), e);
            throw new RuntimeException(e);
        }
    }
}
