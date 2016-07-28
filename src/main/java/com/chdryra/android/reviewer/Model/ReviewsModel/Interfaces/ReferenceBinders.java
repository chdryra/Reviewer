/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReferenceBinders {
    interface CoverBinder extends ReferenceBinder<DataImage> {
    }

    interface CriteriaBinder extends ReferenceBinder<IdableList<? extends DataCriterion>> {
    }

    interface CommentsBinder extends ReferenceBinder<IdableList<? extends DataComment>> {
    }

    interface FactsBinder extends ReferenceBinder<IdableList<? extends DataFact>> {
    }

    interface LocationsBinder extends ReferenceBinder<IdableList<? extends DataLocation>> {
    }

    interface ImagesBinder extends ReferenceBinder<IdableList<? extends DataImage>> {
    }

    interface TagsBinder extends ReferenceBinder<IdableList<? extends DataTag>> {
    }

    interface SizeBinder extends ReferenceBinder<DataSize> {

    }
}
