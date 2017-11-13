# InteractivePPT
Audience interaction tool

This system has been written in following languages:
*Java (Android Studio) - entire mobile application (for audience)
*C# (MetroFramework) - entire client desktop application (for presenter)
*PHP & MySQL - all server-side scripts and data stores (mediators between all clients)

Therefore, mobile application works only on devices with Android operating system (API version equal or greater than 16, i.e. Jelly Bean and newer versions), client desktop application works only on relatively modern distributions of Windows operating system (Microsoft PowerPoint should be installed on it in order to use this application) and server-side applications on Unix based operating systems.

So lets start with demo.
<br/>![image](/images/01.png?raw=true "Login window of desktop application.")<br/>
This is login window of desktop application where user has to enter valid facebook credentials in order to get access of all applications features.
<br/>![image](/images/02.png?raw=true "Loading screen of desktop application after entering facebook credentials.")<br/>
After entering facebook credentials in application, loading screen appears.
<br/>![image](/images/03.png?raw=true "Main window of desktop application with the list of user's presentations.")<br/>
As soon as user is identified and their presentations are loaded, main window shows up with the list of user's presentations with their basic information.
<br/>![image](/images/04.png?raw=true "Full-screen pop-up window with Qr code appears after clicking on presentation's tiny Qr logo (that allows audience to scan the code and immediately load the presentation).")<br/>
Full-screen pop-up window with Qr code appears after clicking on presentation's tiny Qr logo what allows audience to scan the code and immediately load the presentation.
<br/>![image](/images/05.png?raw=true "Classic folder picker dialog appears after user opens (i.e. presses button for its opening while row with desired presentation is selected) one of the presentations.")<br/>
Classic folder picker dialog appears after user opens (i.e. double-clicks or presses button for presentation opening while row with desired presentation is selected) one of the presentations.
<br/>![image](/images/06.png?raw=true "Message box appears if there is already presentation file with same name in selected folder - by choosing 'Yes' option local version is overwritten by remote one, by choosing 'No' remote version is overwritten by local one; otherwise local version is just opened.")<br/>
Message box appears if there is already presentation file with same name in selected folder - by choosing 'Yes' option local version is overwritten by remote one, by choosing 'No' remote version is overwritten by local one; otherwise local version is just opened.
<br/>![image](/images/07.png?raw=true "Preview of opened PowerPoint presentation via desktop application. Its new window contains controls for adding charts representing survey data in active PowerPoint presentation and for monitoring reply-requests and chat/textual-discussion.")<br/>
Selected PowerPoint presentation opens automatically via desktop applications. Also new application window shows up that contains controls for adding charts representing survey data in active PowerPoint presentation and for monitoring reply-requests and chat/textual-discussion.
<br/>![image](/images/08.png?raw=true "Result of chart addition. Chart represents answers (if there are already any of them) on selected question from selected survey.")<br/>
Finally something more interesting and unconventional - here you can see result of chart addition. Chart represents answers (if there are already any of them) on selected question from selected survey. Unfortunately, it is required to disable Presenter View if presenter uses only 2 screens - the reason is that PowerPoint editor has to be active/visible. However, if presenter has 3 or more screens, then it is possible to use Presenter View (on one screen should be PowerPoint Slide Show, on another one should be PowerPoint editor with this application's window and on the remaining one can be placed Presenter View.

Now lets me introduce you other side of this system. We will try to complete survey whose question answers we represented above and test if the chart will be changed.
<br/>![image](/images/09.png?raw=true "Starting activity of the mobile application.")<br/>
First you can see starting activity of the mobile application. Well, hardly you can notice just this screen because it very soon becomes like the following one.
<br/>![image](/images/10.png?raw=true "Pop-up window for entering facebook credentials that appears after tapping Login button.")<br/>
Now we can see pop-up window for entering facebook credentials. If it doesn't show up, you should try to tap the Login button.
<br/>![image](/images/11.png?raw=true "Home activity with list of all user's presentations and all subscribed presentations. By tapping any item in list, presentation details are shown. By tapping item's triangle button, activity for viewing selected presentation shows up.")<br/>
Right after user is authenticated and registered on the server-side of the system, home activity with a list of all user's presentations and all subscribed presentations appears. By tapping any item in list, presentation details are shown. By tapping item's triangle button, activity for viewing selected presentation shows up.
<br/>![image](/images/12.png?raw=true "Activity that loads and displays selected presentation.")<br/>
Screen above represents activity that loads and displays previously-selected presentation. Applications actually loads Google Slides application in integrated web-browser and requests from it to open and preview selected PowerPoint presentation that is stored on the server-side of the application.
<br/>![image](/images/13.png?raw=true "Each activity is responsive on screen rotation.")<br/>
It is important to mention that each activity is responsive on screen rotation and there is no data loss.
<br/>![image](/images/14.png?raw=true "It is also possible to browse a list of surveys of selected presentation.")<br/>
It is also possible to browse a list of surveys of selected presentation.
<br/>![image](/images/15.png?raw=true "After selecting one of surveys, a list of questions (and their options if there are any) appears. Questions that are ending with small asterisk are mandatory.")<br/>
After selecting one of surveys, a list of questions (and their options if there are any) appears. Questions that are ending with small asterisk are mandatory.
<br/>![image](/images/16.png?raw=true "When user fills whole survey form (or at least its all mandatory questions), then they can submit it to the server.")<br/>
When user fills whole survey form (or at least its all mandatory questions), then they can submit it to the server.
<br/>![image](/images/17.png?raw=true "A confirmation message shows up if answers were sent successfully.")<br/>
A confirmation message shows up if answers were sent successfully.
<br/>![image](/images/18.png?raw=true "If user tries to send answers multiple times, an error message shows up that says that answers where previously sent.")<br/>
If user tries to send answers multiple times, an error message shows up that says that answers where previously sent.
<br/>![image](/images/19.png?raw=true "Presenter can anytime refresh current charts. If chart representing answers of selected question is already in opened PowerPoint presentation, then presenter can simply select in drop-down list to replace current chart representing answers of the same question. Now we can notice that pie-chart was really changed.")<br/>
Presenter can anytime refresh current charts. If chart representing answers of selected question is already in opened PowerPoint presentation, then presenter can simply select in drop-down list to replace current chart representing answers of the same question. Now we can notice that pie-chart was really changed.
<br/>![image](/images/20.png?raw=true "When user sends reply request, it can be noticed by presenter in his presentation window of desktop application. Presenter can simply click on row with user's name within 'Replies' tab-page what removes that row from table and allows user again to send reply-request. Number in round brackets indicates number of unseen reply requests or chat messages.")<br/>
When user sends reply request, it can be noticed by presenter in his presentation window of desktop application. Presenter can simply click on row with user's name within 'Replies' tab-page what removes that row from table and allows user again to send reply-request. Number in round brackets indicates number of unseen reply requests or chat messages.
<br/>![image](/images/21.png?raw=true "If user tries to send reply request and their previous reply-request isn't processed yet, then error message appears.")<br/>
If user tries to send reply request and their previous reply-request isn't processed yet, then error message appears.
<br/>![image](/images/22.png?raw=true "Entering a chat section, user can see all messages that were sent during active presentation session (even if user wasn't present during whole discussion). It is possible to use chat and reply requests only when there is active presentation session, i.e. presenter has opened and is using presentation via desktop application.")<br/>
Entering a chat section, user can see all messages that were sent during active presentation session (even if user wasn't present during whole discussion). It is possible to use chat and reply requests only when there is active presentation session, i.e. presenter has opened and is using presentation via desktop application.
<br/>![image](/images/23.png?raw=true "Each time when user receives message, it shows up in notification bar. Therefore, user can even have this application minimized, but still can follow the discussion because of push notifications.")<br/>
Each time when user receives message, it shows up in notification bar. Therefore, user can even have this application minimized, but still can follow the discussion because of push notifications.
<br/>![image](/images/24.png?raw=true "Of course, it is also possible to send message - message can contain not only text, but also emojis.")<br/>
Of course, it is also possible to send message - message can contain not only text, but also emojis.
<br/>![image](/images/25.png?raw=true "All messages appear immediately in presenter's presentation window in 'Chat' tab-page.")<br/>
All messages appear immediately in presenter's presentation window in 'Chat' tab-page.
<br/>![image](/images/26.png?raw=true "By tapping on hamburger button or by swiping right, navigation drawer appears. There are few options user can choose: list presentations, create new survey or open presentation (by scanning Qr code or entering textual access code) if it currently isn't in the list. After all these options for accessing applications modules, there is also a Logout button.")<br/>
By tapping on hamburger button or by swiping right, navigation drawer appears. There are few options user can choose: list presentations, create new survey or open presentation (by scanning Qr code or entering textual access code) if it currently isn't in the list. After all these options for accessing applications modules, there is also a Logout button.
<br/>![image](/images/27.png?raw=true "If user doesn't have some presentation in presentation list (e.g. because they haven't accessed it never before), then there is an option to add it. By tapping third option in navigation, application starts Qr scanner and user just has to direct the camera towards Qr code.")<br/>
If user doesn't have some presentation in presentation list (e.g. because they haven't accessed it never before), then there is an option to add it. By tapping third option in navigation, application starts Qr scanner and user just has to direct the camera towards Qr code.
<br/>![image](/images/28.png?raw=true "In case there is no any Qr code or user is unable to scan it, it is also possible to enter presentation's access code manually. If access code is valid, application loads belonging presentation and subscribes user to it.")<br/>
In case there is no any Qr code or user is unable to scan it, it is also possible to enter presentation's access code manually. If access code is valid, application loads belonging presentation and subscribes user to it.
<br/>![image](/images/29.png?raw=true "If presentation session is not active (i.e. it has already finished) and user opens that presentation, then it is possible to view its content and even to fill the survey forms (if they aren't already completed), but chat and reply requests can't be used.")<br/>
If presentation session is not active (i.e. it has already finished) and user opens that presentation, then it is possible to view its content and even to fill the survey forms (if they aren't already completed), but chat and reply requests can't be used.
<br/>![image](/images/30.png?raw=true "Last application's module is survey creation. For each survey user should define its title, description, presentation on which it is attached and at least one question.")<br/>
Last application's module is survey creation. For each survey user should define its title, description, presentation on which it is attached and at least one question.
<br/>![image](/images/31.png?raw=true "User can link this new survey to one of their own presentations or attach it to new one. If user doesn't have any presentation, then it is required to upload new one in order to create survey.")<br/>
User can link this new survey to one of their own presentations or attach it to new one. If user doesn't have any presentation, then it is required to upload new one in order to create survey.
<br/>![image](/images/32.png?raw=true "By tapping button for adding new presentation file, integrated file explorer appears and user has to select appropriate file - file with extension '.ppt' or '.pptx'.")<br/>
By tapping button for adding new presentation file, integrated file explorer appears and user has to select appropriate file - file with extension '.ppt' or '.pptx'.
<br/>![image](/images/33.png?raw=true "After successfully adding valid file, confirmation message is displayed and presentation's name is set next to previous button. This button now can be used to replace previously selected presentation. If user hasn't selected any file or or it is invalid, then error message appears.")<br/>
After successfully adding valid file, confirmation message is displayed and presentation's name is set next to previous button. This button now can be used to replace previously selected presentation. If user hasn't selected any file or or it is invalid, then error message appears.
<br/>![image](/images/34.png?raw=true "When user taps button for adding new question, then green pop-up dialog shows up. Here can be specified question's name, type and options. Also user can decide if survey form can be submitted without answering on this question.")<br/>
When user taps button for adding new question, then green pop-up dialog shows up. Here can be specified question's name, type and options. Also user can decide if survey form can be submitted without answering on this question.
<br/>![image](/images/35.png?raw=true "Currently, there are 3 question types supported: single-choice, multiple-choice and text-edit. If user selects text-edit as a type of question, then there is no need to specify belonging options, therefore, in that case, list view for adding them is disabled/hidden.")<br/>
Currently, there are 3 question types supported: single-choice, multiple-choice and text-edit. If user selects text-edit as a type of question, then there is no need to specify belonging options, therefore, in that case, list view for adding them is disabled/hidden.
While we were developing this system, we carefully designed current software architecture so it is easy to add new question types and its graphical controls.
<br/>![image](/images/36.png?raw=true "Each time question is added, it is immediately added in questions section. By tapping any of the item that represents previously-added question, a list of its options (if there are any) shows up below it.")<br/>
Each time question is added, it is immediately added in questions section. By tapping any of the item that represents previously-added question, a list of its options (if there are any) shows up below it.
<br/>![image](/images/37.png?raw=true "Finally, when user is ready to publish created survey, then they simply tap button for uploading it. This can take a while if user's Internet connection is very slow and attached presentation's filesize is relatively large. Therefore progress bar appears on screen until upload is completed.")<br/>
Finally, when user is ready to publish created survey, then they simply tap button for uploading it. This can take a while if user's Internet connection is very slow and attached presentation's filesize is relatively large. Therefore progress bar appears on screen until upload is completed.
<br/>![image](/images/38.png?raw=true "If the presentation is successfully stored on the server, confirmation message appears on screen. In case of failure (e.g. filesize exceeds maximum upload limit size on the server-side), error message appears.")<br/>
If the presentation is successfully stored on the server, confirmation message appears on screen. In case of failure (e.g. filesize exceeds maximum upload limit size on the server-side), error message appears.
<br/>![image](/images/39.png?raw=true "Right after that it is possible to add another survey to previously-added presentation. Of course, it is possible to add new survey additionally.")<br/>
Right after that it is possible to add another survey to previously-added presentation. Of course, it is possible to add new survey additionally.
<br/>![image](/images/40.png?raw=true "In the presentation list in home activity, we can notice that now is there item with recently-added presentation. If user taps on it, it extends and shows its detailed information like access code and number of contained surveys.")<br/>
In the presentation list in home activity, we can notice that now is there item with recently-added presentation. If user taps on it, it extends and shows its detailed information like access code and number of contained surveys.
<br/>![image](/images/41.png?raw=true "Finally, lets try to sign in to desktop application as a user with which we were using mobile application in this demo.")<br/>
Finally, lets try to sign in to desktop application as a user with which we were using mobile application in this demo.
<br/>![image](/images/42.png?raw=true "It could be noticed that previously-added presentation is visible here with its Qr access code and can be easily presented to the audience.")<br/>
It could be noticed that previously-added presentation is visible here with its Qr access code and can be easily presented to the audience.

