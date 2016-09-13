/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNodeList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ReviewSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .SelectorMostRecent;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhReviewSelected;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
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

    public GvConverterReviewNode(AuthorsRepository repository,
                                 GvConverterComments converterComments,
                                 GvConverterLocations converterLocations) {
        super(GvNodeList.class);
        mFactory = new VhMostRecentFactory(repository, converterComments, converterLocations);
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
        for(ReviewNode datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }

    private static class VhMostRecentFactory implements ViewHolderFactory<VhNode> {
        private final AuthorsRepository mRepository;
        private final GvConverterComments mConverterComments;
        private final GvConverterLocations mConverterLocations;

        public VhMostRecentFactory(AuthorsRepository repository, GvConverterComments
                converterComments, GvConverterLocations converterLocations) {
            mRepository = repository;
            mConverterComments = converterComments;
            mConverterLocations = converterLocations;
        }

        @Override
        public VhNode newViewHolder() {
            return new VhReviewSelected(mRepository, new ReviewSelector(new SelectorMostRecent()),
                    mConverterComments, mConverterLocations);
        }
    }
}
