package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GiDataLauncher extends GridItemExpander {
    private static final String TAG = "GiDataLauncher.GridItemListener";
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode(TAG);

    //Overridden
    @Override
    public void onClickExpandable(GvData item, int position, View v, ReviewViewAdapter
            expanded) {
        ReviewView screen = expanded.getReviewView();
        if (screen == null) screen = ReviewDataScreen.newScreen(expanded, item.getGvDataType());
        LauncherUi.launch(screen, getActivity(), REQUEST_CODE, screen.getLaunchTag(), new Bundle());
    }

    @Override
    public void onLongClickExpandable(GvData item, int position, View v, ReviewViewAdapter
            expanded) {
        GvData datum = item;
        if (item instanceof GvCanonical) {
            GvCanonical canonical = (GvCanonical) item;
            datum = canonical.size() == 1 ? canonical.getItem(0) : canonical.getCanonical();
        }

        onClickNotExpandable(datum, position, v);
    }

    @Override
    public void onClickNotExpandable(GvData item, int position, View v) {
        if (item.isCollection()) return;
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
        ConfigGvDataUi.Config config = ConfigGvDataUi.getConfig(item.getGvDataType());
        if (config != null) {
            ConfigGvDataUi.LaunchableConfig view = config.getViewConfig();
            LauncherUi.launch(view.getLaunchable(), getActivity(), view.getRequestCode(),
                    view.getTag(), args);
        }
    }

    @Override
    public void onLongClickNotExpandable(GvData item, int position, View v) {
        onClickNotExpandable(item, position, v);
    }
}
