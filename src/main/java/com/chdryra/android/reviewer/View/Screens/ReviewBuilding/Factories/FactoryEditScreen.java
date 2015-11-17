package com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation.EditScreenComments;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation.EditScreenCriteria;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation.EditScreenFacts;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation.EditScreenImages;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation.EditScreenLocations;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation.EditScreenReviewData;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation
        .EditScreenReviewDataImpl;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation.EditScreenTags;

/**
 * Created by: Rizwan Choudrey
 * On: 06/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditScreen {
    private Context mContext;
    private ReviewBuilderAdapter mBuilder;
    private FactoryReviewDataEditor mEditorFactory;

    public FactoryEditScreen(Context context, ReviewBuilderAdapter builder,
                             FactoryReviewDataEditor editorFactory) {
        mContext = context;
        mBuilder = builder;
        mEditorFactory = editorFactory;
    }
    
    public <T extends GvData> EditScreenReviewData<T> newScreen(GvDataType<T> dataType, DataValidator validator) {
        EditScreenReviewData screen;
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            screen = new EditScreenComments(mContext, mBuilder, mEditorFactory);
        } else if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
            screen = new EditScreenCriteria(mContext, mBuilder, mEditorFactory);
        } else if (dataType.equals(GvFactList.GvFact.TYPE)) {
            screen = new EditScreenFacts(mContext, mBuilder, mEditorFactory);
        } else if (dataType.equals(GvImageList.GvImage.TYPE)) {
            screen = new EditScreenImages(mContext, mBuilder, mBuilder.getImageChooser(), mEditorFactory);
        } else if (dataType.equals(GvLocationList.GvLocation.TYPE)) {
            screen = new EditScreenLocations(mContext, mBuilder, mEditorFactory);
        } else if (dataType.equals(GvTagList.GvTag.TYPE)) {
            screen = new EditScreenTags(mContext, mBuilder, validator, mEditorFactory);
        } else {
            screen = new EditScreenReviewDataImpl<>(mContext, mBuilder, dataType, mEditorFactory);
        }

        return screen;
    }
}
