/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenFacts {
    public static class BannerButton extends EditScreen.BannerButton {
        private static final int ADD_ON_BROWSER = 200;

        public BannerButton(String title) {
            super(ConfigGvDataUi.getConfig(GvFactList.TYPE).getAdderConfig(), title);
        }

        @Override
        public boolean onLongClick(View v) {
            showAlertDialog(getActivity().getString(R.string.dialog_add_on_browser),
                    ADD_ON_BROWSER);
            return true;
        }

        @Override
        protected void onDialogAlertPositive(int requestCode) {
            if (requestCode == ADD_ON_BROWSER) {
                LaunchableUi mapUi = ConfigGvDataUi.getLaunchable(ActivityEditUrlBrowser.class);
                LauncherUi.launch(mapUi, getListener(), getRequestCode(), null, new Bundle());
            }
        }
    }

    public static class GridItem extends EditScreen.GridItem {
        private static final int EDIT_ON_BROWSER = 200;

        public GridItem() {
            super(ConfigGvDataUi.getConfig(GvFactList.TYPE).getEditorConfig());
        }

        @Override
        public void onGridItemLongClick(GvData item, View v) {
            GvFactList.GvFact fact = (GvFactList.GvFact) item;
            if (!fact.isUrl()) {
                super.onGridItemLongClick(item, v);
            } else {
                showAlertDialog(getActivity().getString(R.string.dialog_edit_on_browser),
                        EDIT_ON_BROWSER, fact);
            }
        }

        @Override
        protected void onDialogAlertPositive(int requestCode, Bundle args) {
            if (requestCode == EDIT_ON_BROWSER) {
                LaunchableUi mapUi = ConfigGvDataUi.getLaunchable(ActivityEditUrlBrowser
                        .class);
                LauncherUi.launch(mapUi, getListener(), getLaunchableRequestCode(), null, args);
            }
        }
    }
}
