package io.github.fperiodic.apimorphism.telegramium.bots

/** This object contains information about a poll.*/
final case class Poll(
                      /** Unique poll identifier*/
                      id: String,
                      /** Poll question, 1-255 characters*/
                      question: String,
                      /** List of poll options*/
                      options: List[PollOption] = List.empty,
                      /** True, if the poll is closed*/
                      isClosed: Boolean)
