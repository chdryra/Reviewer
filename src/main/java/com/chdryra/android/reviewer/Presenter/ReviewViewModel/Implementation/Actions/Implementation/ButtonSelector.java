/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.support.annotation.Nullable;

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
    private static final int OPTIONS = RequestCodeGenerator.getCode(ButtonSelector.class);

    private final OptionsSelector mSelector;
    private final CommandsList mOptions;

    public ButtonSelector(String title, CommandsList options, OptionsSelector selector) {
        super(title);
        mSelector = selector;
        mOptions = options;
        setClick(new ClickCommand());
    }

    public void addOption(Command command) {
        mOptions.add(command);
    }

    public void addLongClick(Command command) {
        mOptions.add(command);
        setLongClick(command);
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        if(requestCode == OPTIONS) {
            mOptions.execute(option);
            return true;
        } else {
            return super.onOptionSelected(requestCode, option);
        }
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return requestCode == OPTIONS;
    }

    private class ClickCommand extends Command {
        @Override
        public void execute() {
            launchSelector();
        }
    }

    protected void launchSelector() {
        launchSelector(null);
    }

    protected void launchSelector(@Nullable String currentOption) {
        mSelector.execute(mOptions.getCommandNames(), currentOption, OPTIONS, null);
    }
}
