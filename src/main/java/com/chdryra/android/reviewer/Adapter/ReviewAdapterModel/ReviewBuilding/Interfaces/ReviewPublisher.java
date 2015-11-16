package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthor;
import com.chdryra.android.reviewer.Interfaces.Data.DataDate;

/**
 * Created by: Rizwan Choudrey
 * On: 18/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewPublisher {
    DataAuthor getAuthor();

    DataDate getDate();

    int getPublishedIndex();
}
