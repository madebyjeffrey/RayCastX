package RayCastX

import breeze.linalg._
import breeze.linalg.LinearAlgebra.{cross}
import breeze.numerics._
import scala.math.{sqrt, acos}


abstract class Renderable() {}

case class Ray(origin : DenseVector[Double], direction : DenseVector[Double]) {

}

case class Intersection(at : DenseVector[Double], theta : Double) {}

trait IntersectionTest {
  def intersection(r : Ray) : Option[Intersection]
}



case class Sphere(origin : DenseVector[Double], radius : Double) extends Renderable with IntersectionTest {

  def intersection(r : Ray) : Option[Intersection] = {
    val d = r.direction
    val e = r.origin

    // some conveniences
    val ce = e - origin
    val length_d = d dot d
    val length_ce = ce dot ce
    val dec = d dot ce

    val discriminant = dec*dec - length_d * (length_ce - radius * radius)

    def intersection = {
      // might be exactly one value (t1 == t2)
      val t1 = (-dec + sqrt(discriminant)) / length_d
      val t2 = (-dec - sqrt(discriminant)) / length_d

      val point1 = d * t1 + e
      val point2 = d * t2 + e

      // find the closest to the screen
      // most of the time it will not be equal, so no point in adding another if statement

      val distance1 = (e - point1) norm 2
      val distance2 = (e - point2) norm 2

      val intersect = if (distance1 > distance2) point2 else point1

      val intersection_vector = e - intersect
      val normal = (intersect - origin) / radius

      val theta = acos(intersection_vector dot normal) / (intersection_vector.norm(2) * normal.norm(2))

      Intersection(intersect, theta)
    }

    if (discriminant >= 0)
      Option(intersection)
    else
      None
  }
}

case class Triangle(a : DenseVector[Double], b : DenseVector[Double], c : DenseVector[Double]) extends Renderable with IntersectionTest {


  def intersection(r : Ray) : Option[Intersection] = {
    val d = r.direction
    val e = r.origin

    // basis vectors
    val u = b - a
    val v = c - a
    val w = e + d - a

    // triangle normal
    val n = cross(u, v)

    val t = (n dot (a - e)) / (n dot d)

    val sigma = (w dot cross(n, v)) / (u dot cross(n, v))
    val tau = (w dot cross(n, u)) / (v dot cross(n, u))

    def intersection = {
      val p = e - d * t
      val theta = acos(d dot n) / (d.norm(2) * n.norm(2))

      Intersection(p, theta)
    }


    if ((sigma >= 0) && (tau >= 0) && (sigma + tau <= 1.0))
      Option(intersection)
    else
      None
  }
}
