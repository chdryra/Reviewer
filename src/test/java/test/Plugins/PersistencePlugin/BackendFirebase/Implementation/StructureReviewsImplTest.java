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
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;
import com.chdryra.android.testutils.RandomString;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import test.TestUtils.RandomReview;

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
public class StructureReviewsImplTest {
    private static final String REVIEWS = FirebaseStructure.REVIEWS;
    private static final int NUM = RandomReview.NUM;

    private Map<String, Object> mUpdatesMap;
    private DbUpdater.UpdateType mUpdateType;

    public void setUpdatesMap(Map<String, Object> updatesMap) {
        mUpdatesMap = updatesMap;
    }

    public void setUpdateType(DbUpdater.UpdateType updateType) {
        mUpdateType = updateType;
    }

    @Test
    public void testInsert() {
        testStructureReviews(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void testDelete() {
        testStructureReviews(DbUpdater.UpdateType.DELETE);
    }

    private void testStructureReviews(DbUpdater.UpdateType type) {
        setUpdateType(type);

        StructureReviews db = new StructureReviewsImpl(REVIEWS);
        ReviewDb reviewDb = randomReview();

        setUpdatesMap(db.getUpdatesMap(reviewDb, type));

        String reviewPath = path(REVIEWS, reviewDb.getReviewId());

        checkMapSize(reviewDb.size());
        checkKeyValue(path(reviewPath, "reviewId"), reviewDb.getReviewId());
        checkKeyValue(path(reviewPath, "subject"), reviewDb.getSubject());
        checkKeyValue(path(reviewPath, "rating", "rating"), reviewDb.getRating().getRating());
        checkKeyValue(path(reviewPath, "rating", "ratingWeight"), reviewDb.getRating().getRatingWeight());
        checkKeyValue(path(reviewPath, "author", "name"), reviewDb.getAuthor().getName());
        checkKeyValue(path(reviewPath, "author", "authorId"), reviewDb.getAuthor().getAuthorId());
        checkKeyValue(path(reviewPath, "publishDate"), reviewDb.getPublishDate());
        checkKeyValue(path(reviewPath, "average"), reviewDb.isAverage());

        checkKeyList(path(reviewPath, "criteria"), reviewDb.getCriteria(), getCriterionGetters());
        checkKeyList(path(reviewPath, "comments"), reviewDb.getComments(), getCommentGetters());
        checkKeyList(path(reviewPath, "facts"), reviewDb.getFacts(), getFactGetters());
        checkKeyList(path(reviewPath, "images"), reviewDb.getImages(), getImageDataGetters());
        checkKeyList(path(reviewPath, "locations"), reviewDb.getLocations(), getLocationGetters());
    }

    @NonNull
    private List<DataGetter<Criterion>> getCriterionGetters() {
        List<DataGetter<Criterion>> getters = new ArrayList<>();
        getters.add(new DataGetter<Criterion>("subject") {
            @Override
            public Object getData(Criterion item) {
                return item.getSubject();
            }
        });
        getters.add(new DataGetter<Criterion>("rating") {
            @Override
            public Object getData(Criterion item) {
                return item.getRating();
            }
        });
        
        return getters;
    }

    @NonNull
    private List<DataGetter<Comment>> getCommentGetters() {
        List<DataGetter<Comment>> getters = new ArrayList<>();
        getters.add(new DataGetter<Comment>("comment") {
            @Override
            public Object getData(Comment item) {
                return item.getComment();
            }
        });
        getters.add(new DataGetter<Comment>("headline") {
            @Override
            public Object getData(Comment item) {
                return item.isHeadline();
            }
        });

        return getters;
    }

    @NonNull
    private List<DataGetter<Fact>> getFactGetters() {
        List<DataGetter<Fact>> getters = new ArrayList<>();
        getters.add(new DataGetter<Fact>("label") {
            @Override
            public Object getData(Fact item) {
                return item.getLabel();
            }
        });
        getters.add(new DataGetter<Fact>("value") {
            @Override
            public Object getData(Fact item) {
                return item.getValue();
            }
        });
        getters.add(new DataGetter<Fact>("url") {
            @Override
            public Object getData(Fact item) {
                return item.isUrl();
            }
        });
        
        return getters;
    }

    @NonNull
    private List<DataGetter<ImageData>> getImageDataGetters() {
        List<DataGetter<ImageData>> getters = new ArrayList<>();
        getters.add(new DataGetter<ImageData>("bitmap") {
            @Override
            public Object getData(ImageData item) {
                return item.getBitmap();
            }
        });
        getters.add(new DataGetter<ImageData>("date") {
            @Override
            public Object getData(ImageData item) {
                return item.getDate();
            }
        });
        getters.add(new DataGetter<ImageData>("caption") {
            @Override
            public Object getData(ImageData item) {
                return item.getCaption();
            }
        });
        getters.add(new DataGetter<ImageData>("cover") {
            @Override
            public Object getData(ImageData item) {
                return item.isCover();
            }
        });

        return getters;
    }

    @NonNull
    private List<DataGetter<Location>> getLocationGetters() {
        List<DataGetter<Location>> getters = new ArrayList<>();
        getters.add(new DataGetter<Location>("latLng") {
            @Override
            public Object getData(Location item) {
                return getLatLngGetters(item.getLatLng());
            }
        });
        getters.add(new DataGetter<Location>("name") {
            @Override
            public Object getData(Location item) {
                return item.getName();
            }
        });

        return getters;
    }

    private List<DataGetter<LatitudeLongitude>> getLatLngGetters(LatitudeLongitude latLng) {
        List<DataGetter<LatitudeLongitude>> getters = new ArrayList<>();
        getters.add(new DataGetter<LatitudeLongitude>("latitude") {
            @Override
            public Object getData(LatitudeLongitude item) {
                return item.getLatitude();
            }
        });
        getters.add(new DataGetter<LatitudeLongitude>("longitude") {
            @Override
            public Object getData(LatitudeLongitude item) {
                return item.getLongitude();
            }
        });

        return getters;
    }

    private <T> void checkKeyList(String key, List<T> reviewItems, List<DataGetter<T>> getters) {
        boolean isDelete = mUpdateType == DbUpdater.UpdateType.DELETE;
        assertThat(mUpdatesMap.containsKey(key), is(true));
        if(isDelete) {
            assertThat(mUpdatesMap.get(key), nullValue());
            return;
        }

        try {
            List<Object> list = (List<Object>) mUpdatesMap.get(key);
            assertThat(list, not(nullValue()));
            assertThat(list.size(), is(reviewItems.size()));
            for(int i = 0; i < list.size(); ++i) {
                Map<String, Object> objectMap = (Map<String, Object>) list.get(i);
                T item = reviewItems.get(i);
                checkMapSize(objectMap, getters.size());
                checkMapping(objectMap, item, getters);
            }
        } catch (ClassCastException e) {
            fail();
        }
    }
    abstract class DataGetter<In, Out> {
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

    private <In, Out> void checkMapping(Map<String, Object> objectMap,
                                        In object,
                                        List<DataGetter<In, Out>> objectDataGetters) {
        for(DataGetter<In, Out> getter : objectDataGetters) {
            String key = getter.getDataName();
            Out value = getter.getData(object);

            List<DataGetter<Out, ?>> valueGetters = getter.getOutGetters();
            if(valueGetters.size() == 0) {
                //Check mapping is correct
                assertThat(objectMap.get(key), isValue(value));
            } else {
                //Recursion
                try {
                    Map<String, Object> valueMap = (Map<String, Object>) objectMap.get(key);
                    checkMapping(valueMap, value, valueGetters); //***Error is here***//
                } catch (ClassCastException e) {
                    fail();
                }
            }
        }
    }

    private void checkMapSize(int size) {
        checkMapSize(mUpdatesMap, size);
    }

    private void checkMapSize(Map<String, Object> map, int size) {
        assertThat(map, not(nullValue()));
        assertThat(map.size(), is(size));
    }

    private void checkKeyValue(String key, Object value) {
        checkKeyValue(mUpdatesMap, key, value);
    }

    private void checkKeyValue(Map<String, Object> map, String key, Object value) {
        assertThat(map.containsKey(key), is(true));
        assertThat(map.get(key), isValue(value));
    }

    private Matcher<Object> isValue(Object value) {
        boolean isDelete = mUpdateType == DbUpdater.UpdateType.DELETE;
        return isDelete ? nullValue() : is(value);
    }

    @NonNull
    private ReviewDb randomReview() {
        return new ReviewDb(RandomReview.nextReview(), RandomString.nextWordArray(NUM));
    }

    private String path(String root, String... elements) {
        return Path.path(root, elements);
    }
}
