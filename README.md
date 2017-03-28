# Reviewer
## The Main App
### What is it?
This is the core Startouch app. Reviewer was the working title of the project before I came up with Startouch.

### UI Architecture
I'm not a professional coder so the architecture that I've come up with is probably quite heinous to right-minded developers. 
However my intentions were pure.

The architecture is Model-View-Presenter. The idea is to both separate the view from the model and the view from the 
Android UI environment (as much as possible). The Android UI environment consists of activities and fragments. Activities are the bits of Android that create and provide the screen to the user. Activities may contain none or more fragments which contain specific UI logic. If there are no fragments, activities are responsible for UI logic. Activities create fragments, route information to them and do other Androidy things which are not strictly UI related. They are the Android environment for your app on any given screen. 

Therefore, to separate responsibilities, there is 1 fragment per activity to separate the non-UI responsibilities from the pure UI logic. This leads to 2 situations vis a vis presenters:

1) If the activity is pretty simple and static, like [ActivityLogin][login_activity_ref] (which only really creates a fragment), I only need a [presenter][presenter_ref] for the associated [fragment][fragment_ref] to separate the view from the model. The Android UI envronment isn't really doing much so I don't need another presenter for the activity.
2) If the activity is more complex where the prevailing environment *is* important then there may be both a presenter for the activity and a presenter for the fragment to help separate the view both from the model and the Android environment. For example, [ActivityBuildReview][review_building_activity_ref] has an [activity presenter][activity_presenter_ref] which builds the appropriate [fragment presenter][fragment_presenter_ref] for the activity's [fragment][fragment_reviewbuild_ref] and that routes information as and when needed.

The archetypical example is the trinity between [ActivityReviewView][activity_rv_ref] (and its children), [FragmentReviewView][fragment_rv_ref] and [ReviewView][rv_ref]. ActivityReviewView is the OS environment, FragmentReviewView is the OS UI and ReviewView is the UI presenter. Classes that inherit from ActivityReviewView (such as ActivityBuildReview) will usually have a presenter that builds the appropriate ReviewView to be forwarded to FragmentReviewView. FragmentReviewView then binds Android widgets to their more abstract presenter representations in ReviewView. 

There is one further UI abstraction: when launching a UI I wanted to be ambivalent between dialogs and activities so that I could freely switch between the two when trying things out. This led to the [LauncherModel][launcher_model_ref]. When a UI needs to be launched, the app asks for the appropriate [LaunchableUI][launchable_ui_ref] from the [LaunchablesList][launchables_list_ref]. This is then launched using [UiLauncher][ui_launcher_ref]. UiLauncher passes an OS appropriate visitor to the LaunchableUi that uses double dispatch to distinguish between the different UI types and launches the UI using the appropriate method.

  [login_activity_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins/PlugIns/UiPlugin/UiAndroid/Implementation/Activities/ActivityLogin.java
  [presenter_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Presenter/ReviewViewModel/Implementation/View/PresenterLogin.java
  [fragment_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins/PlugIns/UiPlugin/UiAndroid/Implementation/Fragments/FragmentLogin.java
  [review_building_activity_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins/PlugIns/UiPlugin/UiAndroid/Implementation/Activities/ActivityBuildReview.java
  [activity_presenter_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Presenter/ReviewBuilding/Implementation/PresenterReviewBuild.java
  [fragment_presenter_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Presenter/ReviewBuilding/Interfaces/ReviewEditor.java
  [fragment_reviewbuild_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins/PlugIns/UiPlugin/UiAndroid/Implementation/Fragments/FragmentReviewView.java
  [activity_rv_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins/PlugIns/UiPlugin/UiAndroid/Implementation/Activities/ActivityReviewView.java
  [fragment_rv_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins/PlugIns/UiPlugin/UiAndroid/Implementation/Fragments/FragmentReviewView.java
  [rv_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Presenter/Interfaces/View/ReviewView.java
  [launcher_model_ref]:
  https://github.com/chdryra/Reviewer/tree/master/src/main/java/com/chdryra/android/reviewer/View/LauncherModel
  [launchable_ui_ref]:
  https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/View/LauncherModel/Interfaces/LaunchableUi.java
  [launchables_list_ref]:
  https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/View/LauncherModel/Interfaces/LaunchablesList.java
  [ui_launcher_ref]:
  https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/View/LauncherModel/Interfaces/UiLauncher.java

### Application API
The application API is defined in [ApplicationSuite][app_suite_ref] and exposes the functionality that can actually be called from an environment. This is the outer shell of the app that sits below the UI. It is split into a number of thematic domains so that only the necessary API is passed to a presenter to do its job.

#### [AuthenticationSuite][auth_suite_ref]
User authentication, user account details, logging out etc.
#### [LocationServicesSuite][loc_suite_ref]
Finding addresses, searching place names, fetching location details etc.
#### [UiSuite][ui_suite_ref]
Creating certain screens, launching UIs, showing toasts etc.
#### [RepositorySuite][repo_suite_ref]
Storing, fetching, deleting reviews; fetching user info etc.
#### [EditorSuite][editor_suite_ref]
Creating, editing reviews etc.
#### [SocialSuite][social_suite_ref]
Followers, following, publishing to social platforms etc.
#### [NetworkSuite][network_suite_ref]
Checking if the internet is working!
#### [PermissionsSuite][perm_suite_ref]
Checking and requesting permission to use camera, location etc.

[app_suite_ref]:
https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Application/Interfaces/ApplicationSuite.java
[auth_suite_ref]:
https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Application/Interfaces/AuthenticationSuite.java
[loc_suite_ref]:
https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Application/Interfaces/LocationServicesSuite.java
[ui_suite_ref]:
https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Application/Interfaces/UiSuite.java
[repo_suite_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Application/Interfaces/RepositorySuite.java
[editor_suite_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Application/Interfaces/EditorSuite.java
[social_suite_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Application/Interfaces/SocialSuite.java
[network_suite_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Application/Interfaces/NetworkSuite.java
[perm_suite_ref]: https://github.com/chdryra/Reviewer/blob/master/src/main/java/com/chdryra/android/reviewer/Application/Interfaces/PermissionsSuite.java


### Code Structure
The code that is injected into the Application API is split into [contexts][context_ref]. These are (very broadly) split along the same lines as the Application Suites above but there is some variation as it is split more along "code architecture" lines rather than "human-readable" API. Each context exposes bits of code that are necessary for the APIs above to do their job. Each API may require 1 or more contexts for a given API call. There are also context [plugins][plugins_ref] which are implementations of certain services dependent on particular providers or technologies. For example location services by Google, the backend by Firebase, the local db using SqlLite etc. The contexts together with the plugins make up the core wiring of the app.

[context_ref]: https://github.com/chdryra/Reviewer/tree/master/src/main/java/com/chdryra/android/reviewer/ApplicationContexts/Interfaces
[plugins_ref]: https://github.com/chdryra/Reviewer/tree/master/src/main/java/com/chdryra/android/reviewer/ApplicationPlugins
