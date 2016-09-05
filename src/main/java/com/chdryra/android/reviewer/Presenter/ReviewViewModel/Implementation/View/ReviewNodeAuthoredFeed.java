/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewInfo;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeAuthoredFeed extends ReviewNodeRepo implements ReferenceBinder<NamedAuthor> {
    private final DataReviewInfo mMeta;

    public ReviewNodeAuthoredFeed(DataReviewInfo meta,
                                  ApplicationInstance app,
                                  FactoryMdReference referenceFactory,
                                  FactoryReviewNode nodeFactory) {
        super(meta, app.getReviews(meta.getAuthorId()), referenceFactory, nodeFactory);
        mMeta = meta;
        app.getUsersManager().getAuthorsRepository().getName(meta.getAuthorId()).bindToValue(this);
    }

    @Override
    public void onReferenceValue(NamedAuthor value) {
        setTitle(new DatumSubject(mMeta.getReviewId(), value.getName() + "'s feed"));
    }

    private void setTitle(DatumSubject subject) {
        DataReviewInfo meta = new ReviewInfo(mMeta.getReviewId(), subject,
                mMeta.getRating(), mMeta.getAuthorId(), mMeta.getPublishDate());
        setMeta(meta);
    }

    @Override
    public void onInvalidated(DataReference<NamedAuthor> reference) {
        setTitle(new DatumSubject(mMeta.getReviewId(), "feed"));
    }
}
