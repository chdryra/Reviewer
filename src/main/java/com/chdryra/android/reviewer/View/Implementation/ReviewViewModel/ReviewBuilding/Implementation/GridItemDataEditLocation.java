package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditLocation extends GridItemDataEdit<GvLocation> {
    private LaunchableConfig mMapEditorConfig;

    //Constructors
    public GridItemDataEditLocation(LaunchableConfig editorConfig,
                                    LaunchableConfig maoEditorConfig,
                                    LaunchableUiLauncher launchableFactory,
                                    GvDataPacker<GvLocation> dataPacker) {
        super(editorConfig, launchableFactory, dataPacker);
        mMapEditorConfig = editorConfig;
    }

    //Overridden
    @Override
    public void onGridItemLongClick(GvLocation item, int position, View v) {
        showAlertDialog(getActivity().getString(R.string.alert_edit_on_map), mMapEditorConfig.getRequestCode(),
                item);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mMapEditorConfig.getRequestCode()) {
            getLaunchableFactory().launch(mMapEditorConfig, getActivity(), new Bundle());
        }
    }
}
