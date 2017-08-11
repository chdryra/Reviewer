/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;


/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ButtonSelector<T extends GvData> extends ButtonCommandable<T> {
    private final OptionsSelector mSelector;
    private final int mRequestCode;
    private CommandsList mOptions;
    private String mCurrentlySelected = null;

    public ButtonSelector(String title, OptionsSelector selector) {
        super(title);
        mSelector = selector;
        mOptions = new CommandsList();
        setClick(new ClickCommand());
        mRequestCode = RequestCodeGenerator.getCode(ButtonSelector.class, title);
    }

    public void addOption(Command command) {
        mOptions.add(command);
    }

    public void addOption(int position, Command command) {
        mOptions.add(position, command);
    }

    public void addLongClick(Command command) {
        mOptions.add(command);
        setLongClick(command);
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
            mCurrentlySelected = option;
            mOptions.execute(option);
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
