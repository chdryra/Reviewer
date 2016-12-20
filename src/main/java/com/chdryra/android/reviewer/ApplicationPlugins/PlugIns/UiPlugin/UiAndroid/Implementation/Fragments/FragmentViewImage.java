/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.os.Bundle;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation.ViewLayoutImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DatumLayoutView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 20/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FragmentViewImage extends FragmentViewData<GvImage> {
    public static FragmentViewImage newInstance(String reviewId, int index) {
        Bundle args = new Bundle();
        args.putString(PAGE_ID, reviewId);
        args.putInt(INDEX, index);
        FragmentViewImage fragment = new FragmentViewImage();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentViewImage() {
        super(GvImage.TYPE);
    }

    @Override
    public DatumLayoutView<GvImage> newViewLayout() {
        return new ViewLayoutImage();
    }
}
