/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.CacheUtils.ItemPacker;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeUiLauncher {
    private final LaunchableConfig mUi;
    private final ItemPacker<ReviewNode> mNodePacker;

    public NodeUiLauncher(LaunchableConfig ui) {
        mUi = ui;
        mNodePacker = new ItemPacker<>();
    }

    public void setUiLauncher(UiLauncher uiLauncher) {
        mUi.setLauncher(uiLauncher);
    }

    public void launch(ReviewNode node) {
        Bundle args = new Bundle();
        mNodePacker.pack(node, args);
        mUi.launch(new UiLauncherArgs(mUi.getDefaultRequestCode()).setBundle(args));
    }

    @Nullable
    ReviewNode unpackNode(Bundle args) {
        return mNodePacker.unpack(args);
    }
}
