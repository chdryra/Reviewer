package com.chdryra.android.reviewer.View.Screens;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
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

    private GridItemListener mListener;

    public GiDataLauncher() {
        mListener = new GridItemListener() {
        };
        super.registerActionListener(mListener, TAG);
    }

    @Override
    public void onClickExpandable(GvData item, int position, View v, ReviewViewAdapter
            expanded) {
        ReviewView screen = expanded.getReviewView();
        if (screen == null) screen = ReviewDataScreen.newScreen(expanded, item.getGvDataType());
        LauncherUi.launch(screen, getReviewView().getParent(), REQUEST_CODE, screen.getLaunchTag(), new Bundle());
    }

    @Override
    public void onClickNotExpandable(GvData item, int position, View v) {
        if (item.isCollection()) return;
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
        ConfigGvDataUi.Config config = ConfigGvDataUi.getConfig(item.getGvDataType());
        if (config != null) {
            ConfigGvDataUi.LaunchableConfig view = config.getViewConfig();
            LauncherUi.launch(view.getLaunchable(), mListener, view.getRequestCode(), view
                    .getTag(), args);
        }
    }

    protected abstract class GridItemListener extends Fragment {
    }
}
