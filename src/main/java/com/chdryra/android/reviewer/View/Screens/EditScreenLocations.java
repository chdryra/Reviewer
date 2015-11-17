/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditLocationMap;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.Launcher.FactoryLaunchable;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenLocations extends EditScreenReviewData<GvLocationList.GvLocation> {
    private static final GvDataType<GvLocationList.GvLocation> TYPE =
            GvLocationList.GvLocation.TYPE;

    public EditScreenLocations(Context context, ReviewBuilderAdapter builder) {
        super(context, builder, TYPE);
    }

    @Override
    protected GridItemEdit<GvLocationList.GvLocation> newGridItemAction() {
        return new GridItemEditLocation();
    }

    @Override
    protected BannerButtonEdit<GvLocationList.GvLocation> newBannerButtonAction() {
        return new BannerButtonAddLocation(getBannerButtonTitle());
    }

    //Classes
    private static class BannerButtonAddLocation extends BannerButtonEdit<GvLocationList.GvLocation> {
        private static final int ADD_ON_MAP = RequestCodeGenerator.getCode("AddOnMap");

        //Constructors
        private BannerButtonAddLocation(String title) {
            super(TYPE, title);
        }

        //Overridden
        @Override
        public boolean onLongClick(View v) {
            showAlertDialog(getActivity().getString(R.string.alert_add_on_map), ADD_ON_MAP);
            return true;
        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == ADD_ON_MAP) {
                LaunchableUi mapUi = FactoryLaunchable.newLaunchable(ActivityEditLocationMap.class);
                LauncherUi.launch(mapUi, getActivity(), getLaunchableRequestCode(), null,
                        new Bundle());
            }
        }
    }

    private static class GridItemEditLocation extends GridItemEdit<GvLocationList.GvLocation> {
        private static final int EDIT_ON_MAP = RequestCodeGenerator.getCode("EditOnMap");

        //Constructors
        private GridItemEditLocation() {
            super(TYPE);
        }

        //Overridden
        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            showAlertDialog(getActivity().getString(R.string.alert_edit_on_map), EDIT_ON_MAP,
                    item);
        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == EDIT_ON_MAP) {
                LaunchableUi mapUi = FactoryLaunchable.newLaunchable(ActivityEditLocationMap.class);
                LauncherUi.launch(mapUi, getActivity(), getLaunchableRequestCode(), null, args);
            }
        }
    }
}
