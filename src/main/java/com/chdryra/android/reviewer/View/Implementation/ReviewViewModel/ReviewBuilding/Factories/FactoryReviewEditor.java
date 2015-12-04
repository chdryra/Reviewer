package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.ReviewEditorDefault;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewEditor {
    public <GC extends GvDataList<?>> ReviewEditor<GC> newEditor(ReviewBuilderAdapter<GC> builder,
                                  ReviewViewParams params,
                                  ReviewViewActions<GC> actions,
                                  ReviewViewPerspective.ReviewViewModifier modifier) {
        return new ReviewEditorDefault<>(builder, actions, params, modifier);
    }
}
