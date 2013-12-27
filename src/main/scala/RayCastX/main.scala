package RayCastX

import breeze.linalg._
import breeze.linalg.LinearAlgebra.{cross}
import breeze.numerics._
import ps.tricerato.pureimage._
import java.io._

object Main extends App {
	println("Rayness")

  val sphere = Sphere(DenseVector(0.5, 0.5,0.5), 0.25)
  val img = Scene(640, 640, sphere)

  val f = new FileOutputStream("test.png")
  f.write(Output(img, PNG))
  f.close


}

