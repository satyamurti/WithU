<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png"/>
</p>

# WithU


  **always a sheild for u
  
  
[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](https://satyamurti.github.io)


### 💡 Introduction

<p align="center">
  <img src="walktroughbanner.jpg" />
</p>

#### AI/ML model that recognises the tone and perform counter action:
If it hears keywords like ‘bachao’ or ‘help’, it should use a machine learning or artificial intelligence model to judge the tone or emotional state of the statement and automatically trigger some actions like sending texts to nearby police station, she teams etc with geo co-ordinates and start live navigation with them. The model should be able to differentiate a cry for real help from a casual conversation like “could you help me with math test?”. Native language keywords can also be included.

### Developed by 
- Harsh Khandelwal
- Nikkil V
- Satyamurti D

#### Dataset
- We are using RAVDESS, SAVEE, TESS dataset of speech. We filtered some of the data according to our use case which consists of 2000 speech files of 10 different emotions including like female fear, female sad, female happy, male fear and so on. 
#### Feature Extraction
- MFCC Features:
MFCCs are the Mel Frequency Cepstral Coefficients. MFCC takes into account human perception for sensitivity at appropriate frequencies by converting the conventional frequency to Mel Scale, and are thus suitable for speech recognition tasks quite well
- MELL Spectrogram:
A Fast Fourier Transform is computed on overlapping windowed segments of the signal, and we get what is called the spectrogram. This is just a spectrogram that depicts amplitude which is mapped on a Mel scale.
- Chroma:
A Chroma vector is typically a 12-element feature vector indicating how much energy of each pitch class is present in the signal in a standard chromatic scale.

#### Models
 - Multilayer perceptron classifier:
It is capable for understanding complex relation ships between features and labels, it is based on Artificial neural network. It uses no linear activation functions for deriving hidden layer values.
It uses Adam optimizer by default for optimization task, also it uses nonconvex loss functions which gives a drawback of stocking in local optima. The model reaches on accuracy of 67 percent accuracy on validation dataset which is 25 percent of total samples.

- Support vector machine classifier:
SVm classifiers is also used for ensemble and making a robust output. We used kernel linear in our project. 

#### Library Section 
 - Librosa - sklearn - soundfile - tqdm - numpy

### 📸 Screenshots

||||
|:----------------------------------------:|:-----------------------------------------:|:-----------------------------------------: |
| ![Imgur](ss/1.jpg) | ![Imgur](ss/2.jpg) | ![Imgur](ss/3.jpg) |
| ![Imgur](ss/4.jpg) | ![Imgur](ss/5.jpg) | ![Imgur](ss/6.jpg) |
| ![Imgur](ss/7.jpg) | ![Imgur](ss/8.jpg) | ![Imgur](ss/9.jpg) |

### 📌 Features
- [x] Authentication using Firebase Auth
- [x] One to One Chats
- [x] Group Chats
- [x] Text messages
- [x] Audio, Video and Document Messages
- [x] Selfie Camera
- [x] Augmented Reality Filters in camera
- [x] User Mentions
- [x] Emojis Support
- [x] Edit Profile 
- [x] Search People
- [x] Online/Last Active Status
- [x] Push Notifications
### ⚙️ Future Plans
Here's a list of features that I have planned to include overtime.
- [ ] Custom Stickers
- [ ] Video and Audio Calls
- [ ] Live Streaming
- [ ] Library Section (Similar to Google Classroom)
- [ ] Phone Number Authentication
- [ ] GIF Messages
- [ ] Location Messages
- [ ] Link/URL Preview Messages
- [ ] Reply Messages
- [ ] Message Read/Receipt Ticks
- [ ] Unread Messages Count
- [ ] Typing Indicator
- [ ] Chat / Group Media, Documents, and Links
- [ ] Restore/Export from and to CSV File
- [ ] Chat Heads
- [ ] Block/Unblock Users

### Built With 🛠
* [Kotlin](https://kotlinlang.org/)
* [MVVM](https://developer.android.com/jetpack/docs/guide)
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Asynchronous programming 
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) - Declaratively bind observable data to UI elements.
* [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle) - Create a UI that automatically responds to lifecycle events.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Build data objects that notify views when the underlying database changes.
* [Navigation](https://developer.android.com/guide/navigation/) - Handle everything needed for in-app navigation.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks.
* [Firebase](https://firebase.google.com/docs) - Tools to develop high-quality apps.
  - [Authentication](https://firebase.google.com/docs) - Allows an app to securely save user data in the cloud.
  - [Cloud Firestore](https://firebase.google.com/docs/firestore) - Flexible, scalable NoSQL cloud database to store and sync data.
  - [Cloud Functions](https://firebase.google.com/docs/functions) - Automatically run backend code in response to events triggered by Firebase 
  - [Cloud Messaging](https://firebase.google.com/docs/cloud-messaging) - Notify a client app.
  - [Cloud Storage](https://firebase.google.com/docs/storage) - Store and serve user-generated content.
* [Glide](https://github.com/bumptech/glide) - Load and cache images by URL.
* [Retrofit 2](https://github.com/square/retrofit) - Handle REST api communication.

### Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
### 🤝 Contribute
Awesome! If you would like to contribute with above features or submit a bugfix, you're always welcome !
See [Contributing Guidelines](CONTRIBUTING.md). 

### ⚡ Getting Started
* Clone or download repository as a zip file.
* Open project in Android Studio.
* Create Firebase project.
* Paste google-services.json file in app/ folder
* In Firebase console enable all Firebase services listed in section Features.
* Finally run the app `SHIFT+F10`.

