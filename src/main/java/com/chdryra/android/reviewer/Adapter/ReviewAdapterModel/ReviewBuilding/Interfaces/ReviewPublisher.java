package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;

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
