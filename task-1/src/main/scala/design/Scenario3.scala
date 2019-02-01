package design

object Scenario3 {
  /**
   * Write a class to represent and create a user
   * 
   * - User has a name/surname
   * - User has a username
   * - Username can only consist of characters "[a-z][A-Z][0-9]-._"
   * - User has a level
   * - User starts from level 0 and can only increase.
   * - User has experience
   * - User gets experience each time he posts or is reposted. 
   * - The experience transfers to levels on midnight each day
   * - The experience can't ever be negative
   * - An user is either a free user or a paid user.
   * - A free user has a limit to the amount of posts he can write per day.
   * - A paid user has a counter of the remaining paid days
   */
  class User


  object UserLogic {
    /*
     * This logic will be run each midnight every day. It should:
     *   1) give a level for each 1000exp, the remaining experience goes to the next day
     *   2) if a free user is under 3 posts refresh the number of posts he can publish to 3
     *   3) paid users reduce their days remaining count
     *
     * Other functions (that you don't need to write) modify the amount of posts a user can 
     * still post when they post, give experience for posts, might increase or decrease a free users limit
     * etc.
     */
    def runAtMidnight(user: User): User = ???
  }
}
