package topics.jsonformatting.logins

import play.api.libs.json.{Json, OFormat}

case class SimpleLoginRequest(
                       username: String,
                       password: Option[String],
                       hashedPassword: Option[String]
                       )

object SimpleLoginRequest {
  implicit val format: OFormat[SimpleLoginRequest] = Json.format[SimpleLoginRequest]
}
