/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation;

import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DecoratedCommand extends Command {
    private static final int EXECUTE = RequestCodeGenerator.getCode(DecoratedCommand.class);

    private final String mToastOnExecution;
    private final Command mCommand;
    private final CurrentScreen mScreen;

    public DecoratedCommand(String name, String toastOnExecution, Command command, CurrentScreen screen) {
        super(name);
        mToastOnExecution = toastOnExecution;
        mScreen = screen;
        mCommand = command;
    }

    @Override
    public void execute() {
        mScreen.showToast(mToastOnExecution);
        mCommand.execute(EXECUTE, new ExecutionListener() {
            @Override
            public void onCommandExecuted(int requestCode) {
                onExecutionComplete();
            }
        });
    }
}
