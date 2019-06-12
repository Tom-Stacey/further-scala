package topics.jsonformatting.logins

import play.api.libs.json._

import scala.reflect.ClassTag

trait      LoginRequest
case class PlainTextLoginRequest(username: String, password: String)            extends LoginRequest
case class HashedPasswordLoginRequest(username: String, hashedPassword: String) extends LoginRequest

object LoginRequest {
  val apiReads: Reads[LoginRequest] = new Reads[LoginRequest] {
    override def reads(json: JsValue): JsResult[LoginRequest] = {
      json.validate[JsObject].flatMap { jsObject =>
        val requestMap = jsObject.value
        (requestMap.get("username"), requestMap.get("password"), requestMap.get("hashedPassword")) match {
          case (Some(uname), Some(pwrd), None)  => createPlainTextLoginRequest(uname, pwrd)
          case (Some(uname), None, Some(hPwrd)) => createHashedPasswordLoginRequest(uname, hPwrd)
          case (None, _, _)                     => JsError("Username must be supplied")
          case (Some(_), Some(_), Some(_))      => JsError("Both password and hashed password are present in request")
          case (Some(_), None, None)            => JsError("Either password or hashed password must be present in request")
        }
      }
    }
  }

  private def createPlainTextLoginRequest(username: JsValue, password: JsValue): JsResult[PlainTextLoginRequest] = {
    for {
      uname <- username.validate[JsString]
      pwrd  <- password.validate[JsString]
    } yield PlainTextLoginRequest(uname.value, pwrd.value)
  }

  private def createHashedPasswordLoginRequest(username: JsValue, hashedPassword: JsValue): JsResult[HashedPasswordLoginRequest] = {
    for {
      uname <- username.validate[JsString]
      pwrd  <- hashedPassword.validate[JsString]
    } yield HashedPasswordLoginRequest(uname.value, pwrd.value)
  }

  // in case you're really serious about not repeating code
  private def createLoginRequest[T <: LoginRequest](username: JsValue, password: JsValue)(implicit tag: ClassTag[T]): JsResult[T] = {
    for {
      uname <- username.validate[JsString]
      pwrd  <- password.validate[JsString]
    } yield {
      tag.runtimeClass.getConstructor(classOf[String], classOf[String]).
        newInstance(uname.value, pwrd.value).asInstanceOf[T]
    }
  }
}
