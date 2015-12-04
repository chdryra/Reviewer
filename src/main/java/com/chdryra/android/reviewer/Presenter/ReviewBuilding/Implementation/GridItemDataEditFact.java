package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditFact extends GridItemDataEdit<GvFact> {
    private static final int EDIT_ON_BROWSER = RequestCodeGenerator.getCode("EditOnBrowser");
    private LaunchableConfig mUrlConfig;

    //Constructors
    public GridItemDataEditFact(LaunchableConfig factConfig,
                                LaunchableConfig urlConfig,
                                LaunchableUiLauncher launchableFactory,
                                GvDataPacker<GvFact> dataPacker) {
        super(factConfig, launchableFactory, dataPacker);
        mUrlConfig = urlConfig;
    }

    //Overridden
    @Override
    public void onGridItemLongClick(GvFact item, int position, View v) {
        if (!item.isUrl()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlertDialog(getActivity().getString(R.string.alert_edit_on_browser),
                    EDIT_ON_BROWSER, item);
        }
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == EDIT_ON_BROWSER) launch(mUrlConfig, args);
    }
}
