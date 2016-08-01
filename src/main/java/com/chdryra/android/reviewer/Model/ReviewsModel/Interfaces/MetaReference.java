/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface MetaReference extends ReviewReference {
    IdableList<ReviewReference> getReviews();

    IdableList<ReviewDataReference<DataSubject>> getSubjects();

    IdableList<ReviewDataReference<DataAuthorId>> getAuthorIds();

    IdableList<ReviewDataReference<DataDate>> getDates();

    ReviewDataReference<DataSize> getNumReviews();

    ReviewDataReference<DataSize> getNumSubjects();

    ReviewDataReference<DataSize> getNumAuthors();

    ReviewDataReference<DataSize> getNumDates();
}
