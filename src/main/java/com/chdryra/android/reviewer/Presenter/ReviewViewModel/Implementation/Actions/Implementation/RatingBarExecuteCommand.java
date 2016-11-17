/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarExecuteCommand<T extends GvData> extends RatingBarActionNone<T> {
    private final Command mCommand;
    private final String mToast;

    public RatingBarExecuteCommand(Command command, @Nullable String toast) {
        mCommand = command;
        mToast = toast;
    }

    @Override
    public void onClick(View v) {
        if(mToast != null) getCurrentScreen().showToast(mToast);
        mCommand.execute();
    }
}