# Reviewer
## The Main App
### What is it?
This is the core Startouch app. Reviewer was the working title of the project before I came up with Startouch.

### Architecture
I'm not a professional coder so the architecture that I've come up with is probably quite heinous to right-minded developers. 
However my intentions were pure.

The architecture is Model-View-Presenter. The idea is to both separate the view from the model and the view from the 
Android UI environment (as much as possible). The Android UI environment consists of activities and fragments. Activities are the bits of
Android that create and provide the screen to the user. Activities may contain none or more fragments which contain specific UI logic. 
If there are no fragments, activities are responsible for UI logic. 

Activities create fragments, route information to them and do other Androidy things which are not strictly UI related. 
They are the Android environment for your app on any given screen. Consequently I chose to have 1 fragment per activity to separate the 
non-UI responsibilities from the pure UI logic. This leads to 2 situations vis a vis presenters:

1) If the activity is pretty simple and static, like the [login activity][login_activity_ref] (which only really creates a fragment), I only need a [presenter][presenter_ref] for the associated [fragment][fragment_ref] to separate the view from the model as the Android UI envronment isn't really doing much.
2) If the activity is more complex where the prevailing environment is more important then there may be both a presenter for the activity and a presenter for the fragment to help separate the view both from the model and the Android environment. For example, the [review building activity][review_building_activity_ref] has an [activity presenter][activity_presenter_ref] which builds the appropriate [fragment presenter][fragment_presenter_ref] for the activity's [fragment][fragment_reviewbuild_ref].

  [login_activity_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins/PlugIns/UiPlugin/UiAndroid/Implementation/Activities/ActivityLogin.java
  [presenter_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Presenter/ReviewViewModel/Implementation/View/PresenterLogin.java
  [fragment_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins/PlugIns/UiPlugin/UiAndroid/Implementation/Fragments/FragmentLogin.java
  [review_building_activity_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins/PlugIns/UiPlugin/UiAndroid/Implementation/Activities/ActivityBuildReview.java
  [activity_presenter_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Presenter/ReviewBuilding/Implementation/PresenterReviewBuild.java
  [fragment_presenter_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Presenter/ReviewBuilding/Interfaces/ReviewEditor.java
  [fragment_reviewbuild_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins/PlugIns/UiPlugin/UiAndroid/Implementation/Fragments/FragmentReviewView.java
The archetypical example is the trinity between [ActivityReviewView](activity_rv), [FragmentReviewView](fragmrnt_rv) and [ReviewView](rv).

