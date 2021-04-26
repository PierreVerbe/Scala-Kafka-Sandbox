package com.gitHub.dataGenerator.generator

import com.gitHub.dataGenerator.generator.available.{GeneratorA, GeneratorB}

class GeneratorFactory {

  def getGenerator(nameGenerator: String): Option[AbstractGenerator] = {
    nameGenerator match {
      case "GeneratorA" => Some(new GeneratorA)
      case "GeneratorB" => Some(new GeneratorB)
      case _ => None
    }
  }

}
