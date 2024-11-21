# PagingLib3-Compose
A simple demo project for demonstrating the <b>Paging3 library</b> with <b>Jetpack Compose</b>. More details on how to implement Paging3 library with Jetpack Compose can be found [here]([url](https://devanddroid.com/2024/11/20/5-steps-to-use-paging3-library-with-jetpack-compose/)).

<img src="https://github.com/anitaa1990/PagingLib3-Sample/blob/main/media/1.png" width="200" style="max-width:100%;"><img src="https://github.com/anitaa1990/PagingLib3-Sample/blob/main/media/2.png" width="200" style="max-width:100%;"> <img src="https://github.com/anitaa1990/PagingLib3-Sample/blob/main/media/3.png" width="200" style="max-width:100%;"><img src="https://github.com/anitaa1990/PagingLib3-Sample/blob/main/media/4.png" width="200" style="max-width:100%;"><img src="https://github.com/anitaa1990/PagingLib3-Sample/blob/main/media/5.png" width="200" style="max-width:100%;"></br></br>

### App Features
• Users can view the latest news from the [news api](https://newsapi.org/).
• Users can also search for any news from the api.
• Supports pagination so you can literally view all news from around the world in the last 5 years.

#### App Architecture 
Based on mvvm architecture and repository pattern.

#### The app includes the following main components:

* A web api service.
* Pagination support for data received from the api.
* A repository that works with the api service, providing a unified data interface.
* A ViewModel that provides data specific for the UI.
* The UI, using Jetpack Compose, which shows a visual representation of the data in the ViewModel.
* Unit Test cases for API service and Paging source.


#### App Packages
* <b>data</b> - contains 
    * <b>remote</b> - contains the api classes to make api calls to MovieDB server, using Retrofit. 
    * <b>local</b> - contains the db classes to cache network data.
    * <b>repository</b> - contains the repository classes, which acts as a bridge between the db, api and the paging classes.
    * <b>source</b> - contains the remote mediator and paging source classes, responsible for checking if data is available in the db and triggering api requests, if it is not, saving the response in the database.
* <b>module</b> - contains dependency injection classes, using Hilt.   
* <b>ui</b> - contains compose components and classes needed to display movie/tv list and movie/tv detail screen.
* <b>util</b> - contains util classes needed for compose redirection, ui/ux animations.

