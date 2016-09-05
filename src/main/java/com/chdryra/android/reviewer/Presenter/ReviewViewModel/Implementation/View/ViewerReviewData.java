/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterReferences;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataRefList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerReviewData<Value extends HasReviewId,
        GvRef extends GvDataRef<GvRef, Value, ?>, Reference extends ReviewItemReference<Value>,
        List extends ReviewListReference<Value, Reference>>
        extends GridDataWrapperBasic<GvRef> {

    private final List mReference;
    private final GvConverterReferences<Value, GvRef, Reference> mConverter;
    private GvDataRefList<GvRef> mCache;

    ViewerReviewData(List reference, GvConverterReferences<Value, GvRef, Reference>
            converter) {
        mReference = reference;
        mConverter = converter;
        mCache = newDataList();
    }

    List getReference() {
        return mReference;
    }

    void makeGridData(IdableList<Reference> references) {
        mCache = newDataList();
        for (Reference reference : references) {
            mCache.add(mConverter.convert(reference));
        }
        notifyDataObservers();
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return null;
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvRef datum) {
        return null;
    }

    @Override
    public boolean isExpandable(GvRef datum) {
        return false;
    }

    @Override
    public GvDataList<GvRef> getGridData() {
        return mCache;
    }

    @Override
    public GvDataType<GvRef> getGvDataType() {
        return mCache.getGvDataType();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        makeGridData();
    }

    @Override
    protected void onDetach() {
        mCache.unbind();
        super.onDetach();
    }

    void makeGridData() {
        mReference.toItemReferences(new ReviewListReference.ItemReferencesCallback<Value, Reference>() {
            @Override
            public void onItemReferences(IdableList<Reference> references) {
                makeGridData(references);
            }
        });
    }

    @NonNull
    private GvDataRefList<GvRef> newDataList() {
        return new GvDataRefList<>(mConverter.getOutputType(),
                new GvReviewId(mReference.getReviewId()));
    }

    public static class DataList<Value extends HasReviewId, GvRef extends GvDataRef<GvRef, Value,
            ?>>
            extends ViewerReviewData<Value, GvRef, ReviewItemReference<Value>, RefDataList<Value>> {

        public DataList(RefDataList<Value> reference,
                        GvConverterReferences<Value, GvRef, ReviewItemReference<Value>> converter) {
            super(reference, converter);
        }
    }

    public static class CommentList
            extends ViewerReviewData<DataComment, GvComment.Reference, RefComment, RefCommentList> {
        private final FactoryReference mReferenceFactory;
        private boolean mIsSplit = false;

        public CommentList(RefCommentList reference,
                           GvConverterReferences<DataComment, GvComment.Reference, RefComment>
                                   converter,
                           FactoryReference referenceFactory) {
            super(reference, converter);
            mReferenceFactory = referenceFactory;
        }

        public void setSplit(boolean split) {
            mIsSplit = split;
            makeGridData();
        }

        @Override
        protected void makeGridData() {
            if (mIsSplit) {
                toAllSentences();
            } else {
                toFirstSentences();
            }
        }

        private void toFirstSentences() {
            getReference().toItemReferences(new ReviewListReference
                    .ItemReferencesCallback<DataComment, RefComment>() {
                @Override
                public void onItemReferences(IdableList<RefComment> references) {
                    makeGridData(mReferenceFactory.newSentencesCollector(references).collectFirst());
                }
            });
        }

        private void toAllSentences() {
            getReference().toSentences(new RefComment.SentencesCallback() {
                @Override
                public void onSentenceReferences(IdableList<RefComment> references) {
                    makeGridData(references);
                }
            });
        }
    }
}
