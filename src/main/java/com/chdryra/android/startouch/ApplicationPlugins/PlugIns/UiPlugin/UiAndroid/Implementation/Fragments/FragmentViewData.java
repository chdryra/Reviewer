/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityDataPager;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DatumLayoutView;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation.MenuUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation.MenuUpAppLevel;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation.NodeDataPagerAdapter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiUpAppLevel;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 20/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class FragmentViewData<DataType extends HasReviewId, T extends GvData, F extends
        FragmentViewData<DataType, T, F>>
        extends NodeDataPagerAdapter.DataFragment<DataType> {
    public static final String PAGE_ID = TagKeyGenerator.getKey(FragmentViewData.class,
            "PageId");
    public static final String INDEX = TagKeyGenerator.getKey(FragmentViewData.class,
            "index");

    private GvDataType<T> mDataType;
    private DatumLayoutView<T> mLayout;
    private MenuUi mMenu;
    private String mPageId;
    private DataConverter<DataType, T, ?> mConverter;

    public abstract DataConverter<DataType, T, ?> getConverter();

    public abstract DatumLayoutView<T> newView();

    public FragmentViewData(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    protected ActivityDataPager<DataType, F> getContainer() {
        try {
            return (ActivityDataPager<DataType, F>) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPageId() {
        return mPageId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args == null) {
            noReview();
            return;
        }

        mPageId = args.getString(PAGE_ID);

        setMenu();
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mConverter = getConverter();
        mLayout = newView();
        return mLayout.createLayoutUi(getActivity(), null);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mMenu.onItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onData(ReviewItemReference<DataType> data) {
        data.dereference(new DataReference.DereferenceCallback<DataType>() {
            @Override
            public void onDereferenced(DataValue<DataType> value) {
                if (value.hasValue()) mLayout.updateView(mConverter.convert(value.getData()));
            }
        });
    }

    private void setMenu() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        UiSuite ui = app.getUi();
        MenuActionItem<GvData> upAction = new MaiUpAppLevel<>(app);
        mMenu = new MenuUi(new MenuUpAppLevel(mDataType.getDataName(), upAction, ui));
    }

    private void noReview() {
        Toast.makeText(getActivity(), "No review found", Toast.LENGTH_SHORT).show();
    }
}
