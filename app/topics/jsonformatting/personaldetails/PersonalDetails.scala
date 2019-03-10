package topics.jsonformatting.personaldetails

import play.api.libs.json.{Json, OFormat}

case class PersonalDetails(
                          name: String,
                          age: Int,
                          email: Option[String]
                          )

object PersonalDetails {
  implicit val format: OFormat[PersonalDetails] = Json.format[PersonalDetails]
}
