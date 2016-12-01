/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;


import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class LaunchReviewOptionsCommand2 extends Command {
    public static final String AUTHOR_ID
            = TagKeyGenerator.getKey(LaunchReviewOptionsCommand2.class, "AuthorId");

    private final LaunchableConfig mOptionsConfig;
    private DataAuthorId mAuthorId;

    public LaunchReviewOptionsCommand2(LaunchableConfig optionsConfig) {
        mOptionsConfig = optionsConfig;
    }

    public LaunchReviewOptionsCommand2(LaunchableConfig optionsConfig, DataAuthorId authorId) {
        mOptionsConfig = optionsConfig;
        mAuthorId = authorId;
    }

    public void execute(DataAuthorId authorId) {
        mAuthorId = authorId;
        execute();
    }

    @Override
    public void execute() {
        if(mAuthorId == null) return;

        Bundle args = new Bundle();
        DatumAuthorId data = new DatumAuthorId(mAuthorId.getReviewId(), mAuthorId.toString());
        args.putParcelable(AUTHOR_ID, data);
        int code = mOptionsConfig.getDefaultRequestCode();

        mOptionsConfig.launch(new UiLauncherArgs(code).setBundle(args));

        onExecutionComplete();
    }
}
