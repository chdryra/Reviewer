package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.GridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.ViewerChildList;
import com.chdryra.android.reviewer.Interfaces.Data.DataConverter;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderChildListScreen {
    public ReviewView createView(ReviewNode node,
                                 DataConverter<Review, GvReviewOverviewList.GvReviewOverview> reviewConverter,
                                 DataConverter<DataImage, GvImageList.GvImage> imageConverter,
                                 FactoryReviewViewAdapter adapterFactory,
                                 ReviewViewAction.GridItemAction giAction,
                                 ReviewViewAction.MenuAction menuAction) {
        GridDataViewer<GvReviewOverviewList.GvReviewOverview> viewer;
        viewer = new ViewerChildList(node, reviewConverter, adapterFactory);
        ReviewViewAdapter adapter = new AdapterReviewNode<>(node, imageConverter, viewer);

        ReviewViewActions actions = new ReviewViewActions();
        if (giAction != null) actions.setAction(giAction);
        if (menuAction != null) actions.setAction(menuAction);
        actions.setAction(new RbExpandGrid());

        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setSubjectVisible(true).setRatingVisible(true).setBannerButtonVisible(true)
                .setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);

        ReviewViewPerspective perspective = new ReviewViewPerspective(adapter, params, actions);
        return new ReviewView(perspective);
    }
}
