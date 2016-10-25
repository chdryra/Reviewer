/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.HorizontalAdapter;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class HorizontalGridUi<T extends GvData> extends ViewUi<RecyclerView, GvDataList<T>>{
    private HorizontalAdapter<T,?> mAdapter;

    public HorizontalGridUi(Context context,
                            RecyclerView view,
                            ViewHolderFactory<?> viewholderFactory,
                            ValueGetter<GvDataList<T>> getter) {
        super(view, getter);
        mAdapter = new HorizontalAdapter<>(getValue(), viewholderFactory);
        getView().setHasFixedSize(true);
        getView().setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        getView().setAdapter(mAdapter);
    }

    public void update() {
        mAdapter.setData(getValue());
    }
}
