/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;


import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class OptionsSelectAndExecute extends OptionsCommand {
    private final OptionsSelector mOptionsCommand;
    private CommandsList mCommands;
    private boolean mLocked = false;
    private int mCode;

    public OptionsSelectAndExecute(String name, OptionsSelector optionsCommand) {
        super(name);
        mCommands = new CommandsList();
        mOptionsCommand = optionsCommand;
    }

    public void setCommands(CommandsList commands) {
        mCommands = commands;
    }

    @Override
    public void execute() {
        if(mLocked || mCommands == null || mCommands.size() == 0) {
            onExecutionComplete();
            return;
        }

        mCode = RequestCodeGenerator.getCode(ReviewOptionsSelector.class,
                TextUtils.commaDelimited(mCommands.getCommandNames()));

        mLocked = true;
        mOptionsCommand.execute(mCommands.getCommandNames(), null, mCode, new ExecutionListener() {
            @Override
            public void onCommandExecuted(int requestCode) {
                if (mCode == requestCode) onExecutionComplete();
            }
        });
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        if (requestCode == mCode) {
            mCommands.execute(option);
            mLocked = false;
            return true;
        }

        return false;
    }
}
