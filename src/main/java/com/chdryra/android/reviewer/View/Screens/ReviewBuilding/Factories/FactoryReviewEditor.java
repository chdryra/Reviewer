package com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation.ReviewEditorDefault;
import com.chdryra.android.reviewer.View.Screens.ReviewViewActions;
import com.chdryra.android.reviewer.View.Screens.ReviewViewParams;
import com.chdryra.android.reviewer.View.Screens.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewEditor {
    public ReviewEditor newEditor(ReviewBuilderAdapter builder,
                                  ReviewViewParams params,
                                  ReviewViewActions actions,
                                  ReviewViewPerspective.ReviewViewModifier modifier) {
        return new ReviewEditorDefault(builder, params, actions, modifier);
    }
}
