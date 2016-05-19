/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import android.support.annotation.NonNull;

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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation
        .StructureReviewsImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureReviewsImplTest extends StructureTestBasic<ReviewDb>{
    private static final String REVIEWS = FirebaseStructure.REVIEWS;

    @Before
    public void setUp() {
        setStructure(new StructureReviewsImpl(REVIEWS));
    }

    @Override
    protected void testStructure() {
        ReviewDb reviewDb = setData(randomReview());

        String reviewPath = path(REVIEWS, reviewDb.getReviewId());

        checkMapSize(reviewDb.size());
        checkKeyValue(path(reviewPath, "reviewId"), reviewDb.getReviewId());
        checkKeyValue(path(reviewPath, "subject"), reviewDb.getSubject());
        checkKeyValue(path(reviewPath, "rating", "rating"), reviewDb.getRating().getRating());
        checkKeyValue(path(reviewPath, "rating", "ratingWeight"), reviewDb.getRating()
                .getRatingWeight());
        checkKeyValue(path(reviewPath, "author", "name"), reviewDb.getAuthor().getName());
        checkKeyValue(path(reviewPath, "author", "authorId"), reviewDb.getAuthor().getAuthorId());
        checkKeyValue(path(reviewPath, "publishDate"), reviewDb.getPublishDate());
        checkKeyValue(path(reviewPath, "average"), reviewDb.isAverage());
        checkKeyValue(path(reviewPath, "tags"), reviewDb.getTags());

        checkKeyList(path(reviewPath, "criteria"), reviewDb.getCriteria(), getCriterionGetters());
        checkKeyList(path(reviewPath, "comments"), reviewDb.getComments(), getCommentGetters());
        checkKeyList(path(reviewPath, "facts"), reviewDb.getFacts(), getFactGetters());
        checkKeyList(path(reviewPath, "images"), reviewDb.getImages(), getImageDataGetters());
        checkKeyList(path(reviewPath, "locations"), reviewDb.getLocations(), getLocationGetters());
    }

    @NonNull
    private List<DataGetter<Criterion, ?>> getCriterionGetters() {
        List<DataGetter<Criterion, ?>> getters = new ArrayList<>();
        getters.add(new DataGetter<Criterion, String>("subject") {
            @Override
            public String getData(Criterion item) {
                return item.getSubject();
            }
        });
        getters.add(new DataGetter<Criterion, Double>("rating") {
            @Override
            public Double getData(Criterion item) {
                return item.getRating();
            }
        });

        return getters;
    }

    @NonNull
    private List<DataGetter<Comment, ?>> getCommentGetters() {
        List<DataGetter<Comment, ?>> getters = new ArrayList<>();
        getters.add(new DataGetter<Comment, String>("comment") {
            @Override
            public String getData(Comment item) {
                return item.getComment();
            }
        });
        getters.add(new DataGetter<Comment, Boolean>("headline") {
            @Override
            public Boolean getData(Comment item) {
                return item.isHeadline();
            }
        });

        return getters;
    }

    @NonNull
    private List<DataGetter<Fact, ?>> getFactGetters() {
        List<DataGetter<Fact, ?>> getters = new ArrayList<>();
        getters.add(new DataGetter<Fact, String>("label") {
            @Override
            public String getData(Fact item) {
                return item.getLabel();
            }
        });
        getters.add(new DataGetter<Fact, String>("value") {
            @Override
            public String getData(Fact item) {
                return item.getValue();
            }
        });
        getters.add(new DataGetter<Fact, Boolean>("url") {
            @Override
            public Boolean getData(Fact item) {
                return item.isUrl();
            }
        });

        return getters;
    }

    @NonNull
    private List<DataGetter<ImageData, ?>> getImageDataGetters() {
        List<DataGetter<ImageData, ?>> getters = new ArrayList<>();
        getters.add(new DataGetter<ImageData, String>("bitmap") {
            @Override
            public String getData(ImageData item) {
                return item.getBitmap();
            }
        });
        getters.add(new DataGetter<ImageData, Long>("date") {
            @Override
            public Long getData(ImageData item) {
                return item.getDate();
            }
        });
        getters.add(new DataGetter<ImageData, String>("caption") {
            @Override
            public String getData(ImageData item) {
                return item.getCaption();
            }
        });
        getters.add(new DataGetter<ImageData, Boolean>("cover") {
            @Override
            public Boolean getData(ImageData item) {
                return item.isCover();
            }
        });

        return getters;
    }

    @NonNull
    private List<DataGetter<Location, ?>> getLocationGetters() {
        List<DataGetter<Location, ?>> getters = new ArrayList<>();
        getters.add(new DataGetter<Location, LatitudeLongitude>("latLng", getLatLngGetters()) {
            @Override
            public LatitudeLongitude getData(Location item) {
                return item.getLatLng();
            }
        });
        getters.add(new DataGetter<Location, String>("name") {
            @Override
            public String getData(Location item) {
                return item.getName();
            }
        });

        return getters;
    }

    private List<DataGetter<LatitudeLongitude, ?>> getLatLngGetters() {
        List<DataGetter<LatitudeLongitude, ?>> getters = new ArrayList<>();
        getters.add(new DataGetter<LatitudeLongitude, Double>("latitude") {
            @Override
            public Double getData(LatitudeLongitude item) {
                return item.getLatitude();
            }
        });
        getters.add(new DataGetter<LatitudeLongitude, Double>("longitude") {
            @Override
            public Double getData(LatitudeLongitude item) {
                return item.getLongitude();
            }
        });

        return getters;
    }

    private <T> void checkKeyList(String key, List<T> reviewItems, List<DataGetter<T, ?>> getters) {
        boolean isDelete = mUpdateType == DbUpdater.UpdateType.DELETE;
        assertThat(mUpdatesMap.containsKey(key), is(true));
        if (isDelete) {
            assertThat(mUpdatesMap.get(key), nullValue());
            return;
        }

        try {
            List<Object> list = (List<Object>) mUpdatesMap.get(key);
            assertThat(list, not(nullValue()));
            assertThat(list.size(), is(reviewItems.size()));
            for (int i = 0; i < list.size(); ++i) {
                Map<String, Object> objectMap = (Map<String, Object>) list.get(i);
                T item = reviewItems.get(i);
                checkMapSize(objectMap, getters.size());
                checkMapping(objectMap, item, getters);
            }
        } catch (ClassCastException e) {
            fail();
        }
    }

    private <In> void checkMapping(Map<String, Object> objectMap,
                                   In object,
                                   List<DataGetter<In, ?>> objectDataGetters) {
        for (DataGetter<In, ?> getter : objectDataGetters) {
            checkMappingForGetter(objectMap, object, getter);
        }
    }

    private <In, Out> void checkMappingForGetter(Map<String, Object> objectMap,
                                                 In object,
                                                 DataGetter<In, Out> getter) {
        String key = getter.getDataName();
        Out value = getter.getData(object);

        List<DataGetter<Out, ?>> valueGetters = getter.getOutGetters();
        if (valueGetters.size() == 0) {
            checkKeyValue(objectMap, key, value);
        } else {
            try {
                Map<String, Object> valueMap = (Map<String, Object>) objectMap.get(key);
                checkMapping(valueMap, value, valueGetters);
            } catch (ClassCastException e) {
                fail();
            }
        }
    }

    private abstract class DataGetter<In, Out> {
        private String mDataName;
        private List<DataGetter<Out, ?>> mOutGetters;

        public abstract Out getData(In item);

        public DataGetter(String dataName) {
            mDataName = dataName;
            mOutGetters = new ArrayList<>();
        }

        public DataGetter(String dataName, List<DataGetter<Out, ?>> outGetters) {
            mDataName = dataName;
            mOutGetters = outGetters;
        }

        public String getDataName() {
            return mDataName;
        }

        public List<DataGetter<Out, ?>> getOutGetters() {
            return mOutGetters;
        }
    }
}
