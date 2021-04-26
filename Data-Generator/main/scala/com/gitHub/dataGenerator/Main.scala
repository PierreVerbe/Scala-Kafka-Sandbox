package com.gitHub.dataGenerator

import com.gitHub.dataGenerator.generator.GeneratorFactory
import com.gitHub.dataGenerator.model.Trip
import org.scalacheck.Gen

object Main extends App {
  println("Hello World")

  val tripTest = Trip(1, "Hello", "World")
  println(tripTest)

  val truc = Gen.frequency(
    1 -> Trip(1, "a", "a"),
    2 -> Trip(2, "b", "b"),
    1 -> Trip(3, "c", "c")
  )

  println(truc.sample.get)

  val factory = new GeneratorFactory()
  val generatorA = factory.getGenerator("GeneratorA")
  generatorA.get.methodGenerator()
}
