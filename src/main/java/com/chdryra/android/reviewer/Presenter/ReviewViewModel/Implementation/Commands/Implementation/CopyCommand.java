/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Implementation.Strings;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CopyCommand extends Command {
    private static final int LAUNCH = RequestCodeGenerator.getCode(CopyCommand.class, "Launch");
    private final NewReviewCommand mCommand;
    private final CurrentScreen mScreen;

    public CopyCommand(NewReviewCommand command, CurrentScreen screen) {
        mScreen = screen;
        mCommand = command;
    }

    @Override
    public void execute() {
        mScreen.showToast(Strings.Toasts.COPYING);
        mCommand.execute(LAUNCH, new ExecutionListener() {
            @Override
            public void onCommandExecuted(int requestCode) {
                onExecutionComplete();
            }
        });
    }
}
