# Recipe Master

The goal of this repository is to showcase a fairly simple android application. It is developed using various good practices: Clean architecture, TDD, UI-testing, using popular third party libraries,... It presents a recipe application where one can find various recipes coming from a backend server (in this case a Wiremock stub). The user can also manage its refrigerator and shopping list.

<img src="https://user-images.githubusercontent.com/48157659/127305828-d59f8129-136d-4012-aa24-cc29d8afd73f.png" width=350 align="left">
<img src="https://user-images.githubusercontent.com/48157659/127306371-920c9df0-59e6-40ad-ad6e-122855f08157.png" width=350 align="right">
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>


## How to run
The recommended way is to use Android Studio. You can also do everything from the command line:

Build the app:
`./gradlew build`

Run unit tests:
`./gradlew test`

Run instrumentation tests:
`./gradlew connectedAndroidTest`

## Application architecture
<img width="628" alt="Screenshot 2021-07-28 at 13 45 21" src="https://user-images.githubusercontent.com/48157659/127316950-7f7aa271-4ee7-4d22-9ad3-dc5c7c5afe58.png">

#### :app
The application module, it uses both the recipes and stock modules. It contains code specific to the application and not it's functionalities. That responsibility is delegated to the corresponding modules.
#### :features:recipes
The recipe module is responsible of displaying the recipes.
#### :features:stock
The stock module is responsible of managing both the refrigerator and the shopping list.

## Languages, Frameworks and libraries
- [Kotlin](https://www.googleadservices.com/pagead/aclk?sa=L&ai=DChcSEwjY6IT73IXyAhWRBogJHfW7CUgYABAAGgJxbg&ei=KUgBYaa4Lbay5NoPrPai6Ak&ohost=www.google.com&cid=CAESQOD2g2b4tY5ySLjpMEThga0zc_qHV8srzHZJ8QtSRmMG5RGcx8IUjlMqw1-tG5H0ANqvkeaZkQJ7kQntKQV6P0U&sig=AOD64_1FCKQJvIGZbgJV_o4qv2-2aRvKNg&q&sqi=2&adurl&ved=2ahUKEwjm8Pv63IXyAhU2GVkFHSy7CJ0Q0Qx6BAgCEAE)
- [Android Jetpack](https://developer.android.com/jetpack#architecture-components)
  - [Room](https://developer.android.com/jetpack/androidx/releases/room)
  - [MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel)
  - [Navigation Component](https://developer.android.com/guide/navigation)
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
- [Kotest](https://kotest.io/)
- [Kakao](https://github.com/agoda-com/Kakao)
- [Espresso](https://developer.android.com/training/testing/espresso)
- [Koin](https://insert-koin.io/)
- [Arrow](https://arrow-kt.io/)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://square.github.io/okhttp/)
- [GSON](https://github.com/google/gson)
