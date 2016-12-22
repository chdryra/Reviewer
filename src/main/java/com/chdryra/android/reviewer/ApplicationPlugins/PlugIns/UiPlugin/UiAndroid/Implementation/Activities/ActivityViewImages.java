/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation.ViewLayoutImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DatumLayoutView;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentViewData;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityViewImages extends ActivityDataPager<DataImage, ActivityViewImages.FragmentViewImage> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected RefDataList<DataImage> getData(ReviewNode node) {
        return node.getImages();
    }

    @Override
    public FragmentViewImage newFragment(String pageId) {
        return FragmentViewImage.newInstance(pageId);
    }

    public static class FragmentViewImage extends FragmentViewData<DataImage, GvImage, FragmentViewImage> {
        public static FragmentViewImage newInstance(String pageId) {
            Bundle args = new Bundle();
            args.putString(PAGE_ID, pageId);
            FragmentViewImage fragment = new FragmentViewImage();
            fragment.setArguments(args);
            return fragment;
        }

        public FragmentViewImage() {
            super(GvImage.TYPE);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public DatumLayoutView<GvImage> newViewLayout() {
            return new ViewLayoutImage();
        }

        @Override
        public DataConverter<DataImage, GvImage, ?> getConverter() {
            return AppInstanceAndroid.getInstance(getActivity()).getUi().getGvConverter().newConverterImages();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = super.onCreateView(inflater, container, savedInstanceState);
            getContainer().onFragmentReady(this);
            return v;
        }
    }
}
