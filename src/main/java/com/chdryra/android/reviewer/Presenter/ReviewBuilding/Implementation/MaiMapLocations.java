/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchMappedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;


/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiMapLocations extends MaiDataEditor<GvLocation> {
    private LaunchMappedCommand mCommand;

    public MaiMapLocations(LaunchMappedCommand command) {
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
