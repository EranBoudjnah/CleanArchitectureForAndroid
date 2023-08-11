# Clean Architecture for Android Sample Project

This project is a loose implementation of Clean Architecture as presented in my book,
[Clean Architecture for Android](
https://amzn.to/43cUuhb). It is a native Android project written in Kotlin. It demonstrates
the key principles presented in the book and how they apply to a real life project.

I will endeavour to keep this project up to date and use it to demonstrate the strengths of the
architecture: **scalability**, **testability** and **flexibility** when it comes to choosing 3rd
party solutions.

### Features

- Feature separation
- Layer separation
    - UI
    - Presentation
    - Domain
    - Data
    - Data Source
- Unit tests
- End-to-end tests
- Demonstrates use of [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Demonstrates use of [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
  including [Flow](https://kotlinlang.org/docs/flow.html)
- Demonstrates <abbr title="Model View ViewModel">MVVM</abbr>
- Code quality checks using [ktlint](https://github.com/pinterest/ktlint)
- Code quality checks using [detekt](https://github.com/detekt/detekt)
- Continuous integration (CI) using [GitHub Actions](https://github.com/features/actions)
    - Unit tests
    - Instrumentation tests
    - Linting

### Choices

- ***Mappers as classes*** **vs.** ***mapping extension functions***

  When mapping between models, we have several options. The primary decision is
  between mapper classes and mapping extension functions.

  While extension functions are more concise, using them for mapping limits our choices of
  testing frameworks ([Mockito](
  https://site.mockito.org/), for example, cannot stub static functions).

  How about injecting the mapper extension functions? We could do that. However, this
  removes the benefits of conciseness almost entirely. It also makes navigation to the
  implementation harder.

  And so, I opted for the slightly more verbose concrete mapper classes.

- **Skipping Google's [Architecture Components](
  https://developer.android.com/reference/androidx/lifecycle/package-summary)**

  The greatest issue with Google's Architecture Components is that they leak Android
  details into the Presentation layer. This prevents the Presentation layer from being
  truly UI agnostic.

  Another issue with the Architecture Components is that they give too much responsibility
  to the ViewModel. They make it persist state it does not own, leading to potential data
  synchronization bugs.

  For these reasons, while still following MVVM, this project relies on **Kotlin Flows** rather
  than **LiveData**, and implements pure ViewModels rather than Google's.

- **Mocking framework**

  Both [Mockito-Kotlin](https://github.com/mockito/mockito-kotlin) and [Mockk](
  https://mockk.io/) are used in this project to demonstrate how the use of each would look.

  My personal preference remains **Mockito-Kotlin**. I find the code easier to read and follow
  when using it. At the time of writing, judging by the number of stars on each repository,
  the industry seems to lean towards Mockk.

  I was asked about using *fakes*. I have explored fakes, and found them to be overly verbose
  and too expensive to maintain.

- **Dependency injection framework**

  A critical part of most modern apps, dependency injection (DI) helps us obtain the objects
  that build our app. It also helps manage their scope. The most popular choices in the
  Android world are [Hilt](https://dagger.dev/hilt/) (which is built on top of [Dagger](
  https://dagger.dev/)) and [Koin](https://insert-koin.io/).

  **Hilt** was chosen for two main reasons:
    1. **Compile time safety** - having the confidence that all my dependencies are provided
       before the app starts is a huge time saver and helps maintain a stable app.
    2. **Simplicity** - from experience, setting up and using Hilt (unlike the underlying Dagger)
       is considerably easier than using Koin. Hilt also introduces fewer breaking changes
       over time.

- ***XML*** **vs** ***Jetpack Compose***

  Why not both? I still have a lot of concerns around **Jetpack Compose**. Even so, it was
  important to me to show the presented architecture works well regardless of the UI
  mechanism chosen. As an exercise, I invite you to try and replace the UI layer from Compose 
  to XML or vice versa without updating the Presentation layer.

### Links

[Clean Architecture for Android on Amazon](https://amzn.to/43cUuhb "Clean Architecture for Android")

[Clean Architecture on the Clean Coder Blog](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html "Clean Architecture")
