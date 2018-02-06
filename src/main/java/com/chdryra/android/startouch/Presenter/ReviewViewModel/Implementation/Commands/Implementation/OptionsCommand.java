/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class OptionsCommand extends Command implements OptionSelectListener {
    public OptionsCommand(String name) {
        super(name);
    }
}
