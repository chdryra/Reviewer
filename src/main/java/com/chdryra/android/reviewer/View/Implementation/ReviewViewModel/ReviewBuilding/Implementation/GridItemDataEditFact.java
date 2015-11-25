package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvUrl;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditFact extends GridItemDataEdit<GvFact> {
    private static final int EDIT_ON_BROWSER = RequestCodeGenerator.getCode("EditOnBrowser");
    private LaunchableConfig<GvUrl> mUrlConfig;

    //Constructors
    public GridItemDataEditFact(LaunchableConfig<GvFact> factConfig,
                                LaunchableConfig<GvUrl> urlConfig,
                                FactoryLaunchableUi launchableFactory,
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
