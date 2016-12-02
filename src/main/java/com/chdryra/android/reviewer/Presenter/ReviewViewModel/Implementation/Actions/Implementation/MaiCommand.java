/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiCommand<T extends GvData> extends MenuActionItemBasic<T> {
    private final Command mCommand;
    private final String mToast;

    public MaiCommand(Command command) {
        this(command, null);
    }

    public MaiCommand(Command command, @Nullable String toast) {
        mCommand = command;
        mToast = toast;
    }

    @Override
    public void doAction(MenuItem item) {
        if(mToast != null) getCurrentScreen().showToast(mToast);
        mCommand.execute();
    }

    protected Command getCommand() {
        return mCommand;
    }
}
