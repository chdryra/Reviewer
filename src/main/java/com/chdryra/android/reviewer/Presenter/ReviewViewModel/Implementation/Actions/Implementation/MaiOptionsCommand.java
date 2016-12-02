/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuOptionsItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsCommand;


/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiOptionsCommand<T extends GvData> extends MaiCommand<T> implements MenuOptionsItem<T> {
    public MaiOptionsCommand(OptionsCommand command) {
        super(command);
    }

    public MaiOptionsCommand(OptionsCommand command, @Nullable String toast) {
        super(command, toast);
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return ((OptionSelectListener)getCommand()).onOptionSelected(requestCode, option);
    }
}
