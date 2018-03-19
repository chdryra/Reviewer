/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvNodeList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;


/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterReviewNode
        extends GvConverterBasic<ReviewNode, GvNode, GvNodeList>
        implements DataConverter<ReviewNode, GvNode, GvNodeList> {

    private final ViewHolderFactory<VhNode> mFactory;

    public GvConverterReviewNode(ViewHolderFactory<VhNode> factory) {
        super(GvNodeList.class);
        mFactory = factory;
    }

    @Override
    public GvNode convert(ReviewNode node, @Nullable ReviewId parentId) {
        return new GvNode(node, mFactory);
    }

    @Override
    public GvNode convert(ReviewNode node) {
        return convert(node, null);
    }

    @Override
    public GvNodeList convert(IdableList<? extends ReviewNode> data) {
        GvNodeList list = new GvNodeList(newId(data.getReviewId()));
        for (ReviewNode datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }

}
