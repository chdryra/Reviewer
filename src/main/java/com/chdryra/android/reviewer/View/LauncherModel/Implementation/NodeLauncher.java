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

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class NodeLauncher extends PackingLauncherImpl<ReviewNode> {
    public static final String PUBLISHED = TagKeyGenerator.getTag(NodeLauncher.class);

    public NodeLauncher(LaunchableConfig ui) {
        super(ui);
    }

    @Override
    public void launch(@Nullable ReviewNode item) {
        launch(item, true);
    }

    public void launch(@Nullable ReviewNode item, boolean published) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(PUBLISHED, published);
        launch(item, bundle);
    }
}
