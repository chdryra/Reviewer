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

Activities create fragments, route information to them or between them, and do other Androidy things which are not strictly UI related. 
They are the Android environment for your app on any given screen. Consequently I chose to have 1 fragment per activity to separate the 
non-UI responsibilities from the pure UI logic. This led me to two types of presenter - one for activities and one for fragments.


