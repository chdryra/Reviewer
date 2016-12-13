/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityFormatReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CellDimensionsCalculator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CommentNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CoverNodeBannerUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CriteriaNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.FactsNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuOptionsAppLevel;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUpAppLevel;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.RatingBarLaunchSummary;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.StampUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.TagsNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.TextUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ViewUi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiUpAppLevel;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FragmentFormatReview2 extends Fragment implements ReviewNode.NodeObserver,
        OptionSelectListener {
    private static final String ID = TagKeyGenerator.getKey(FragmentFormatReview2.class,
            "ReviewId");
    private static final String PUBLISHED = TagKeyGenerator.getKey(FragmentFormatReview2.class,
            "published");

    private static final int LAYOUT = R.layout.fragment_review_formatted2;
    private static final int IMAGE = R.id.image_formatted;
    private static final int SUBJECT = R.id.subject_formatted;
    private static final int RATING = R.id.rating_formatted;
    private static final int HEADLINE = R.id.headline_formatted;
    private static final int AUTHOR = R.id.author_formatted;
    private static final int DATE = R.id.date_formatted;
    private static final int COMMENT = R.id.comment_formatted;
    private static final int TAGS = R.id.tags_formatted;
    private static final int CRITERIA = R.id.criteria_formatted;
    private static final int FACTS = R.id.facts_formatted;
    private static final int TITLE = R.id.section_title;
    private static final int VALUE = R.id.section_value;

    private static final ReviewViewParams.CellDimension FULL
            = ReviewViewParams.CellDimension.FULL;
    private static final ReviewViewParams.CellDimension HALF
            = ReviewViewParams.CellDimension.HALF;
    private static final ReviewViewParams.CellDimension EIGHTH
            = ReviewViewParams.CellDimension.EIGHTH;
    private static final ReviewViewParams.CellDimension QUARTER
            = ReviewViewParams.CellDimension.QUARTER;

    private boolean mIsPublished = true;
    private ReviewNode mNode;
    private NamedAuthor mNamedAuthor;
    private UiSuite mUi;
    private RepositorySuite mRepo;
    private MenuUi mMenu;

    private CoverNodeBannerUi mCover;
    private TextUi<TextView> mSubject;
    private RatingBarLaunchSummary mRating;
    private TextUi<TextView> mAuthor;
    private TextUi<TextView> mDate;
    private CommentNodeUi mComment;
    private TagsNodeUi mTags;
    private CriteriaNodeUi mCriteria;
    private FactsNodeUi mFacts;

    public static FragmentFormatReview2 newInstance(ReviewId nodeId, boolean isPublished) {
        //Can't use FactoryFragment as Support fragment rather than normal fragment
        Bundle args = new Bundle();
        args.putString(ID, nodeId.toString());
        args.putBoolean(PUBLISHED, isPublished);
        FragmentFormatReview2 fragment = new FragmentFormatReview2();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        mUi = app.getUi();
        mRepo = app.getRepository();

        Bundle args = getArguments();
        if (args == null) throwNoReview();
        mIsPublished = args.getBoolean(PUBLISHED);

        setNode(args);
        setMenu();

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        CellDimensionsCalculator calculator = new CellDimensionsCalculator(getActivity());
        CellDimensionsCalculator.Dimensions dims12 = calculator.calcDimensions(FULL, HALF);
        CellDimensionsCalculator.Dimensions dims28 = calculator.calcDimensions(HALF, EIGHTH);

        View v = inflater.inflate(LAYOUT, container, false);

        ReviewLauncher launcher = mUi.getLauncher().getReviewLauncher();

        setCover(v, dims12);
        setSubject(v);
        setRating(v, launcher);
        setAuthor(v, launcher);
        setDate(v, launcher);
        setComment(v);
        setTags(v);
        setCriteria(v);
        setFacts(v);

        update();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mMenu.onItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        update();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        update();
    }

    @Override
    public void onNodeChanged() {
        update();
    }

    @Override
    public void onTreeChanged() {
        update();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mMenu.onOptionSelected(requestCode, option);
    }

    private FactoryCommands getCommandsFactory() {
        return mUi.getCommandsFactory();
    }

    private void setNode(Bundle args) {
        String reviewId = args.getString(ID);
        if (reviewId == null) throwNoReview();
        try {
            ActivityFormatReview activity = (ActivityFormatReview) getActivity();
            mNode = activity.getNode(new DatumReviewId(reviewId));
            mNode.registerObserver(this);
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    private void throwNoReview() {
        throw new RuntimeException("No review found");
    }

    private void setMenu() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        UiSuite ui = app.getUi();
        MenuActionItem<GvData> upAction = new MaiUpAppLevel<>(app);

        MenuAction<?> action;
        if (mIsPublished) {
            OptionsCommand command
                    = getCommandsFactory().newReviewOptionsSelector(new NodeAuthorId(mNode));
            MaiOptionsCommand<GvData> mai = new MaiOptionsCommand<>(command);
            action = new MenuOptionsAppLevel(Strings.Screens.FORMATTED, upAction, mai, ui);
        } else {
            action = new MenuUpAppLevel(Strings.Screens.PREVIEW, upAction, ui);
        }

        mMenu = new MenuUi(action);
    }

    private void update() {
        mCover.update();
        mSubject.update();
        mRating.update();
        mAuthor.update();
        mDate.update();
        mComment.update();
        mTags.update();
        mCriteria.update();
        mFacts.update();
    }

    private void setCover(View v, CellDimensionsCalculator.Dimensions dims) {
        Bitmap placeholder = BitmapFactory.decodeResource(getResources(),
                R.drawable.image_placeholder);
        ImageView image = (ImageView) v.findViewById(IMAGE);
        image.getLayoutParams().width = dims.getCellWidth();
        image.getLayoutParams().height = dims.getCellHeight();
        mCover = new CoverNodeBannerUi(image, mNode, placeholder);
    }

    private void setTags(final View v) {
        Command onClick = mIsPublished ? newLaunchViewCommand(GvTag.TYPE) : null;
        TextView tags = (TextView) v.findViewById(TAGS);
        mTags = new TagsNodeUi(tags, mNode, onClick);
    }

    private void setComment(View v) {
        Command onClick = mIsPublished ? newLaunchViewCommand(GvComment.TYPE) : null;
        TextView comment = (TextView) setTitleAndGetValueView(v, Strings.FORMATTED.COMMENT,
                COMMENT);
        mComment = new CommentNodeUi((TextView) v.findViewById(HEADLINE),
                comment, mNode, onClick);
    }

    private void setCriteria(View v) {
        Command onClick = mIsPublished ? newLaunchViewCommand(GvCriterion.TYPE) : null;
        setTitleAndGetValueView(v, Strings.FORMATTED.CRITERIA, CRITERIA);
        LinearLayout section = (LinearLayout)v.findViewById(CRITERIA);
        mCriteria = new CriteriaNodeUi(section, VALUE, LayoutInflater.from(getActivity()), mNode, onClick);
    }

    private void setFacts(View v) {
        Command onClick = mIsPublished ? newLaunchViewCommand(GvFact.TYPE) : null;
        setTitleAndGetValueView(v, Strings.FORMATTED.FACTS, FACTS);
        LinearLayout section = (LinearLayout)v.findViewById(FACTS);
        mFacts= new FactsNodeUi(section, VALUE, LayoutInflater.from(getActivity()), mNode, onClick);
    }

    private void setAuthor(View v, ReviewLauncher launcher) {
        DataAuthorId authorId = mNode.getAuthorId();
        TextView author = (TextView) setTitleAndGetValueView(v, Strings.FORMATTED.AUTHOR, AUTHOR);
        mAuthor = new StampUi(author, author(), authorId, launcher, mIsPublished);
        mRepo.getAuthorsRepository().getReference(authorId).dereference(setAuthorAndUpdate());
    }

    private void setDate(View v, ReviewLauncher launcher) {
        DataAuthorId authorId = mNode.getAuthorId();
        TextView date = (TextView) setTitleAndGetValueView(v, Strings.FORMATTED.DATE, DATE);
        mDate = new StampUi(date, date(), authorId, launcher, mIsPublished);
    }

    private void setRating(View v, ReviewLauncher launcher) {
        mRating = new RatingBarLaunchSummary((RatingBar) v.findViewById(RATING), mNode, launcher,
                mIsPublished);
    }

    private int setSubject(View v) {
        mSubject = new TextUi<>((TextView) v.findViewById(SUBJECT), subject());
        return mSubject.getTextColour();
    }

    private View setTitleAndGetValueView(View v, String sectionTitle, int sectionView) {
        View view = v.findViewById(sectionView);
        TextView titleView = (TextView) view.findViewById(TITLE);
        titleView.setText(sectionTitle);
        return view.findViewById(VALUE);
    }

    private Command newLaunchViewCommand(GvDataType<?> dataType) {
        return getCommandsFactory().newLaunchViewCommand(mUi.newDataView(mNode, dataType));
    }

    @NonNull
    private DataReference.DereferenceCallback<NamedAuthor> setAuthorAndUpdate() {
        return new DataReference.DereferenceCallback<NamedAuthor>() {
            @Override
            public void onDereferenced(DataValue<NamedAuthor> value) {
                if (value.hasValue()) {
                    mNamedAuthor = value.getData();
                    mAuthor.update();
                }
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<String> author() {
        return new ViewUi.ValueGetter<String>() {
            @Override
            public String getValue() {
                return mNamedAuthor != null ? mNamedAuthor.getName() : "";
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<String> date() {
        return new ViewUi.ValueGetter<String>() {
            @Override
            public String getValue() {
                Date date = new Date(mNode.getPublishDate().getTime());
                return DateFormat.getDateInstance(DateFormat.LONG).format(date);
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<String> subject() {
        return new ViewUi.ValueGetter<String>() {
            @Override
            public String getValue() {
                String subject = mNode.getSubject().getSubject();
                return subject;
            }
        };
    }

    private static class NodeAuthorId extends DatumAuthorId {
        private ReviewNode mNode;

        public NodeAuthorId(ReviewNode node) {
            super(node.getReviewId(), node.getAuthorId().toString());
            mNode = node;
        }

        @Override
        public ReviewId getReviewId() {
            return mNode.getReviewId();
        }

        @Override
        public String toString() {
            return mNode.getAuthorId().toString();
        }
    }
}
