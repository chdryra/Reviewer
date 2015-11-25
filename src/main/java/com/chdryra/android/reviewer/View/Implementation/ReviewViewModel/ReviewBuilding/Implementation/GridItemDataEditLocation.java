package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Implementation.SpecialisedActivities.ActivityEditLocationMap;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditLocation extends GridItemDataEdit<GvLocation> {
    private static final String TAG = "EditOnMap";
    private static final int EDIT_ON_MAP = RequestCodeGenerator.getCode(TAG);

    //Constructors
    public GridItemDataEditLocation(LaunchableConfig<GvLocation> editorConfig,
                                    FactoryLaunchableUi launchableFactory,
                                    GvDataPacker<GvLocation> dataPacker) {
        super(editorConfig, launchableFactory, dataPacker);
    }

    //Overridden
    @Override
    public void onGridItemLongClick(GvLocation item, int position, View v) {
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
