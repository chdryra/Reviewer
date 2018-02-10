/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;


/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ButtonSelector<T extends GvData> extends ButtonCommandable<T> {
    private final OptionsSelector mSelector;
    private final boolean mIgnoreCurrent;
    private final int mRequestCode;
    private CommandList mOptions;
    private String mCurrentlySelected = null;

    public ButtonSelector(OptionsSelector selector, CommandList commands, boolean ignoreCurrent) {
        super(commands.getListName());
        mSelector = selector;
        mIgnoreCurrent = ignoreCurrent;
        mOptions = new CommandList();
        setClick(new ClickCommand());
        mRequestCode = RequestCodeGenerator.getCode(ButtonSelector.class, commands.getListName());
        for(Command option : commands) {
            addOption(option);
        }
    }

    public void addOption(Command command) {
        mOptions.add(command);
    }

    protected void setCurrentlySelected(String optionName) {
        Command option = null;
        for(int i = 0; i < mOptions.size(); ++i) {
            option = mOptions.get(i);
            if(optionName.equals(option.getName())) break;
        }

        if(option != null) mCurrentlySelected = optionName;
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        if(requestCode == mRequestCode) {
            if(!option.equals(mCurrentlySelected) || !mIgnoreCurrent) {
                mCurrentlySelected = option;
                mOptions.execute(option);
            }
            return true;
        } else {
            return super.onOptionSelected(requestCode, option);
        }
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return requestCode == mRequestCode;
    }

    private class ClickCommand extends Command {
        @Override
        public void execute() {
            launchSelector();
        }
    }

    protected void launchSelector() {
        mSelector.execute(mOptions.getCommandNames(), mCurrentlySelected, mRequestCode, null);
    }
}
