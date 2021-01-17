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
| ![Imgur](ss/1.jpg) | ![Imgur](ss/2.jpg) | ![Imgur](ss/3.jpg) |
| ![Imgur](ss/4.jpg) | ![Imgur](ss/5.jpg) | ![Imgur](ss/6.jpg) |
| ![Imgur](ss/7.jpg) | ![Imgur](ss/8.jpg) | ![Imgur](ss/9.jpg) |

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

