/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.TestUtils;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Comment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Criterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Fact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ImageData;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.LatitudeLongitude;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Location;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ProfileAuthor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.startouch.Authentication.Implementation.AuthorProfileDefault;
import com.chdryra.android.testutils.RandomString;

import java.util.ArrayList;
import java.util.List;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomDataDate;
import test.TestUtils.RandomReview;

/**
 * Created by: Rizwan Choudrey
 * On: 19/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendTestUtils {
    public static final int NUM_TAGS = RandomReview.NUM;

    @NonNull
    public static User randomUser() {
        return new User(RandomString.nextWord(), RandomString.nextWord(), randomProfile());
    }

    @NonNull
    public static ProfileAuthor randomProfile() {
        return new ProfileAuthor(new AuthorProfileDefault(RandomAuthor.nextAuthor(),
                RandomDataDate.nextDateTime()));
    }

    @NonNull
    public static ReviewDb randomReview() {
        return new ReviewDb(RandomReview.nextReview(), RandomString.nextWordArray(NUM_TAGS));
    }

    @NonNull
    public static List<DataGetter<Criterion, ?>> criterionGetters() {
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
    public static List<DataGetter<Comment, ?>> commentGetters() {
        List<DataGetter<Comment, ?>> getters = new ArrayList<>();
        getters.add(new DataGetter<Comment, String>("comment") {
            @Override
            public String getData(Comment item) {
                return item.getSentences();
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
    public static List<DataGetter<Fact, ?>> factGetters() {
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
    public static List<DataGetter<ImageData, ?>> imageDataGetters() {
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
    public static List<DataGetter<Location, ?>> locationGetters() {
        List<DataGetter<Location, ?>> getters = new ArrayList<>();
        getters.add(new DataGetter<Location, LatitudeLongitude>("latLng", latLngGetters()) {
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

    private static List<DataGetter<LatitudeLongitude, ?>> latLngGetters() {
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
}
