package telegramium.bots

/** This object represents the audios displayed on a user's profile.
  *
  * @param totalCount
  *   Total number of profile audios for the target user
  * @param audios
  *   Requested profile audios
  */
final case class UserProfileAudios(totalCount: Int, audios: List[Audio] = List.empty)
