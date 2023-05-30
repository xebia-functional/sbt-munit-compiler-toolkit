package example

import scala.util.Try

class LoggingInterceptor[A](intercepted: => A):
  def apply(name: String): A = 
    val result = Try(intercepted)
    result.get
  def apply(name: String)(arguments: Any*): A = 
    val result = Try(intercepted)
    result.get
