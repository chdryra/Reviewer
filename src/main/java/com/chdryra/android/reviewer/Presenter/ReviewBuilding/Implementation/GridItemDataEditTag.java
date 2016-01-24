/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.widget.Toast;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditTag extends GridItemDataEdit<GvTag> {
    private TagAdjuster mTagAdjuster;

    public GridItemDataEditTag(LaunchableConfig editorConfig,
                               LaunchableUiLauncher launchableFactory,
                               GvDataPacker<GvTag> dataPacker,
                               TagAdjuster tagAdjuster) {
        super(editorConfig, launchableFactory, dataPacker);
        mTagAdjuster = tagAdjuster;
    }

    @Override
    public void onDelete(GvTag data, int requestCode) {
        if (data.equals(mTagAdjuster.getCurrentSubjectTag())) {
            String toast = "Cannot delete subject tag...";
            Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
        } else {
            super.onDelete(data, requestCode);
        }
    }
}
