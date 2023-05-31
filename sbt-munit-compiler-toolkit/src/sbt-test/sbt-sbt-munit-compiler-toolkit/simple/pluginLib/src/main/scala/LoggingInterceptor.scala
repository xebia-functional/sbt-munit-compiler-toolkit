package example

import scala.util.Try

class LoggingInterceptor[A](intercepted: => A):
  def apply(name: String): A = 
    println(s"$name called")
    val result = Try(intercepted)
    result.get
  def apply(name: String)(arguments: Any*): A = 
    println(s"$name called with ${arguments.map(_.toString).mkString(", ")}")
    val result = Try(intercepted)
    result.get
