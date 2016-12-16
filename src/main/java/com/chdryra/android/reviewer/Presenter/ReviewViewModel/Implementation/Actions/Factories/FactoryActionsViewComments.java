/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSplitCommentRefs;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuViewComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewComments extends FactoryActionsViewData<GvComment.Reference> {
    public FactoryActionsViewComments(ViewDataParameters<GvComment.Reference> parameters) {
        super(parameters);
    }

    @Override
    public GridItemAction<GvComment.Reference> newGridItem() {
        return new GridItemComments(getLauncher(), getGridItemConfig(), getViewFactory());
    }

    @Override
    public MenuAction<GvComment.Reference> newMenu() {
        return new MenuViewComments(newOptionsMenuItem(), new MaiSplitCommentRefs());
    }
}
