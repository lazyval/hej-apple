import PhonePrefixes.Problem
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

    Problem.solve(solvable) should be(true)
  }

  it should "return false for non-solvable example" in {
    val nonSolvable = Array(
      "911",
      "97625999",
      "91125426"
    )

    Problem.solve(nonSolvable) should be(false)
  }

  it should "return false if there are many ways to fail" in {
    val solvable = Array(
      "91125999",
      "911",
      "91125426"
    )

    Problem.solve(solvable) should be(false)
  }

  it should "0911 is a different phone number from 911" in {
    val solvable = Array("911", "0911")


    Problem.solve(solvable) should be(true)
  }

  it should "execute fast for very large sequences" in {
    import org.scalatest.time.SpanSugar._

    val bigInput = Random.alphanumeric.grouped(10).map(x => x.mkString).take(10000).toArray

    failAfter(100 millis) {
      Problem.solve(bigInput)
    }
  }

  it should "return true for a single number" in {
    val singleton = Array("911")

    Problem.solve(singleton) should be(true)
  }

  it should "return false for (1, 57, 5)" in {
    val singleton = Array("1", "57", "5")

    Problem.solve(singleton) should be(false)
  }

  it should "return median right after inserting arbitrary sequence" in {
    import org.scalacheck.Gen
    import org.scalacheck.Prop.{BooleanOperators, forAll}

    def answer(xs: Seq[String]) = {
      xs.combinations(2).forall { case Seq(x, y) =>
        val fail = x.startsWith(y) || y.startsWith(x)
        ! fail
      }
    }

    val numbers = Gen.listOf(Gen.numStr)

    val propConcatLists = forAll(numbers) { xs: Seq[String] => xs.nonEmpty ==> {
      PhonePrefixes.Problem.solve(xs.distinct.toArray) == answer(xs.distinct)
    }}

    propConcatLists.check
  }
}
