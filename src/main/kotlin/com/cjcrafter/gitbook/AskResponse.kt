package com.cjcrafter.gitbook

/**
 * Data class wrapping the GitBook API's response to an ask request. Since the
 * response might be null, you should check [hasAnswer] before accessing other
 * data.
 *
 * @property answer
 * @constructor Create empty Ask response
 */
data class AskResponse(
    val answer: Answer?
) {

    /**
     * Returns true if the wiki had enough context to answer the question. When
     * this is false, that could mean:
     * 1. The wiki did not have enough information.
     * 2. The question was not understood or off-topic.
     */
    val hasAnswer: Boolean
        get() = answer != null

    /**
     * Shorthand for [Answer.text].
     *
     * @throws IllegalStateException if the wiki could not answer the question.
     */
    val text: String
        get() = answer?.text ?: throw IllegalStateException("The wiki could not answer your question.")

    /**
     * Shorthand for [Answer.followupQuestions].
     *
     * @throws IllegalStateException if the wiki could not answer the question.
     */
    val followupQuestions: List<String>
        get() = answer?.followupQuestions ?: throw IllegalStateException("The wiki could not answer your question.")

    /**
     * Shorthand for [Answer.pages].
     *
     * @throws IllegalStateException if the wiki could not answer the question.
     */
    val pages: List<Answer.Page>
        get() = answer?.pages ?: throw IllegalStateException("The wiki could not answer your question.")

    /**
     * Data class wrapping the response from the GitBook API.
     *
     * @property text The human-readable answer.
     * @property followupQuestions A (potentially empty) list of followup questions.
     * @property pages The sources used to construct the answer.
     * @constructor Instantiates a new answer.
     */
    data class Answer(
        val text: String,
        val followupQuestions: List<String>,
        val pages: List<Page>
    ) {

        /**
         * Data class wrapping a page used as a source for the answer. The data
         * stored here is "a garbled mess," but can be used to pull pages from
         * the wiki.
         *
         * @property page The page's id.
         * @property revision The page's revision id.
         * @property space The wiki space id.
         * @property sections The section id.
         * @constructor Instantiates a new page.
         */
        data class Page(
            val page: String,
            val revision: String,
            val space: String,
            val sections: List<String>
        )
    }
}