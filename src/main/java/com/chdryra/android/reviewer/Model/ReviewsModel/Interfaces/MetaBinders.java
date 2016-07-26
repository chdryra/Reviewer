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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface MetaBinders extends ReferenceBinders{
    interface ReviewsBinder extends ValueBinder<IdableList<ReviewReference>> {
    }

    interface SubjectsBinder extends ValueBinder<IdableList<? extends DataSubject>> {

    }

    interface AuthorsBinder extends ValueBinder<IdableList<? extends DataAuthorId>> {

    }

    interface DatesBinder extends ValueBinder<IdableList<? extends DataDate>> {

    }
}
