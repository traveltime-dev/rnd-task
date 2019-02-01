package review

import donotmodifyme.Scenario1._

object Scenario1 {
  /*
   * Given a `blackBoxPositiveInt` 
   *  - a black box procedure returning `Int` that's outside of our control (i.e an external library call
   * or a call to a service)
   *
   * @return the amount of calls to `blackBoxPositiveInt` needed, so that the sum of all returned values from
   * `blackBoxPositiveInt` would be equal to @input `total` 
   *
   * blackBoxPositiveInt:
   *  - can side effect
   *  - is delivered by a third party, we don't know how it operates
   */
  def process(total: Int): Int = {
    helper(total, 0)
  }

  def helper(total: Int, n: Int): Int = {
    if (total == 0) n
    else {
      helper(total - blackBoxPositiveInt, n + 1)
    }
  }
}
