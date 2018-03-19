package test.TestUtils;

import android.graphics.Bitmap;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterionList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDateList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvFactList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocationList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverviewList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatformList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSubjectList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvText;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrlList;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.lang3.ArrayUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 22/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataMocker {
    public static final GvDataType[] DATATYPES = {GvComment.TYPE,
            GvFact.TYPE, GvImage.TYPE, GvLocation.TYPE, GvUrl.TYPE, GvTag.TYPE,
            GvCriterion.TYPE};
    public static final GvDataType[] TYPES = ArrayUtils.addAll(DATATYPES,
            GvReviewOverview.TYPE);
    private static final RandomString STRING_GENERATOR = new RandomString();
    private static final Random RAND = new Random();

    //Just a convenient method even if it uses GvType.....
    public static GvDataList getData(GvDataType dataType, int size, boolean withId) {
        if (dataType == GvComment.TYPE) {
            return newCommentList(size, withId);
        } else if (dataType == GvFact.TYPE) {
            return newFactList(size, withId);
        } else if (dataType == GvImage.TYPE) {
            return newImageList(size, withId);
        } else if (dataType == GvLocation.TYPE) {
            return newLocationList(size, withId);
        } else if (dataType == GvUrl.TYPE) {
            return newUrlList(size, withId);
        } else if (dataType == GvTag.TYPE) {
            return newTagList(size, withId);
        } else if (dataType == GvCriterion.TYPE) {
            return newChildList(size, withId);
        } else if (dataType == GvReviewOverview.TYPE) {
            return newReviewList(size, withId);
        } else if (dataType == GvAuthor.TYPE) {
            return newAuthorList(size, withId);
        } else if (dataType == GvSubject.TYPE) {
            return newSubjectList(size, withId);
        } else {
            return null;
        }
    }

    public static GvDataList getData(GvDataType dataType, int size) {
        return getData(dataType, size, false);
    }

    //Just a convenient method even if it uses GvType.....
    public static GvData getDatum(GvDataType dataType, boolean withId) {
        if (dataType == GvComment.TYPE) {
            return newComment(getId(withId));
        } else if (dataType == GvFact.TYPE) {
            return newFact(getId(withId));
        } else if (dataType == GvImage.TYPE) {
            return newImage(getId(withId));
        } else if (dataType == GvLocation.TYPE) {
            return newLocation(getId(withId));
        } else if (dataType == GvUrl.TYPE) {
            return newUrl(getId(withId));
        } else if (dataType == GvTag.TYPE) {
            return newTag(getId(withId));
        } else if (dataType == GvCriterion.TYPE) {
            return newChild(getId(withId));
        } else if (dataType == GvReviewOverview.TYPE) {
            return newReviewOverview(getId(withId));
        } else if (dataType == GvAuthor.TYPE) {
            return newAuthor(getId(withId));
        } else if (dataType == GvSubject.TYPE) {
            return newSubject(getId(withId));
        } else {
            return null;
        }
    }

    public static GvData getDatum(GvDataType dataType) {
        return getDatum(dataType, false);
    }

    public static GvCommentList newCommentList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvCommentList list = new GvCommentList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newComment(id));
        }

        return list;
    }

    public static GvImageList newImageList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvImageList list = new GvImageList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newImage(id));
        }

        return list;
    }

    public static GvLocationList newLocationList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvLocationList list = new GvLocationList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newLocation(id));
        }

        return list;
    }

    public static GvFactList newFactList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvFactList list = new GvFactList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newFact(id));
        }

        return list;
    }

    public static GvUrlList newUrlList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvUrlList list = new GvUrlList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newUrl(id));
        }

        return list;
    }

    public static GvTagList newTagList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvTagList list = new GvTagList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newTag(id));
        }

        return list;
    }

    public static GvCriterionList newChildList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvCriterionList list = new GvCriterionList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newChild(id));
        }

        return list;
    }

    public static GvReviewOverviewList newReviewList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvReviewOverviewList list = new GvReviewOverviewList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newReviewOverview(id));
        }

        return list;
    }

    public static GvAuthorList newAuthorList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvAuthorList list = new GvAuthorList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newAuthor(id));
        }

        return list;
    }

    public static GvSubjectList newSubjectList(int size, boolean withId) {
        GvReviewId id = getId(withId);
        GvSubjectList list = new GvSubjectList(id);
        for (int i = 0; i < size; ++i) {
            list.add(newSubject(id));
        }

        return list;
    }

    public static GvComment newComment(GvReviewId id) {
        String comment = STRING_GENERATOR.nextParagraph();
        boolean isHeadline = RAND.nextBoolean();

        return new GvComment(id, comment, isHeadline);
    }

    public static GvImageList.GvImage newImage(GvReviewId id) {
        Bitmap bitmap = BitmapMocker.nextBitmap(RAND.nextBoolean());
        Date date = RandomDate.nextDate();
        String caption = RandomString.nextSentence();
        Boolean isCover = RAND.nextBoolean();

        return new GvImageList.GvImage(id, bitmap, date, caption, isCover);
    }

    public static GvLocationList.GvLocation newLocation(GvReviewId id) {
        LatLng latLng = RandomLatLng.nextLatLng();
        String name = RandomString.nextWord();

        return new GvLocationList.GvLocation(id, latLng, name);
    }

    public static GvFactList.GvFact newFact(GvReviewId id) {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();

        return new GvFactList.GvFact(id, label, value);
    }

    public static GvUrlList.GvUrl newUrl(GvReviewId id) {
        String label = RandomString.nextWord();
        String urlString = "http://www." + RandomString.nextWord() + ".co.uk";

        try {
            URL url = new URL(urlString);
            return new GvUrlList.GvUrl(id, label, url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new GvUrlList.GvUrl();
        }
    }

    public static GvCriterionList.GvCriterion newChild(GvReviewId id) {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();

        return new GvCriterionList.GvCriterion(id, subject, rating);
    }

    public static GvTagList.GvTag newTag(GvReviewId id) {
        return new GvTagList.GvTag(id, RandomString.nextWord());
    }

    public static GvText newText(GvReviewId id) {
        return new GvText(id, RandomString.nextWord());
    }

    public static GvSubjectList.GvSubject newSubject(GvReviewId id) {
        return new GvSubjectList.GvSubject(id, RandomString.nextWord());
    }

    public static GvAuthorList.GvAuthor newAuthor(GvReviewId id) {
        Author author = RandomAuthor.nextAuthor();
        return new GvAuthorList.GvAuthor(id, author.getName(), author.getUserId().toString());
    }

    public static GvDateList.GvDate newDate() {
        return new GvDateList.GvDate(RandomDate.nextDate());
    }

    public static GvReviewOverviewList.GvReviewOverview newReviewOverview(GvReviewId parentId) {
        Author author = RandomAuthor.nextAuthor();
        Date date = RandomDate.nextDate();
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        Bitmap bitmap = BitmapMocker.nextBitmap(RAND.nextBoolean());
        String comment = RandomString.nextSentence();
        ArrayList<String> locations = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            locations.add(RandomString.nextWord());
        }
        GvReviewId id = GvReviewId.getId(RandomReviewId.nextId().toString());

        return new GvReviewOverviewList.GvReviewOverview(parentId, id.toString(), author, date,
                subject,
                rating, bitmap, comment, locations);
    }

    public static GvSocialPlatformList.GvSocialPlatform newSocialPlatform() {
        return new GvSocialPlatformList.GvSocialPlatform(RandomString.nextWord(),
                RAND.nextInt(100) ^ 2);
    }

    private static GvReviewId getId(boolean withId) {
        return withId ? GvReviewId.getId(RandomReviewId.nextId().toString()) : null;
    }
}
