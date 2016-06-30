/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.BindersManagerMeta;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.BindersManagerReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MetaBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryBinders {
    public ReferenceBinder bindTo(ReviewReference reference) {
        return new ReferenceBinder(reference);
    }

    public MetaBinder bindTo(MetaReference reference) {
        return new MetaBinder(reference);
    }

    public BindersManagerReference newReferenceBindersManager() {
        return new BindersManagerReference();
    }

    public BindersManagerMeta newMetaBindersManager() {
        return new BindersManagerMeta();
    }
}
