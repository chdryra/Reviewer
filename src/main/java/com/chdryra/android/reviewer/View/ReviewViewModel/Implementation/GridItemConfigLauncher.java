package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemConfigLauncher<T extends GvData> extends GridItemLauncher<T> {
    private LaunchableConfig<T> mLaunchableConfig;

    public GridItemConfigLauncher(LaunchableConfig<T> launchableConfig,
                                  FactoryLauncherUi launcherfactory,
                                  FactoryLaunchableUi launchableFactory) {
        super(launcherfactory, launchableFactory);
        mLaunchableConfig = launchableConfig;
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
        if (item.isVerboseCollection() || mLaunchableConfig == null) return;
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
        LaunchableUi launchable = mLaunchableConfig.getLaunchable(getLaunchableFactory());
        launch(launchable, mLaunchableConfig.getRequestCode(), mLaunchableConfig.getTag(), args);
    }
}
