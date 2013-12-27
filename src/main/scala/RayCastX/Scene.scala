
package RayCastX

/*
package object RayCastX {
  def render_scene(width : Int, height : Int, output : String, object : Renderable) {

  }

}

*/

import scala.math.{acos}
import ps.tricerato.pureimage._
import breeze.linalg._

case class Scene(width : Int, height : Int, obj : IntersectionTest) extends Image[RGB] {
  def apply(x : Int, y : Int) : RGB = {
    val fx = x.toDouble / width.toDouble
    val fy = y.toDouble / height.toDouble
    def thetamod(angle : Double) = (angle - acos(-1)) / acos(-1)

    obj.intersection(Ray(DenseVector(fx, fy, 0.0), DenseVector(0.0, 0.0, 1.0))) match {
      case Some(a) => RGB.fromChannels((thetamod(a.theta) * 255.0).toInt, (thetamod(a.theta) * 255.0).toInt, (thetamod(a.theta) * 255.0).toInt)
      case None => RGB.fromChannels(0, 0, 0)
    }
  }
}

