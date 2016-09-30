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
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class LaunchOptionsCommand extends Command {
    private static final int OPTIONS = RequestCodeGenerator.getCode(LaunchOptionsCommand.class, "Options");

    private final LaunchableConfig mOptionsUi;
    private DataAuthorId mAuthorId;

    public LaunchOptionsCommand(LaunchableConfig optionsUi) {
        mOptionsUi = optionsUi;
    }

    public void execute(DataAuthorId authorId) {
        mAuthorId = authorId;
        execute();
    }

    @Override
    void execute() {
        if(mAuthorId == null) return;

        Bundle args = new Bundle();
        DatumAuthorId data = new DatumAuthorId(mAuthorId.getReviewId(), mAuthorId.toString());
        args.putParcelable(mOptionsUi.getTag(), data);
        mOptionsUi.launch(OPTIONS, args);
    }
}
