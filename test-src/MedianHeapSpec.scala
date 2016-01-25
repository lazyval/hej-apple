import java.util.NoSuchElementException

import org.scalatest._

class MedianHeapSpec extends FlatSpec with Matchers {

  "MedianHeap" should "pop median of it's elements" in {
    val heap = new MedianHeap
    heap.push(1)
    heap.push(2)
    heap.push(3)
    heap.push(4)
    heap.pop() should be(3)
    heap.pop() should be(2)
    heap.pop() should be(4)
    heap.pop() should be(1)
  }

  it should "return elements in push order if pop in called after every addition" in {
    val heap = new MedianHeap
    heap.push(1)
    heap.pop() should be(1)
    heap.push(2)
    heap.pop() should be(2)
    heap.push(3)
    heap.pop() should be(3)
    heap.push(4)
    heap.pop() should be(4)
  }

  it should "work correctly when duplicates presented" in {
    val heap = new MedianHeap
    heap.push(0)
    heap.push(0)
    heap.pop() should be(0)
    heap.pop() should be(0)
    heap.push(0)
    heap.push(4)
    heap.pop() should be(4)
    heap.pop() should be(0)
  }

  it should "throw NoSuchElementException if an empty heap is popped" in {
    val emptyHeap = new MedianHeap
    a[NoSuchElementException] should be thrownBy {
      emptyHeap.pop()
    }
  }

  it should "return 1 for (0, 1, 1) as input" in {
    val heap = new MedianHeap
    heap.push(0)
    heap.push(1)
    heap.push(1)
    heap.pop() should be(1)
  }

  it should "return median right after inserting arbitrary sequence" in {
    import org.scalacheck.Gen
    import org.scalacheck.Prop.{BooleanOperators, forAll}

    def median(xs: Seq[Int]) = {
      val c = xs.size
      val idx = if (c % 2 == 0) (c / 2) + 1 else (c + 1) / 2
      val ordered = xs.sorted
      ordered(idx - 1)
    }

    val positiveEnough = Gen.choose(0, 300 * 1000 * 1000)
    val cookies = Gen.listOf(positiveEnough)

    val propConcatLists = forAll(cookies) { xs: Seq[Int] => xs.nonEmpty ==> {
      val heap = new MedianHeap
      xs.foreach(heap.push)
      val m = heap.pop()
      m == median(xs)
    }}

    propConcatLists.check
  }
}
