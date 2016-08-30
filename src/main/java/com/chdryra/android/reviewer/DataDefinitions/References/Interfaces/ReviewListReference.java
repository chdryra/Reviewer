/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewListReference<Value extends HasReviewId,
        Reference extends ReviewItemReference<Value>>
        extends ListReference<Value, IdableList<Value>>, HasReviewId{

    interface ItemReferencesCallback<Value extends HasReviewId,
            Reference extends ReviewItemReference<Value>> {
        void onItemReferences(IdableList<Reference> references);
    }

    void toItemReferences(ItemReferencesCallback<Value, Reference> callback);

    ReviewItemReference<DataSize> getSize();
}
