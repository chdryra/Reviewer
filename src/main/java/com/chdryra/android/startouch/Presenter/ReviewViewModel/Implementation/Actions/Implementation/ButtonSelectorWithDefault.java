/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.OptionsSelector;

/**
 * Created by: Rizwan Choudrey
 * On: 12/08/2017
 * Email: rizwan.choudrey@gmail.com
 *
 * Assumes view on attach pertains to one of the choices within the selector.
 */

public class ButtonSelectorWithDefault<T extends GvData> extends ButtonSelector<T> {
    private final String mDefaultCommand;

    public ButtonSelectorWithDefault(OptionsSelector selector, CommandList commands, String defaultCommand) {
        super(selector, commands, true);
        mDefaultCommand = defaultCommand;
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        setCurrentlySelected(mDefaultCommand);
    }
}
