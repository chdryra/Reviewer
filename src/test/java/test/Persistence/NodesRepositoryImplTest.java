/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Persistence;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Collections.SortableListImpl;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DefaultNamedAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.VerboseDataReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.VerboseIdableCollection;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryMdConverter;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.MdConverters.ConverterMd;
import com.chdryra.android.mygenerallibrary.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewsNodeRepoImpl;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsNodeRepo;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Random;

import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewId;
import test.TestUtils.StaticReviewsRepo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by: Rizwan Choudrey
 * On: 22/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class NodesRepositoryImplTest {
    private static final int NUM = 5;
    private static final NamedAuthor AUTHOR = new DefaultNamedAuthor("Author", AuthorIdGenerator.newId());
    private static final Random RAND = new Random();

    @Mock
    private TagsManager mMockManager;
    private IdableCollection<Review> mReviews;
    private ReviewsRepoReadable mRepo;
    private ReviewsNodeRepo mSource;

    @Before
    public void setUp() {
        mReviews = new IdableDataCollection<>();
        for (int i = 0; i < NUM; ++i) {
            mReviews.add(RandomReview.nextReview());
        }
        mRepo = new StaticReviewsRepo(mReviews, mMockManager);
        mSource = new ReviewsNodeRepoImpl(mRepo, getReviewFactory());
    }

    @Test
    public void asMetaReview_ReviewId_ReturnsErrorIfNoReviewFound() {
        mSource.getMetaReview(RandomReviewId.nextReviewId(), new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertThat(result.isError(), is(true));
            }
        });
    }

    @Test
    public void asMetaReview_ReviewId_ReturnsMetaReviewWithOneChildOnly() {
        Review review = getRandomReview();
        mSource.getMetaReview(review.getReviewId(), new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertNumChildren(getNode(result), 1);
            }
        });
    }

    private ReviewNode getNode(RepoResult result) {
        assertThat(result.isError(), is(false));
        ReviewNode node = result.getReviewNodeComponent();
        assertThat(node, not(nullValue()));
        return node;
    }

    @Test
    public void asMetaReview_ReviewId_ReturnsMetaReviewWithCorrectChildNode() {
        final Review expectedReview = getRandomReview();
        mSource.getMetaReview(expectedReview.getReviewId(), new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertCorrectReview(getNode(result).getChildren().get(0), expectedReview);
            }
        });
    }

    @Test
    public void asMetaReview_Data_ReturnsErrorIfNoReviewFound() {
        VerboseDatum datum = new VerboseDatum(RandomReviewId.nextReviewId());
        mSource.asMetaReview(datum, "", new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertThat(result.isError(), is(true));
            }
        });
    }

    @Test
    public void asMetaReview_Data_ReturnsMetaReviewWithOneChildOnlyForDatum() {
        Review review = getRandomReview();
        VerboseDatum datum = new VerboseDatum(review.getReviewId());
        mSource.asMetaReview(datum, "", new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertNumChildren(getNode(result), 1);
            }
        });
    }

    @Test
    public void asMetaReview_Data_ReturnsMetaReviewWithCorrectChildNodeForDatum() {
        final Review expectedReview = getRandomReview();
        VerboseDatum datum = new VerboseDatum(expectedReview.getReviewId());
        mSource.asMetaReview(datum, "", new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertCorrectReview(getNode(result).getChildren().get(0), expectedReview);
            }
        });

    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithOneChildOnlyForDataCollectionWithReviewIdInSource
            () {
        ReviewId id = getRandomReview().getReviewId();
        VerboseCollection collection = new VerboseCollection(id, RandomString.nextWord());
        for (int i = 0; i < NUM; ++i) {
            collection.add(new VerboseDatum(id));
        }

        mSource.asMetaReview(collection, "", new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertNumChildren(getNode(result), 1);
            }
        });
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithCorrectChildNodeForDataCollectionWithReviewIdInSource() {
        final Review expectedReview = getRandomReview();
        ReviewId id = expectedReview.getReviewId();
        VerboseCollection collection = new VerboseCollection(id, RandomString.nextWord());
        for (int i = 0; i < NUM; ++i) {
            collection.add(new VerboseDatum(id));
        }

        mSource.asMetaReview(collection, RandomString.nextWord(), new ReviewsNodeRepo.ReviewsSourceCallback() {

            @Override
            public void onMetaReviewCallback(RepoResult result) {
                ReviewNode review = getNode(result);
                assertThat(review.getChildren().size(), is(1));
                assertCorrectReview(review.getChildren().get(0), expectedReview);
            }
        });
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithCorrectNumberChildrenForDataCollectionWithReviewIdNotInSource
            () {
        VerboseCollection collection = getItemsDataCollection();
        assertThat(collection.size(), is(mReviews.size() + 1));
        mSource.asMetaReview(collection, "", new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertNumChildren(getNode(result), mReviews.size());
            }
        });
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithCorrectReviewForDataCollectionWithReviewIdNotInSource
            () {
        VerboseCollection collection = getItemsDataCollection();
        final String subject = RandomString.nextWord();

        mSource.asMetaReview(collection, subject, new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertNodeHasCorrectData(subject, getNode(result));
            }
        });
    }

    @Test
    public void
    getMetaReviewReturnsErrorIfDataDoesNotHaveReviewIdsInSource() {
        final VerboseCollection collection = new VerboseCollection(RandomReviewId.nextReviewId(), "");
        for(int i = 0; i < NUM; ++i) {
            collection.add(new VerboseDatum(RandomReviewId.nextReviewId()));
        }

        mSource.getMetaReview(collection, "", new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertThat(result.isError(), is(true));
            }
        });
    }

    @Test
    public void
    getMetaReviewReturnsMetaReviewWithCorrectNumberChildren() {
        VerboseCollection collection = getItemsDataCollection();
        assertThat(collection.size(), is(mReviews.size() + 1));
        mSource.getMetaReview(collection, "", new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertNumChildren(getNode(result), mReviews.size());
            }
        });
    }

    @Test
    public void
    getMetaReviewReturnsMetaReviewWithCorrectReview() {
        VerboseCollection collection = getItemsDataCollection();
        final String subject = RandomString.nextWord();

        mSource.getMetaReview(collection, subject, new ReviewsNodeRepo.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepoResult result) {
                assertNodeHasCorrectData(subject, getNode(result));
            }
        });
    }

    @Test
    public void getReviewReturnsErrorIfReviewNotFound() {
        mSource.getReview(RandomReviewId.nextReviewId(), new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                assertThat(result.isError(), is(true));
            }
        });
    }

    @Test
    public void getReviewReturnsCorrectReviewWhenFound() {
        final Review review = getRandomReview();
        mSource.getReview(review.getReviewId(), new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                assertThat(result.getReview(), is(review));
            }
        });
    }

    @Test
    public void getReviewsReturnsReviewsInRepository() {
        mSource.getReviewsByAuthor(new RepoCallback() {
            @Override
            public void onRepoCallback(final RepoResult fromSource) {
                mRepo.getRepository(new RepoCallback() {
                    @Override
                    public void onRepoCallback(RepoResult fromRepo) {
                        assertThat(fromSource.getReview(), is(fromRepo.getReview()));
                    }
                });
            }
        });
    }

    @Test
    public void getTagsManagerReturnsManager() {
        assertThat(mSource.getTagsManager(), is(mMockManager));
    }

    @Test
    public void registerUnregisterObserverDelegatesToRepository() {
        ReviewsRepoReadable repo = mock(ReviewsRepoReadable.class);
        ReviewsNodeRepo source = new ReviewsNodeRepoImpl(repo, getReviewFactory());
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
        FactoryReviews factoryReviews = new FactoryReviews(new FactoryReviewNode(), converter, new DataValidator());
        factoryReviews.setReviewStamper(new FactoryReviews.AuthorsStamp(AUTHOR));
        return factoryReviews;
    }

    private Review getRandomReview() {
        int index = RAND.nextInt(mReviews.size());
        return mReviews.get(index);
    }

    private void assertNodeHasCorrectData(String subject, ReviewNode node) {
        IdableList<ReviewNode> children = node.getChildren();
        float averageRating = 0;
        int numChildren = children.size();
        ArrayList<Review> reviewsAssessed = new ArrayList<>();
        for (int i = 0; i < numChildren; ++i) {
            ReviewNode child = children.get(i);
            Review review = child.getReference();

            assertThat(mReviews.contains(review), is(true));
            assertThat(reviewsAssessed.contains(review), is(false));
            reviewsAssessed.add(review);

            Review item = mReviews.get(review.getReviewId());
            assertNotNull(item);
            assertCorrectReview(child, item);
            averageRating += child.getRating().getRating() / numChildren;
        }

        assertThat(node.getSubject().getSubject(), is(subject));
        assertThat((double)node.getRating().getRating(), closeTo((double)averageRating, 0.001));
        assertThat(node.getRating().getRatingWeight(), is(numChildren));
    }

    private void assertNumChildren(@Nullable ReviewNode node, int num) {
        assertNotNull(node);
        assertThat(node.getParent(), is(nullValue()));
        assertThat(node.getChildren().size(), is(num));
        for (int i = 0; i < num; ++i) {
            ReviewNode child = node.getChildren().get(i);
            assertThat(child.getChildren().size(), is(0));
        }
    }

    private void assertCorrectReview(@Nullable ReviewNode node, Review expectedReview) {
        assertNotNull(node);
        assertThat(node.getReference(), is(expectedReview));
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
        public String toString() {
            return mSummary;
        }

        @Override
        public boolean hasElements() {
            return false;
        }

        @Override
        public boolean isCollection() {
            return false;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return true;
        }
    }

    private class VerboseCollection extends SortableListImpl<VerboseDatum>
            implements VerboseIdableCollection<VerboseDatum>, VerboseDataReview {
        private ReviewId mId;
        private String mSummary;

        public VerboseCollection(ReviewId id, String summary) {
            mId = id;
            mSummary = summary;
        }

        @Nullable
        @Override
        public VerboseDatum getItem(ReviewId id) {
            for (VerboseDatum datum : this) {
                if(datum.getReviewId().equals(id)) return datum;
            }

            return null;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public String toString() {
            return mSummary;
        }

        @Override
        public boolean hasElements() {
            return size() > 0;
        }

        @Override
        public boolean isCollection() {
            return true;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return hasElements();
        }
    }
}
