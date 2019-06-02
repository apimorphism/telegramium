package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represents one row of the high scores table for a game.*/
final case class GameHighScore(
                               /** Position in high score table for the game*/
                               position: Int,
                               /** User*/
                               user: User,
                               /** Score*/
                               score: Int)
