package telegramium.bots

/** This object represents the content of a service message, sent whenever a user
  * in the chat triggers a proximity alert set by another user.*/
final case class ProximityAlertTriggered(
                                         /** User that triggered the alert*/
                                         traveler: User,
                                         /** User that set the alert*/
                                         watcher: User,
                                         /** The distance between the users*/
                                         distance: Int)
