/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories;



import android.webkit.URLUtil;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Comment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Criterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Fact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ImageData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.LatitudeLongitude;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Location;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Rating;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUrl;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewInfo;
import com.google.android.gms.maps.model.LatLng;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 14/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendDataConverter {
    private interface ItemConverter<T1, T2> {
        T2 convertItem(String reviewId, T1 item);
    }

    public DataSubject convert(String reviewId, String subject) {
        return new DatumSubject(convert(reviewId), subject);
    }

    public DataRating convert(String reviewId, Rating rating) {
        return new DatumRating(convert(reviewId), (float) rating.getRating(), (int) rating.getRatingWeight());
    }


    public DataAuthor convert(Author author) {
        return new DatumAuthor(author.getName(), new DatumAuthorId(author.getAuthorId()));
    }

    public DataAuthorReview convert(String reviewId, Author author) {
        return new DatumAuthorReview(convert(reviewId), author.getName(), new DatumAuthorId(author.getAuthorId()));
    }

    public DataDateReview convert(String reviewId, long time) {
        return new DatumDateReview(convert(reviewId), time);
    }

    public IdableList<DataImage> convertImages(String reviewId, List<ImageData> list) {
        return getList(reviewId, list, new ItemConverter<ImageData, DataImage>() {
            @Override
            public DataImage convertItem(String reviewId, ImageData item) {
                return convert(reviewId, item);
            }
        });
    }
    
    public DataImage convert(String reviewId, ImageData item) {
        ReviewId id = convert(reviewId);
        return item == null ? new DatumImage(id) : new DatumImage(id, ImageData.asBitmap(item.getBitmap()),
                new DatumDateReview(id, item.getDate()), item.getCaption(), item.isCover());
    }

    public IdableList<DataCriterion> convertCriteria(String reviewId, List<Criterion> list) {
        return getList(reviewId, list, new ItemConverter<Criterion, DataCriterion>() {
            @Override
            public DataCriterion convertItem(String reviewId, Criterion item) {
                return convert(reviewId, item);
            }
        });
    }

    public DataCriterion convert(String reviewId, Criterion item) {
        return new DatumCriterion(convert(reviewId), item.getSubject(), (float) item.getRating());
    }

    public IdableList<DataComment> convertComments(String reviewId, List<Comment> list) {
        return getList(reviewId, list, new ItemConverter<Comment, DataComment>() {
            @Override
            public DataComment convertItem(String reviewId, Comment item) {
                return convert(reviewId, item);
            }
        });
    }

    public DataComment convert(String reviewId, Comment item) {
        return new DatumComment(convert(reviewId), item.getComment(),item.isHeadline());
    }

    public IdableList<DataFact> convertFacts(String reviewId, List<Fact> list) {
        return getList(reviewId, list, new ItemConverter<Fact, DataFact>() {
            @Override
            public DataFact convertItem(String reviewId, Fact item) {
                return convert(reviewId, item);
            }
        });
    }
    
    public DataFact convert(String reviewId, Fact item) {
        ReviewId id = convert(reviewId);
        if(item.isUrl()) {
            String urlGuess = URLUtil.guessUrl(item.getValue());
            try {
                return new DatumUrl(id, item.getLabel(), new URL(urlGuess));
            } catch (MalformedURLException e1) {
                return new DatumFact(id, item.getLabel(), item.getValue());
            }
        } else {
            return new DatumFact(id, item.getLabel(), item.getValue());
        }
    }

    public IdableList<DataLocation> convertLocations(String reviewId, List<Location> list) {
        return getList(reviewId, list, new ItemConverter<Location, DataLocation>() {
            @Override
            public DataLocation convertItem(String reviewId, Location item) {
                return convert(reviewId, item);
            }
        });
    }

    public DataLocation convert(String reviewId, Location item) {
        LatitudeLongitude latLng = item.getLatLng();
        return new DatumLocation(convert(reviewId),
                new LatLng(latLng.getLatitude(), latLng.getLongitude()), item.getName());
    }
    
    private <T1, T2 extends HasReviewId> IdableList<T2> getList(String id,
                                                                List<T1> list,
                                                                ItemConverter<T1, T2> converter) {
        IdableList<T2> data = new IdableDataList<>(convert(id));
        for (T1 item : list) {
            data.add(converter.convertItem(id, item));
        }

        return data;
    }

    public ReviewId convert(String reviewId) {
        return new DatumReviewId(reviewId);
    }

    public ReviewInfo convert(ReviewListEntry entry) {
        String id = entry.getReviewId();
        return new ReviewInfo(convert(id), convert(id, entry.getSubject()),
                convert(id, entry.getRating()), convert(id, entry.getAuthor()),
                convert(id, entry.getPublishDate()));
    }
}
