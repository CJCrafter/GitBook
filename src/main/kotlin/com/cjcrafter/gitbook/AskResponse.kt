package com.cjcrafter.gitbook

data class AskResponse(
    val answer: Answer
) {

    val text: String
        get() = answer.text

    val followupQuestions: List<String>
        get() = answer.followupQuestions

    val pages: List<Answer.Page>
        get() = answer.pages

    data class Answer(
        val text: String,
        val followupQuestions: List<String>,
        val pages: List<Page>
    ) {
        data class Page(
            val page: String,
            val revision: String,
            val space: String,
            val sections: List<String>
        )
    }
}