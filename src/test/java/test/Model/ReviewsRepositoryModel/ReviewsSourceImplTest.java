package test.Model.ReviewsRepositoryModel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.SortableListImpl;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableItems;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableItems;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewsSourceImpl;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.TreeFlattenerImpl;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.AuthorId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryObserver;
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
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by: Rizwan Choudrey
 * On: 22/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewsSourceImplTest {
    private static final int NUM = 5;
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
        for (int i = 0; i < NUM; ++i) {
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
    public void asMetaReview_ReviewId_ReturnsMetaReviewWithOneChildOnly() {
        Review review = getRandomReview();
        assertNumChildren(mSource.asMetaReview(review.getReviewId()), 1);
    }

    @Test
    public void asMetaReview_ReviewId_ReturnsMetaReviewWithCorrectChildNode() {
        Review expectedReview = getRandomReview();
        ReviewNode node = mSource.asMetaReview(expectedReview.getReviewId());
        assertNotNull(node);
        assertCorrectReview(node.getChildren().getItem(0), expectedReview);
    }

    @Test
    public void asMetaReview_Data_ReturnsNullIfNoReviewFound() {
        VerboseDatum datum = new VerboseDatum(RandomReviewId.nextReviewId());
        assertThat(mSource.asMetaReview(datum, ""), is(nullValue()));
    }

    @Test
    public void asMetaReview_Data_ReturnsMetaReviewWithOneChildOnlyForDatum() {
        Review review = getRandomReview();
        VerboseDatum datum = new VerboseDatum(review.getReviewId());
        assertNumChildren(mSource.asMetaReview(datum, ""), 1);
    }

    @Test
    public void asMetaReview_Data_ReturnsMetaReviewWithCorrectChildNodeForDatum() {
        Review expectedReview = getRandomReview();
        VerboseDatum datum = new VerboseDatum(expectedReview.getReviewId());
        ReviewNode node = mSource.asMetaReview(datum, "");
        assertNotNull(node);
        assertCorrectReview(node.getChildren().getItem(0), expectedReview);
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithOneChildOnlyForDataCollectionWithReviewIdInSource
            () {
        ReviewId id = getRandomReview().getReviewId();
        VerboseCollection collection = new VerboseCollection(id, RandomString.nextWord());
        for (int i = 0; i < NUM; ++i) {
            collection.add(new VerboseDatum(RandomReviewId.nextReviewId()));
        }

        assertNumChildren(mSource.asMetaReview(collection, ""), 1);
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithCorrectChildNodeForDataCollectionWithReviewIdInSource() {
        Review expectedReview = getRandomReview();
        ReviewId id = expectedReview.getReviewId();
        VerboseCollection collection = new VerboseCollection(id, RandomString.nextWord());
        for (int i = 0; i < NUM; ++i) {
            collection.add(new VerboseDatum(RandomReviewId.nextReviewId()));
        }

        ReviewNode node = mSource.asMetaReview(collection, RandomString.nextWord());
        assertNotNull(node);
        assertCorrectReview(node.getChildren().getItem(0), expectedReview);
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithCorrectNumberChildrenForDataCollectionWithReviewIdNotInSource
            () {
        VerboseCollection collection = getItemsDataCollection();
        assertThat(collection.size(), is(mReviews.size() + 1));
        assertNumChildren(mSource.asMetaReview(collection, ""), mReviews.size());
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithCorrectReviewForDataCollectionWithReviewIdNotInSource
            () {
        VerboseCollection collection = getItemsDataCollection();
        String subject = RandomString.nextWord();

        ReviewNode node = mSource.asMetaReview(collection, subject);

        assertNotNull(node);
        assertNodeHasCorrectData(subject, node);
    }

    @Test
    public void
    getMetaReviewReturnsNullIfDataDoesNotHaveReviewIdsInSource() {
        VerboseCollection collection = new VerboseCollection(RandomReviewId.nextReviewId(), "");
        for(int i = 0; i < NUM; ++i) {
            collection.add(new VerboseDatum(RandomReviewId.nextReviewId()));
        }

        assertThat(mSource.getMetaReview(collection, ""), is(nullValue()));
    }

    @Test
    public void
    getMetaReviewReturnsMetaReviewWithCorrectNumberChildren() {
        VerboseCollection collection = getItemsDataCollection();
        assertThat(collection.size(), is(mReviews.size() + 1));
        assertNumChildren(mSource.getMetaReview(collection, ""), mReviews.size());
    }

    @Test
    public void
    getMetaReviewReturnsMetaReviewWithCorrectReview() {
        VerboseCollection collection = getItemsDataCollection();
        String subject = RandomString.nextWord();

        ReviewNode node = mSource.getMetaReview(collection, subject);

        assertNotNull(node);
        assertNodeHasCorrectData(subject, node);
    }

    @Test
    public void getFlattenedMetaReview() {
        fail(); //TODO write test when flattening strategy more concrete
    }


    @Test
    public void getReviewReturnsNullIfReviewNotFound() {
        assertThat(mSource.getReview(RandomReviewId.nextReviewId()), is(nullValue()));
    }

    @Test
    public void getReviewReturnsCorrectReviewIfFound() {
        Review review = getRandomReview();
        assertThat(mSource.getReview(review.getReviewId()), is(review));
    }

    @Test
    public void getReviewsReturnsReviewsInRepository() {
        assertThat(mSource.getReviews(), is(mRepo.getReviews()));
    }

    @Test
    public void getTagsManagerReturnsManager() {
        assertThat(mSource.getTagsManager(), is(mMockManager));
    }

    @Test
    public void registerUnregisterObserverDelegatesToRepository() {
        ReviewsRepository repo = mock(ReviewsRepository.class);
        ReviewsSource source = new ReviewsSourceImpl(repo, getReviewFactory(), getTreeFlattener());
        ReviewsRepositoryObserver observer = mock(ReviewsRepositoryObserver.class);
        source.registerObserver(observer);
        verify(repo).registerObserver(observer);
        source.unregisterObserver(observer);
        verify(repo).unregisterObserver(observer);
    }

    @NonNull
    private VerboseCollection getItemsDataCollection() {
        ReviewId id = RandomReviewId.nextReviewId();
        VerboseCollection collection = new VerboseCollection(id, RandomString.nextWord());
        for (Review review : mReviews) {
            collection.add(new VerboseDatum(review.getReviewId()));
        }
        collection.add(new VerboseDatum(getRandomReview().getReviewId()));
        return collection;
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
        int index = RAND.nextInt(mReviews.size());
        return mReviews.getItem(index);
    }

    private void assertNodeHasCorrectData(String subject, ReviewNode node) {
        IdableList<ReviewNode> children = node.getChildren();
        float averageRating = 0;
        int numChildren = children.size();
        for (int i = 0; i < numChildren; ++i) {
            ReviewNode child = children.getItem(i);
            assertCorrectReview(child, mReviews.getItem(i));
            averageRating += child.getRating().getRating() / numChildren;
        }

        assertThat(node.getSubject().getSubject(), is(subject));
        assertThat(node.getRating().getRating(), is(averageRating));
        assertThat(node.getRating().getRatingWeight(), is(numChildren));
    }

    private void assertNumChildren(@Nullable ReviewNode node, int num) {
        assertNotNull(node);
        assertThat(node.getParent(), is(nullValue()));
        assertThat(node.getChildren().size(), is(num));
        for (int i = 0; i < num; ++i) {
            ReviewNode child = node.getChildren().getItem(i);
            assertThat(child.getChildren().size(), is(0));
        }
    }

    private void assertCorrectReview(@Nullable ReviewNode node, Review expectedReview) {
        assertNotNull(node);
        assertThat(node.getReview(), is(expectedReview));
        assertThat(node.getSubject(), is(expectedReview.getSubject()));
        assertThat(node.getRating(), is(expectedReview.getRating()));
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
