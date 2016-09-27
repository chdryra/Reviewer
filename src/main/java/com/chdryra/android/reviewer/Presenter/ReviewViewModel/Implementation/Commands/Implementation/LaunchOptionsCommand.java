/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class LaunchOptionsCommand extends Command {
    private static final int OPTIONS = RequestCodeGenerator.getCode(LaunchOptionsCommand.class, "Options");

    private final LaunchableUi mShareEditUi;
    private UiLauncher mLauncher;
    private DataAuthorId mAuthorId;

    public LaunchOptionsCommand(LaunchableUi shareEditUi) {
        mShareEditUi = shareEditUi;
    }

    public void execute(DataAuthorId authorId, UiLauncher launcher) {
        mLauncher = launcher;
        mAuthorId = authorId;
        execute();
    }

    @Override
    void execute() {
        if(mLauncher == null || mAuthorId == null) return;

        Bundle args = new Bundle();
        DatumAuthorId data = new DatumAuthorId(mAuthorId.getReviewId(), mAuthorId.toString());
        args.putParcelable(mShareEditUi.getLaunchTag(), data);
        mLauncher.launch(mShareEditUi, OPTIONS, args);
    }
}
