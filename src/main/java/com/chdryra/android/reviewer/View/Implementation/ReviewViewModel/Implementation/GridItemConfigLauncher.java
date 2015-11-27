package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonical;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories
        .FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemConfigLauncher<T extends GvData> extends GridItemLauncher<T> {
    private LaunchableConfig mDataConfig;
    private GvDataPacker<GvData> mPacker;

    public GridItemConfigLauncher(LaunchableConfig dataConfig,
                                  FactoryReviewViewLaunchable launchableFactory,
                                  LaunchableUiLauncher launcher,
                                  GvDataPacker<GvData> packer) {
        super(launchableFactory, launcher);
        mDataConfig = dataConfig;
        mPacker = packer;
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
        mPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
        getLauncher().launch(mDataConfig, getActivity(), args);
    }
}
