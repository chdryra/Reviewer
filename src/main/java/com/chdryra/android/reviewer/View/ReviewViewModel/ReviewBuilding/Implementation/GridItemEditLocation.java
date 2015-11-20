package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditLocationMap;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemEditLocation extends GridItemEdit<GvLocationList.GvLocation> {
    private static final String TAG = "EditOnMap";
    private static final int EDIT_ON_MAP = RequestCodeGenerator.getCode(TAG);

    //Constructors
    public GridItemEditLocation(LaunchableConfig<GvLocationList.GvLocation> editorConfig,
                                FactoryLaunchableUi launchableFactory,
                                GvDataPacker<GvLocationList.GvLocation> dataPacker) {
        super(editorConfig, launchableFactory, dataPacker);
    }

    //Overridden
    @Override
    public void onGridItemLongClick(GvLocationList.GvLocation item, int position, View v) {
        showAlertDialog(getActivity().getString(R.string.alert_edit_on_map), EDIT_ON_MAP,
                item);
    }

    //TODO move launch class to config somehow...
    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == EDIT_ON_MAP) {
            FactoryLaunchableUi launchableFactory = getLaunchableFactory();
            LaunchableUi mapUi = launchableFactory.newLaunchable(ActivityEditLocationMap.class);
            launchableFactory.launch(mapUi, getActivity(), getLaunchableRequestCode(), TAG, args);
        }
    }
}
