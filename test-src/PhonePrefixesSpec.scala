import PhonePrefixes.Solver
import org.scalatest.concurrent.Timeouts
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

class PhonePrefixesSpec extends FlatSpec with Matchers with Timeouts {
  "Solver" should "return true for solvable problem" in {
    val solvable = Array(
      "113",
      "12340",
      "123440",
      "12345",
      "98346"
    )

    (new Solver).solve(solvable) should be(true)
  }

  it should "return false for non-solvable example" in {
    val nonSolvable = Array(
      "911",
      "97625999",
      "91125426"
    )

    (new Solver).solve(nonSolvable) should be(false)
  }

  it should "0911 is a different phone number from 911" in {
    val solvable = Array("911", "0911")


    (new Solver).solve(solvable) should be(true)
  }

  it should "execute fast for very large sequences" in {
    import org.scalatest.time.SpanSugar._

    val bigInput = Random.alphanumeric.grouped(10).map(x => x.mkString).take(10000).toArray

    failAfter(100 millis) {
      (new Solver).solve(bigInput)
    }
  }
}
