/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;


/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiPreviewDataEditor<T extends GvData> extends MaiDataEditor<T> {
    private LaunchBespokeViewCommand mCommand;

    public MaiPreviewDataEditor(LaunchBespokeViewCommand command) {
        mCommand = command;
    }

    @Override
    public void doAction(MenuItem item) {
        DataBuilderAdapter<?> builder = (DataBuilderAdapter<?>) getEditor().getAdapter();
        mCommand.execute(builder.buildPreview(), false);
    }
}
