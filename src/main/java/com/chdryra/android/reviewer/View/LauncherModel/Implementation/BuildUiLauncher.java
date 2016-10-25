/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewBuilderSuite;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 05/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildUiLauncher extends ReviewUiLauncher {
    private final ReviewBuilderSuite mBuilder;

    public BuildUiLauncher(LaunchableConfig ui, RepositorySuite repository,
                           ReviewBuilderSuite builder) {
        super(ui, repository);
        mBuilder = builder;
    }

    @Override
    protected void onPrelaunch() {
        mBuilder.discardReviewEditor();
    }
}
