<div align="center">

# GitBook Java/Kotlin API
[![Maven Central](https://img.shields.io/maven-central/v/com.cjcrafter/gitbook?color=blue&label=Download)](https://central.sonatype.com/artifact/com.cjcrafter/gitbook)
[![](https://img.shields.io/badge/-docs%20-blueviolet?logo=Kotlin&colorA=gray)](https://gitbook.cjcrafter.com/)
[![](https://img.shields.io/github/discussions/CJCrafter/GitBook)](https://github.com/CJCrafter/GitBook/discussions)
[![License](https://img.shields.io/github/license/CJCrafter/GitBook)](https://github.com/CJCrafter/GitBook/blob/master/LICENSE)

A community-maintained easy-to-use Java/Kotlin GitBook API for searching wiki pages, and using the 
Lens feature to ask questions. Great for chatbots.
</div>

## Features
* [Search](https://developer.gitbook.com/gitbook-api/reference/search) for searching and asking questions.

## Installation
For Kotlin DSL (`build.gradle.kts`), add this to your dependencies block:
```kotlin
dependencies {
    implementation("com.cjcrafter:gitbook:1.0.0")
}
```
For Maven projects, add this to your `pom.xml` file in the `<dependencies>` block:
```xml
<dependency>
    <groupId>com.cjcrafter</groupId>
    <artifactId>gitbook</artifactId>
    <version>1.0.0</version>
</dependency>
```
See the [maven repository](https://central.sonatype.com/artifact/com.cjcrafter/gitbook/) for gradle/ant/etc.


## Working Example
```kotlin
fun main() {
    val gitbook = GitBookApi.builder()
        .apiKey("gb_api_yourapikey")
        .build()
    val request = AskRequest(query = "what is the meaning to life?")
    //val location = SearchLocation.Organization("MgHAZkcfIhs3YcmBjk2r")

    val response = gitbook.ask(request)
    if (response.isFailure) {
        println("Failed to connect to API: ${response.exceptionOrNull()}")
        return
    }

    val answer = response.getOrThrow().answer
    if (answer == null) {
        println("You must have asked for forbidden knowledge, since the wiki could not confidently answer your question.")
        return
    }

    println("The wiki says: ${answer.text}")
}
```
> **Note**: You should not include your API key in your code. 

## Support
If I have saved you time, please consider [sponsoring me](https://github.com/sponsors/CJCrafter).

## License
This GitBook API is an open-sourced software licensed under the [MIT License](https://github.com/CJCrafter/ChatGPT-Java-API/blob/master/LICENSE).
**This is an unofficial library, and is not affiliated with GitBook**.