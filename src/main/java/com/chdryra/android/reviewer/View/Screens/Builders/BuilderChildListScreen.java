package com.chdryra.android.reviewer.View.Screens.Builders;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.GvImageConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.GvReviewConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces.GridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.ViewerChildList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Screens.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Screens.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Screens.RbExpandGrid;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Screens.ReviewViewActions;
import com.chdryra.android.reviewer.View.Screens.ReviewViewDefault;
import com.chdryra.android.reviewer.View.Screens.ReviewViewParams;
import com.chdryra.android.reviewer.View.Screens.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderChildListScreen {
    public ReviewView createView(ReviewNode node,
                                 GvReviewConverter<GvReviewOverviewList.GvReviewOverview, GvReviewOverviewList> reviewConverter,
                                 GvImageConverter imageConverter,
                                 FactoryReviewViewAdapter adapterFactory,
                                 GridItemAction giAction,
                                 MenuAction menuAction) {
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
        return new ReviewViewDefault(perspective);
    }
}
