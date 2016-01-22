import collection.mutable.Stack
import org.scalatest._

class ExampleSpec extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
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
    heap.push(0)
    heap.pop() should be(0)
    heap.pop() should be(0)
    heap.pop() should be(0)
    heap.push(4)
    heap.pop() should be(4)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyHeap = new MedianHeap
    a[NoSuchElementException] should be thrownBy {
      emptyHeap.pop()
    }
  }
}
