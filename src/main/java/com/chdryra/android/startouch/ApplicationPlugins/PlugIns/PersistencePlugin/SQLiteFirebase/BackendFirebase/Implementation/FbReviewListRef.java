/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewListReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewListRef<Value extends HasReviewId, Reference extends ReviewItemReference<Value>>
        extends FbListReference<Value, IdableList<Value>> implements ReviewListReference<Value, Reference> {
    private final ReviewId mId;
    private final ReviewItemReference<DataSize> mSizeReference;
    private final ListItemsReferencer<Value, Reference> mItemReferencer;

    FbReviewListRef(ReviewId id,
                    Firebase reference,
                    ReviewItemReference<DataSize> sizeReference,
                    IdableListConverter<Value> converter,
                    ListItemsReferencer<Value, Reference> itemReferencer) {
        super(reference, converter, converter.getItemConverter());
        mId = id;
        mSizeReference = sizeReference;
        mItemReferencer = itemReferencer;
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    public ReviewItemReference<DataSize> getSize() {
        return mSizeReference;
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<Value, Reference> callback) {
        mItemReferencer.toItemReferences(getReference(), mSizeReference,
                new ListItemsReferencer.Callback<Value, Reference>() {
            @Override
            public void onItemReferences(IdableList<Reference> references) {
                callback.onItemReferences(references);
            }
        });
    }
}
