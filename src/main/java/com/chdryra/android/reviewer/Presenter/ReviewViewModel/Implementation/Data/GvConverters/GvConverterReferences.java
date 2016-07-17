/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReferenceList;


/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterReferences
        extends GvConverterBasic<ReviewReference, GvReference, GvReferenceList>
        implements DataConverter<ReviewReference, GvReference, GvReferenceList> {
    private TagsManager mTagsManager;
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;

    public GvConverterReferences(TagsManager tagsManager,
                                 GvConverterImages converterImages,
                                 GvConverterComments converterComments,
                                 GvConverterLocations converterLocations) {
        super(GvReferenceList.class);
        mTagsManager = tagsManager;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
    }

    @Override
    public GvReference convert(ReviewReference review, ReviewId parentId) {
        return new GvReference(review, mConverterComments, mConverterLocations);
    }

    @Override
    public GvReference convert(ReviewReference review) {
        return convert(review, null);
    }

    @Override
    public GvReferenceList convert(IdableList<? extends ReviewReference> data) {
        GvReferenceList list = new GvReferenceList(newId(data.getReviewId()));
        for(ReviewReference datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }
}
