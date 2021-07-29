package com.gitHub.dataGenerator.generator.available

import scala.collection.mutable

object MainTest extends App {

  val pq = mutable.PriorityQueue[Int]()
  pq.addOne(9)
  pq.addOne(5)
  pq.addOne(7)

  println(pq.clone.dequeueAll)
  println(pq.clone.dequeue())


  // ######################

  def diff(t2: (Int,Int)) = math.abs(t2._1 - t2._2)
  val x = mutable.PriorityQueue[(Int, Int)]()(Ordering.by(diff))

  x.enqueue(1 -> 1)
  x.enqueue(1 -> 2)
  x.enqueue(1 -> 3)
  x.enqueue(1 -> 4)
  x.enqueue(1 -> 0)

  println(x.clone.dequeueAll)

  val t : Integer = 5
  println(t == 5)

}
