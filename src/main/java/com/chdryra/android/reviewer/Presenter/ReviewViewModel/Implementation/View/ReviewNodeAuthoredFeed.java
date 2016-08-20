/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewFundamentals;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewInfo;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeAuthoredFeed extends ReviewNodeRepo implements DataReference.DereferenceCallback<NamedAuthor>{
    private ReviewFundamentals mMeta;

    public ReviewNodeAuthoredFeed(ReviewFundamentals meta,
                                  ApplicationInstance app,
                                  FactoryMdReference referenceFactory,
                                  FactoryReviewNode nodeFactory) {
        super(meta, app.getReviews(meta.getAuthorId()), referenceFactory, nodeFactory);
        mMeta = meta;
        app.getUsersManager().getAuthorsRepository().getName(meta.getAuthorId()).dereference(this);
    }

    @Override
    public void onDereferenced(@Nullable NamedAuthor data, CallbackMessage message) {
        if(data != null && !message.isError()) {
            ReviewId id = mMeta.getReviewId();
            DatumSubject subject = new DatumSubject(id, data.getName() + "'s feed");
            ReviewFundamentals meta = new ReviewInfo(id, subject,
                    mMeta.getRating(), mMeta.getAuthorId(), mMeta.getPublishDate());
            setMeta(meta);
        }
    }
}
