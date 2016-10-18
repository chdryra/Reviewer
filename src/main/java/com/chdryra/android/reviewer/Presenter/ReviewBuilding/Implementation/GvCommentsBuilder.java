/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCommentsBuilder extends DataBuilderImpl<GvComment> {
    private CommentsDataParser mParser;

    public GvCommentsBuilder(GvDataList<GvComment> data, FactoryGvData dataFactory, CommentsDataParser parser) {
        super(data, dataFactory);
        mParser = parser;
    }

    @Override
    public ConstraintResult add(GvComment newDatum) {
        if (getData().size() == 0) newDatum.setIsHeadline(true);
        return super.add(newDatum);
    }

    @Override
    public ConstraintResult replace(GvComment oldDatum,
                                    GvComment newDatum) {
        newDatum.setIsHeadline(oldDatum.isHeadline());
        return super.replace(oldDatum, newDatum);
    }

    @Override
    public boolean delete(GvComment data) {
        boolean removed = super.delete(data);
        if (data.isHeadline()) {
            data.setIsHeadline(false);
            GvCommentList comments = (GvCommentList) getData();
            if (comments.getHeadlines().size() == 0 && comments.size() > 0) {
                comments.getItem(0).setIsHeadline(true);
            }
        }

        notifyDataObservers();

        return removed;
    }

    @Override
    public void buildData() {
        for(GvComment comment : getOriginalData()) {
            mParser.delete(comment);
        }

        for(GvComment comment : getData()) {
            mParser.add(comment);
        }

        mParser.commit();
        super.buildData();
    }
}
