/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenShareButton<GC extends GvDataList<? extends GvDataParcelable>>
        extends ReviewEditorActionBasic<GC>
        implements ContextualButtonAction<GC>{
    private final LaunchableConfig mBuildScreenConfig;

    public BuildScreenShareButton(LaunchableConfig buildScreenConfig) {
        mBuildScreenConfig = buildScreenConfig;
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void onClick(View v) {
        ReviewEditor.ReadyToBuildResult result = getEditor().isReviewBuildable();
        if (!result.equals(ReviewEditor.ReadyToBuildResult.YES)) {
            getCurrentScreen().showToast(result.getMessage());
            return;
        }

        mBuildScreenConfig.launch();
    }

    @Override
    public String getButtonTitle() {
        return Strings.Screens.SHARE;
    }
}
