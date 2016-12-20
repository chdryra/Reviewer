/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DatumLayoutView;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUpAppLevel;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.PagerAdapterBasic;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiUpAppLevel;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 20/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class FragmentViewData<T extends GvData> extends PagerAdapterBasic.PageableFragment {
    public static final String PAGE_ID = TagKeyGenerator.getKey(FragmentViewData.class,
            "PageId");
    public static final String INDEX = TagKeyGenerator.getKey(FragmentViewData.class,
            "index");

    private GvDataType<T> mDataType;
    private DatumLayoutView<T> mLayout;
    private MenuUi mMenu;
    private int mIndex;
    private String mPageId;

    public FragmentViewData(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    @Override
    public String getPageId() {
        return mPageId;
    }

    public int getIndex() {
        return mIndex;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args == null) throwNoReview();

        mPageId = args.getString(PAGE_ID);
        mIndex = args.getInt(INDEX);

        setMenu();
    }

    private void setMenu() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        UiSuite ui = app.getUi();
        MenuActionItem<GvData> upAction = new MaiUpAppLevel<>(app);
        mMenu = new MenuUi(new MenuUpAppLevel(mDataType.getDataName(), upAction, ui));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mLayout = newViewLayout();
        return mLayout.createLayoutUi(getActivity(), null);
    }

    public abstract DatumLayoutView<T> newViewLayout();

    private void throwNoReview() {
        throw new RuntimeException("No review found");
    }
}
