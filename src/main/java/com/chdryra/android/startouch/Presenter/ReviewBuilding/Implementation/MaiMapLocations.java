/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.view.MenuItem;

import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;


/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiMapLocations extends MaiDataEditor<GvLocation> {
    private LaunchBespokeViewCommand mCommand;

    public MaiMapLocations(LaunchBespokeViewCommand command) {
        mCommand = command;
    }

    @Override
    public void doAction(MenuItem item) {
        ReviewDataEditor<GvLocation> editor = getEditor();
        if(editor.getGridData().size() == 0) return;
        DataBuilderAdapter<?> builder = (DataBuilderAdapter<?>) editor.getAdapter();
        mCommand.execute(builder.buildPreview(), false);
    }
}
