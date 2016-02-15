/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemConfigLauncher<T extends GvData> extends GridItemLauncher<T> {
    private static final String TAG = "GridItemConfigLauncher:";
    private final LaunchableConfig mDataConfig;
    private final ParcelablePacker<GvData> mPacker;
    private final int mLaunchCode;

    public GridItemConfigLauncher(LaunchableConfig dataConfig,
                                  FactoryReviewViewLaunchable launchableFactory,
                                  LaunchableUiLauncher launcher,
                                  ParcelablePacker<GvData> packer) {
        super(launchableFactory, launcher);
        mDataConfig = dataConfig;
        mPacker = packer;
        mLaunchCode = RequestCodeGenerator.getCode(TAG + mDataConfig.getTag());
    }

    @Override
    public void onLongClickExpandable(T item, int position, View v, ReviewViewAdapter<?>
            expanded) {
        GvData datum = item;
        //TODO get rid of this instanceof
        if (item instanceof GvCanonical) {
            GvCanonical canonical = (GvCanonical) item;
            datum = canonical.size() == 1 ? canonical.getItem(0) : canonical.getCanonical();
        }

        launchViewer(datum);
    }

    @Override
    public void onClickNotExpandable(T item, int position, View v) {
        launchViewer(item);
    }

    @Override
    public void onLongClickNotExpandable(T item, int position, View v) {
        onClickNotExpandable(item, position, v);
    }

    private void launchViewer(GvData item) {
        if (item.isVerboseCollection() || mDataConfig == null) return;
        Bundle args = new Bundle();
        mPacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, item, args);
        launch(mDataConfig.getLaunchable(), mLaunchCode, args);
    }
}
