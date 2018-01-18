/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterReferences;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeData<Value extends HasReviewId, GvRef extends
        GvDataRef<GvRef, Value, ?>>
        extends ViewerReviewData.DataList<Value, GvRef> {

    public ViewerTreeData(RefDataList<Value> reference,
                          GvConverterReferences<Value, GvRef, ReviewItemReference<Value>> converter,
                          FactoryReviewViewAdapter adapterFactory) {
        super(reference, converter, adapterFactory, null);
    }

    @Override
    public boolean isExpandable(GvRef datum) {
        return getGridData().contains(datum);
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvRef datum) {
        return isExpandable(datum) ? newAdapter(datum) : null;
    }

    protected ReviewViewAdapter<?> newAdapter(GvRef datum) {
        return getAdapterFactory().newReviewsListAdapter(datum, null, null);
    }

    public static class TreeAuthorList extends ViewerTreeData<DataAuthorId, GvAuthorId.Reference> {

        public TreeAuthorList(RefDataList<DataAuthorId> reference,
                              GvConverterReferences<DataAuthorId, GvAuthorId.Reference,
                                      ReviewItemReference<DataAuthorId>> converter,
                              FactoryReviewViewAdapter adapterFactory) {
            super(reference, converter, adapterFactory);
        }

        @Override
        protected ReviewViewAdapter<?> newAdapter(GvAuthorId.Reference datum) {
            NamedAuthor author = datum.getNamedAuthor();
            String name = author != null ? author.getName() : null;
            AuthorId id = author != null ? author.getAuthorId() : null;
            return getAdapterFactory().newReviewsListAdapter(datum, name, id);
        }
    }

    public static class TreeCommentList extends ViewerReviewData.CommentList {
        private final FactoryReviewViewAdapter mAdapterFactory;

        public TreeCommentList(RefCommentList reference,
                               GvConverterReferences<DataComment, GvComment.Reference, RefComment> converter,
                               FactoryReviewViewAdapter adapterFactory,
                               FactoryReferences referenceFactory) {
            super(reference, converter, null, adapterFactory, referenceFactory);
            mAdapterFactory = adapterFactory;
        }

        @Override
        public boolean isExpandable(GvComment.Reference datum) {
            return getGridData().contains(datum);
        }

        @Override
        public ReviewViewAdapter<?> expandGridCell(GvComment.Reference datum) {
            return isExpandable(datum) ? mAdapterFactory.newReviewsListAdapter(datum, null, null) : null;
        }
    }
}
