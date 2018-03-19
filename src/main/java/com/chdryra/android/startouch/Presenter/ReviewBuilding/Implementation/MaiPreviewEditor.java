/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.view.MenuItem;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchBespokeViewCommand;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiPreviewEditor<GC extends GvDataList<? extends GvDataParcelable>>
        extends MaiEditor<GC> {
    private LaunchBespokeViewCommand mCommand;

    public MaiPreviewEditor(LaunchBespokeViewCommand command) {
        mCommand = command;
    }

    @Override
    public void doAction(MenuItem item) {
        mCommand.execute(getEditor().buildPreview(), false);
    }
}
