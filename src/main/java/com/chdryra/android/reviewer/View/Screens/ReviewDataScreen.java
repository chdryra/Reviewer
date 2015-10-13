/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataScreen {
    private ReviewDataScreen() {
    }

    //Static methods
    public static ReviewView newScreen(ReviewViewAdapter adapter) {
        ReviewViewParams params = new ReviewViewParams();

        ReviewViewActions actions = new ReviewViewActions();
        actions.setAction(new RbExpandGrid());
        actions.setAction(new GiDataLauncher());

        ReviewViewPerspective perspective = new ReviewViewPerspective(adapter, params, actions);
        return new ReviewView(perspective);
    }

    public static ReviewView newScreen(ReviewViewAdapter adapter,
                                       GvDataType forDefaults) {
        ReviewViewParams params = DefaultParameters.getParams(forDefaults);

        ReviewViewActions actions = new ReviewViewActions();
        actions.setAction(new RbExpandGrid());
        actions.setAction(DefaultMenus.getMenu(forDefaults));
        actions.setAction(DefaultGridActions.getGridAction(forDefaults));

        ReviewViewPerspective perspective = new ReviewViewPerspective(adapter, params, actions);
        return new ReviewView(perspective);
    }
}
