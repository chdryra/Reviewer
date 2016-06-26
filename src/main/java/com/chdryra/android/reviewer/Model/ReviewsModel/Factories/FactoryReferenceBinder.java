/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReferenceBinder {
    public ReferenceBinder newBinder(ReviewReference reference) {
        return new ReferenceBinder(reference);
    }
}
