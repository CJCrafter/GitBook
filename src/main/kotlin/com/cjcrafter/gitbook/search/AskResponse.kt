package com.cjcrafter.gitbook.search

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
     * Shorthand for [Answer.sources].
     *
     * @throws IllegalStateException if the wiki could not answer the question.
     */
    val sources: List<Answer.Source>
        get() = answer?.sources ?: throw IllegalStateException("The wiki could not answer your question.")

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
        val sources: List<Source>
    ) {

        sealed class Source {

            data class PageSource(
                val page: String,
                val revision: String,
                val space: String,
                val sections: List<String>
            ): Source()

            data class EntitySource(
                val entityId: String,
                val entityType: String,
                val integration: String?
            ) : Source()

            data class CaptureSource(
                val captureId: String,
                val source: String
            ) : Source()
        }
    }
}