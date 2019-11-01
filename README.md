# Photo Gallery Challenge

## A simple photo gallery app for Android

* Fetches images from Unsplash.com via their API
* Written in Kotlin
* MVVM Architecture
* Uses ***Retrofit2***, ***OkHTTP3***, ***Moshi*** & ***Glide*** & ***Timber*** libraries
* Offline viewing support via a local database built with ***Room***
* Limited unit testing (mostly local)

## Screenshots:

The app displays images in two layouts
> * **Full-width** or **Grid tiles**
> > ![](demo_1.gif)


Supports limited offline viewing by using a local database to store image URLS and metadata
> * Images which are still in Glide's cache will be displayed
> * Prompt for the user to reconnect
> > ![](demo_2.gif)


## Installation:

The app needs to be initialized with an Unsplash API key. You can generate a new API key by registering an account and following the instructions at https://unsplash.com/developers .

Once you have your API key, insert it into the **`./photo-gallery-challenge/keystore.properties`** file:


> *example:* `UNSPLASH_API_ACCESS_KEY =  "456789876545678909876543"`


Compile and run the app via Android Studio.

*For convenience, there is pre-compiled APK also included in the [release](https://github.com/akueisara/photo-gallery-challenge/releases) page*


## Roadmap for improvements:

* Add more local and instrumented unit tests
* Implement a search bar
* 'Like' / 'Unlike' or 'add to favorites' function

## Credits:

<div>Icons made by <a href="https://www.flaticon.com/authors/google" title="Google">Google</a> from <a href="https://www.flaticon.com/"title="Flaticon">www.flaticon.com</a></div>
