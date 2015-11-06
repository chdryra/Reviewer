package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 06/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditScreen {
    private Context mContext;
    private ReviewBuilderAdapter mBuilder;
    
    public FactoryEditScreen(Context context, ReviewBuilderAdapter builder) {
        mContext = context;
        mBuilder = builder;
    }
    
    public <T extends GvData> EditScreenReviewData<T> newScreen(GvDataType<T> dataType) {
        EditScreenReviewData screen;
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            screen = new EditScreenComments(mContext, mBuilder);
        } else if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
            screen = new EditScreenCriteria(mContext, mBuilder);
        } else if (dataType.equals(GvFactList.GvFact.TYPE)) {
            screen = new EditScreenFacts(mContext, mBuilder);
        } else if (dataType.equals(GvImageList.GvImage.TYPE)) {
            screen = new EditScreenImages(mContext, mBuilder, mBuilder.getImageChooser(mContext));
        } else if (dataType.equals(GvLocationList.GvLocation.TYPE)) {
            screen = new EditScreenLocations(mContext, mBuilder);
        } else if (dataType.equals(GvTagList.GvTag.TYPE)) {
            screen = new EditScreenTags(mContext, mBuilder);
        } else {
            screen = new EditScreenReviewData<>(mContext, mBuilder, dataType);
        }

        return screen;
    }
}
