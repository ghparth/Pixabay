# Pixabay

This is a basic app that allow user to search images on Pixabay.

I have followed Clean Architecture i.e All the dependency follow inwards
View (Activity)  ->  Presenter -> Repository

For Dependency Injection, I have used Dagger 2

#####Network
I have used Retrofit + OkHttp3 at the network layer.
Caching is done by OkHttp3 at network layer as the requirement of Pixabay is to cache responses for a day.

#####Repo
I have used a Mapper in between Presenter/Repo so that Presenter only gets the data that it needs and in the format that it need.

#####Presenter
Presenter controls the logic of pagination. On receiving response from server notifies the View (Activity abstracted by interface).

#####UI
UI has been kept very simplistic. For pagination there is a button at the bottom of the page.
I have used Picaso (Although abstracted) for Image loading

