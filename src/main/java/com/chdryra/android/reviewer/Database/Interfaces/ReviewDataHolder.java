package com.chdryra.android.reviewer.Database.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewDataHolder {
    String getId();

    DataAuthor getAuthor();

    DataDate getPublishDate();

    String getSubject();

    float getRating();

    float getRatingWeight();

    Iterable<? extends DataComment> getComments();

    Iterable<? extends DataImage> getImages();

    Iterable<? extends DataFact> getFacts();

    Iterable<? extends DataLocation> getLocations();

    Iterable<Review> getCriteria();

    boolean isAverage();
}
