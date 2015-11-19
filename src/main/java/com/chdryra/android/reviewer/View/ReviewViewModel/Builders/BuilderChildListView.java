package com.chdryra.android.reviewer.View.ReviewViewModel.Builders;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewDefault;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewPerspective;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderChildListView {
    public ReviewView<GvReviewOverviewList.GvReviewOverview>
    buildView(ReviewNode node, FactoryReviewViewAdapter adapterFactory,
              ReviewViewActions<GvReviewOverviewList.GvReviewOverview> actions) {
        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setSubjectVisible(true).setRatingVisible(true).setBannerButtonVisible(true)
                .setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);

        ReviewViewAdapter<GvReviewOverviewList.GvReviewOverview> adapter;
        adapter = adapterFactory.newChildListAdapter(node);

        ReviewViewPerspective<GvReviewOverviewList.GvReviewOverview> perspective;
        perspective = new ReviewViewPerspective<>(adapter, params, actions);

        return new ReviewViewDefault<>(perspective);
    }
}
