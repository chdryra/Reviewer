/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class WrapperCommentListRef extends StaticListReferenceBasic<DataComment, CommentRef>
        implements CommentListRef {

    private final FactoryReferences mFactory;

    public WrapperCommentListRef(IdableList<DataComment> value, FactoryReferences factory) {
        super(value);
        mFactory = factory;
    }

    @Override
    public void toSentences(final CommentRef.SentencesCallback callback) {
        toItemReferences(new ItemReferencesCallback<DataComment, CommentRef>() {
            @Override
            public void onItemReferences(IdableList<CommentRef> commentRefs) {
                mFactory.newSentencesCollector(commentRefs).collectAll(callback);
            }
        });
    }

    @Override
    protected CommentRef newStaticReference(DataComment item) {
        return mFactory.newWrapper(item, null);
    }
}
