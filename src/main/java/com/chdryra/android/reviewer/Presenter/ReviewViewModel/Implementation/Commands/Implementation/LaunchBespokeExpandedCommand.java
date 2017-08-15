/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;



import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 15/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class LaunchBespokeExpandedCommand extends LaunchBespokeViewCommand {
    private final ReviewViewAdapter<?> mUnexpanded;

    public LaunchBespokeExpandedCommand(String name,
                                        ReviewLauncher launcher,
                                        ReviewViewAdapter<?> unexpanded,
                                        GvDataType<?> dataType) {
        super(name, launcher, null, dataType);
        mUnexpanded = unexpanded;
    }

    @Override
    public void execute() {
        AdapterReviewNode<?> adapter = null;
        try {
            adapter = (AdapterReviewNode<?>) mUnexpanded.expandGridData();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        if(adapter != null) execute(adapter.getNode(), 0, true);
    }
}
