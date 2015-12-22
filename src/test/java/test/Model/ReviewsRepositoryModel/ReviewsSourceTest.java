package test.Model.ReviewsRepositoryModel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.SortableListImpl;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters
        .ConverterMd;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableItems;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableItems;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewsSourceImpl;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation
        .TreeFlattenerImpl;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.AuthorId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsSource;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewId;
import test.TestUtils.StaticReviewsRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 22/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewsSourceTest {
    private static final int NUM_REVIEWS = 5;
    private static final DataAuthor AUTHOR = new DatumAuthor("Author", AuthorId.generateId());
    private static final Random RAND = new Random();

    @Mock
    private TagsManager mMockManager;
    private IdableItems<Review> mReviews;
    private ReviewsRepository mRepo;
    private ReviewsSource mSource;

    @Before
    public void setUp() {
        mReviews = new IdableDataCollection<>();
        for (int i = 0; i < NUM_REVIEWS; ++i) {
            mReviews.add(RandomReview.nextReview());
        }
        mRepo = new StaticReviewsRepository(mReviews, mMockManager);
        mSource = new ReviewsSourceImpl(mRepo, getReviewFactory(), getTreeFlattener());
    }

    @Test
    public void asMetaReview_ReviewId_ReturnsNullIfNoReviewFound() {
        assertThat(mSource.asMetaReview(RandomReviewId.nextReviewId()), is(nullValue()));
    }

    @Test
    public void asMetaReview_ReviewId_ReturnsMetaReviewWithOneDescendantOnly() {
        Review review = getRandomReview();
        assertSingleDescendant(mSource.asMetaReview(review.getReviewId()));
    }

    @Test
    public void asMetaReview_ReviewId_ReturnsMetaReviewWithCorrectChildNode() {
        Review expectedReview = getRandomReview();
        assertCorrectReview(mSource.asMetaReview(expectedReview.getReviewId()), expectedReview);
    }

    @Test
    public void asMetaReview_Data_ReturnsNullIfNoReviewFound() {
        VerboseDatum datum = new VerboseDatum(RandomReviewId.nextReviewId());
        assertThat(mSource.asMetaReview(datum, ""), is(nullValue()));
    }

    @Test
    public void asMetaReview_Data_ReturnsMetaReviewWithOneDescendantOnlyForDatum() {
        Review review = getRandomReview();
        VerboseDatum datum = new VerboseDatum(review.getReviewId());
        assertSingleDescendant(mSource.asMetaReview(datum, ""));
    }

    @Test
    public void asMetaReview_Data_ReturnsMetaReviewWithCorrectChildNodeForDatum() {
        Review expectedReview = getRandomReview();
        VerboseDatum datum = new VerboseDatum(expectedReview.getReviewId());
        assertCorrectReview(mSource.asMetaReview(datum, ""), expectedReview);
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithOneDescendantOnlyForDataCollectionWithReviewIdInSource
            () {
        ReviewId id = getRandomReview().getReviewId();
        VerboseCollection collection = new VerboseCollection(id, RandomString.nextWord());
        for (Review review : mReviews) {
            collection.add(new VerboseDatum(RandomReviewId.nextReviewId(),
                    review.getSubject().getSubject()));
        }

        assertSingleDescendant(mSource.asMetaReview(collection, ""));
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithCorrectChildNodeForDataCollectionWithReviewIdInSource() {
        Review expectedReview = getRandomReview();
        ReviewId id = expectedReview.getReviewId();
        VerboseCollection collection = new VerboseCollection(id, RandomString.nextWord());
        for (Review review : mReviews) {
            collection.add(new VerboseDatum(RandomReviewId.nextReviewId(),
                    review.getSubject().getSubject()));
        }

        assertCorrectReview(mSource.asMetaReview(collection, ""), expectedReview);
    }

    @NonNull
    private FactoryReviews getReviewFactory() {
        ConverterMd converter = new FactoryMdConverter().newMdConverter();
        FactoryReviewPublisher publisherFactory = new FactoryReviewPublisher(AUTHOR);
        return new FactoryReviews(publisherFactory, new FactoryReviewNode(), converter);
    }

    @NonNull
    private TreeFlattenerImpl getTreeFlattener() {
        return new TreeFlattenerImpl(new FactoryVisitorReviewNode(), new FactoryNodeTraverser());
    }

    private Review getRandomReview() {
        int index = RAND.nextInt(NUM_REVIEWS);
        return mReviews.getItem(index);
    }

    private void assertSingleDescendant(@Nullable ReviewNode node) {
        assertNotNull(node);
        assertThat(node.getParent(), is(nullValue()));
        assertThat(node.getChildren().size(), is(1));
        ReviewNode child = node.getChildren().getItem(0);
        assertThat(child.getChildren().size(), is(0));
    }

    private void assertCorrectReview(@Nullable ReviewNode node, Review expectedReview) {
        assertNotNull(node);
        ReviewNode child = node.getChildren().getItem(0);
        assertThat(child.getReview(), is(expectedReview));
        assertThat(child.getSubject(), is(expectedReview.getSubject()));
        assertThat(child.getRating(), is(expectedReview.getRating()));
    }

    private class VerboseDatum implements VerboseDataReview {
        private ReviewId mId;
        private String mSummary;

        public VerboseDatum(ReviewId id) {
            this(id, RandomString.nextWord());
        }

        public VerboseDatum(ReviewId id, String summary) {
            mId = id;
            mSummary = summary;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public String getStringSummary() {
            return mSummary;
        }

        @Override
        public boolean hasElements() {
            return false;
        }

        @Override
        public boolean isVerboseCollection() {
            return false;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return true;
        }
    }

    private class VerboseCollection extends SortableListImpl<VerboseDatum>
            implements VerboseIdableItems<VerboseDatum>, VerboseDataReview {
        private ReviewId mId;
        private String mSummary;

        public VerboseCollection(ReviewId id, String summary) {
            mId = id;
            mSummary = summary;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public String getStringSummary() {
            return mSummary;
        }

        @Override
        public boolean hasElements() {
            return size() > 0;
        }

        @Override
        public boolean isVerboseCollection() {
            return true;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return hasElements();
        }
    }
}
