/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditTag extends GridItemDataEdit<GvTag> {
    private TagAdjuster mTagAdjuster;

    public GridItemDataEditTag(LaunchableConfig editorConfig,
                               ParcelablePacker<GvTag> dataPacker,
                               TagAdjuster tagAdjuster) {
        super(editorConfig, dataPacker);
        mTagAdjuster = tagAdjuster;
    }

    @Override
    public void onDelete(GvTag data, int requestCode) {
        if (data.equals(mTagAdjuster.getCurrentSubjectTag())) {
            getApp().getCurrentScreen().showToast(Strings.Toasts.CANNOT_DELETE_SUBJECT_TAG);
        } else {
            super.onDelete(data, requestCode);
        }
    }
}
