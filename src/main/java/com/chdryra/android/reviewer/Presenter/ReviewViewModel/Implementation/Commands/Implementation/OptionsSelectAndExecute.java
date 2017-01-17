/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation;


import android.support.annotation.Nullable;

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
        if (mLocked || mCommands == null || mCommands.size() == 0) {
            onExecutionComplete();
            return;
        }

        mCode = RequestCodeGenerator.getCode(ReviewOptionsSelector.class,
                TextUtils.commaDelimited(mCommands.getCommandNames()));

        lock();
        mOptionsCommand.execute(mCommands.getCommandNames(), null, mCode, new ExecutionListener() {
            @Override
            public void onCommandExecuted(int requestCode) {
                if (mCode == requestCode) onExecutionComplete();
            }
        });
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return executeOption(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return executeOption(requestCode, null);
    }

    private boolean executeOption(int requestCode, @Nullable String option) {
        if (requestCode == mCode) {
            if (option != null) mCommands.execute(option);
            unlock();
            return true;
        }

        return false;
    }

    private void lock() {
        mLocked = true;
    }

    private void unlock() {
        mLocked = false;
    }
}
