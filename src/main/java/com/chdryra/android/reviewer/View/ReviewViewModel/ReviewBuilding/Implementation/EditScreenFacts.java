/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Launcher.Implementation.LauncherUiImpl;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories.FactoryReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenFacts extends EditScreenReviewDataImpl<GvFactList.GvFact> {
    private static final GvDataType<GvFactList.GvFact> TYPE =
            GvFactList.GvFact.TYPE;

    public EditScreenFacts(Context context, ReviewBuilderAdapter builder, FactoryReviewDataEditor
            editorFactory) {
        super(context, builder, TYPE, editorFactory);
    }

    @Override
    protected GridItemEdit<GvFactList.GvFact> newGridItemAction() {
        return new GridItemAddEditFact();
    }

    @Override
    protected BannerButtonEdit<GvFactList.GvFact> newBannerButtonAction() {
        return new BannerButtonAddFacts(getBannerButtonTitle());
    }

    private static class GridItemAddEditFact extends GridItemEdit<GvFactList.GvFact> {
        private static final int EDIT_ON_BROWSER = RequestCodeGenerator.getCode("EditOnBrowser");

        //Constructors
        private GridItemAddEditFact() {
            super(TYPE);
        }

        //Overridden
        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            GvFactList.GvFact fact = (GvFactList.GvFact) item;
            if (!fact.isUrl()) {
                super.onGridItemLongClick(item, position, v);
            } else {
                showAlertDialog(getActivity().getString(R.string.alert_edit_on_browser),
                        EDIT_ON_BROWSER, fact);
            }
        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == EDIT_ON_BROWSER) {
                LaunchableUi urlUi = FactoryLaunchableUi.newLaunchable(ActivityEditUrlBrowser.class);
                LauncherUiImpl.launch(urlUi, getActivity(), getLaunchableRequestCode(), null, args);
            }
        }
    }
}
