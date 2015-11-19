/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;

import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories.FactoryReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenFacts extends EditScreenReviewDataImpl<GvFactList.GvFact> {
    private static final GvDataType<GvFactList.GvFact> TYPE =
            GvFactList.GvFact.TYPE;

    public EditScreenFacts(Context context, ReviewBuilderAdapter builder, FactoryReviewDataEditor
            editorFactory) {
        super(context, builder, TYPE, editorFactory);
    }

    @Override
    protected GridItemEdit<GvFactList.GvFact> newGridItemAction() {
        return new GridItemAddEditFact();
    }

    @Override
    protected BannerButtonEdit<GvFactList.GvFact> newBannerButtonAction() {
        return new BannerButtonAddFacts(getBannerButtonTitle());
    }

}
