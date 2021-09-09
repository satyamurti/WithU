<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png"/>
</p>

# withU


  **An Intelligent android app that uses ML model to recognise the tone and emotional state of the statement and automatically trigger actions like sending location to nearby police station thorough sms, she teams etc and also live tracking using geo tracking** 
  
  
[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](https://satyamurti.github.io)


### 💡 Intro

<p align="center">
  <img src="walktroughbanner.jpg" />
</p>

Women are now on respected positions in the country, but if we take a look behind the curtains, we see even then they are being exploited. Each day we read about horrific crimes being committed against women in our country like it’s a norm. Thats why we came up with this app.

#### AI/ML model that recognises the tone and perform counter action:
If it hears keywords like ‘bachao’ or ‘help’, it uses a machine learning model to judge the tone and emotional state of the statement and automatically trigger actions like sending location to nearby police station thorough sms, she teams etc and also live tracking using geo tracking.Our model is cabale of differentiatng between  a cry for real help and a casual conversation like “could you help me with this recipe?”. Native language keywords can also be included.

### Developed by 
- Harsh Khandelwal
- Nikkil V
- Satyamurti D

#### Dataset
- We are using [RAVDESS](https://www.kaggle.com/uwrfkaggler/ravdess-emotional-speech-audio), [SAVEE](https://www.kaggle.com/barelydedicated/savee-database), [TESS](https://www.kaggle.com/ejlok1/toronto-emotional-speech-set-tess) dataset of speech. We filtered some of the data according to our use case which consists of 2000 speech files of 10 different emotions including like female fear, female sad, female happy, male fear and so on. 
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
 - Fast Api - uvicorn[standard] - Librosa - sklearn - soundfile - tqdm - numpy

### 📸 Screenshots

||||
|:----------------------------------------:|:-----------------------------------------:|:-----------------------------------------: |
| ![Imgur](https://i.imgur.com/VVw7O2H.jpg) | ![Imgur](https://i.imgur.com/WFIGLyD.jpg) | ![Imgur](https://i.imgur.com/fW7d4lu.jpg) |
| ![Imgur](https://i.imgur.com/JtZmfs2.jpg) | ![Imgur](https://i.imgur.com/A38OK3c.jpg) |  |
| ![Imgur](https://i.imgur.com/npFHV9O.jpg) | ![Imgur](https://i.imgur.com/74MpuT9.jpg) |  |


### 📌 Features
- [x] AI/ML model that recognises the tone and perform counter action.
- [x] Live location tracking
- [x] SOS 
- [x] Store Recordings For Future Analysis and Investigation.


### ⚡ Getting Started
* Clone or download repository as a zip file.
* Open project in Android Studio.
* Create Firebase project.
* Paste google-services.json file in app/ folder
* In Firebase console enable all Firebase services listed in section Features.
* Finally run the app `SHIFT+F10`.

