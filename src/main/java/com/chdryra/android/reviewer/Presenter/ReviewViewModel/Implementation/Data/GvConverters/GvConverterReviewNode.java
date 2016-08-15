/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNodeList;


/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterReviewNode
        extends GvConverterBasic<ReviewNode, GvNode, GvNodeList>
        implements DataConverter<ReviewNode, GvNode, GvNodeList> {
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;

    public GvConverterReviewNode(GvConverterComments converterComments,
                                 GvConverterLocations converterLocations) {
        super(GvNodeList.class);
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
    }

    @Override
    public GvNode convert(ReviewNode node, ReviewId parentId) {
        return new GvNode(node, mConverterComments, mConverterLocations);
    }

    @Override
    public GvNode convert(ReviewNode node) {
        return convert(node, null);
    }

    @Override
    public GvNodeList convert(IdableList<? extends ReviewNode> data) {
        GvNodeList list = new GvNodeList(newId(data.getReviewId()));
        for(ReviewNode datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }
}
