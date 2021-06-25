package telegramium.bots

/**
 * This object represents the content of a service message, sent whenever a user
 * in the chat triggers a proximity alert set by another user.
 *
 * @param traveler User that triggered the alert
 * @param watcher User that set the alert
 * @param distance The distance between the users
 */
final case class ProximityAlertTriggered(traveler: User, watcher: User, distance: Int)
