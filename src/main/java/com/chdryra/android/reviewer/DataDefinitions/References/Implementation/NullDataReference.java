/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullDataReference<T> implements DataReference<T> {
    @Override
    public void registerListener(InvalidationListener listener) {

    }

    @Override
    public void unregisterListener(InvalidationListener listener) {

    }

    @Override
    public void dereference(DereferenceCallback<T> callback) {
        callback.onDereferenced(null, CallbackMessage.error("Null reference"));
    }

    @Override
    public void bindToValue(ReferenceBinder<T> binder) {

    }

    @Override
    public void unbindFromValue(ReferenceBinder<T> binder) {

    }

    @Override
    public boolean isValidReference() {
        return false;
    }

    @Override
    public void invalidate() {

    }

    public static class Item<T extends HasReviewId> extends NullDataReference<T>
            implements ReviewItemReference<T> {
        @Override
        public ReviewId getReviewId() {
            return new DatumReviewId();
        }
    }

    public static class CommentList extends NullDataReference<IdableList<DataComment>>
            implements RefCommentList {
        @Override
        public void toSentences(RefComment.SentencesCallback callback) {
            callback.onSentenceReferences(new IdableDataList<RefComment>(getReviewId()));
        }

        @Override
        public void toItemReferences(ItemReferencesCallback<DataComment, RefComment> callback) {

        }

        @Override
        public ReviewItemReference<DataSize> getSize() {
            return new Item<>();
        }

        @Override
        public ReviewId getReviewId() {
            return new DatumReviewId();
        }

        @Override
        public void bindToItems(ListItemBinder<DataComment> binder) {

        }

        @Override
        public void unbindFromItems(ListItemBinder<DataComment> binder) {

        }
    }

    public static class List<T extends HasReviewId> extends NullDataReference<IdableList<T>>
            implements RefDataList<T> {
        @Override
        public void toItemReferences(ItemReferencesCallback<T, ReviewItemReference<T>> callback) {
            callback.onItemReferences(new IdableDataList<ReviewItemReference<T>>(getReviewId()));
        }

        @Override
        public ReviewItemReference<DataSize> getSize() {
            return new Item<>();
        }

        @Override
        public void bindToItems(ListItemBinder<T> binder) {

        }

        @Override
        public void unbindFromItems(ListItemBinder<T> binder) {

        }

        @Override
        public ReviewId getReviewId() {
            return new DatumReviewId();
        }
    }
}
