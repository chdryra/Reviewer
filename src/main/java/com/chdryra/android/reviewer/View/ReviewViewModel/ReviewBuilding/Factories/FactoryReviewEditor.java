package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.ReviewEditorDefault;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewEditor {
    public ReviewEditor<?> newEditor(ReviewBuilderAdapter<?> builder,
                                  ReviewViewParams params,
                                  ReviewViewActions actions,
                                  ReviewViewPerspective.ReviewViewModifier modifier) {
        return new ReviewEditorDefault(builder, params, actions, modifier);
    }
}
