package topics.jsonformatting.personaldetails

import play.api.libs.json.{Json, OFormat, Reads}

case class PersonalDetails(
                          name: String,
                          age: Int,
                          email: Option[String]
                          )

object PersonalDetails {

  implicit val format: OFormat[PersonalDetails] = Json.format[PersonalDetails]

  /*
  {
    "user-name": "name",
    "user-age-years": 28,
    "email-address": "test@test.com"
  }
   */
  val mediaReads: Reads[PersonalDetails] = Json.reads[PersonalDetails]

  /*
  {
    "first-name": "first",
    "surname": "last",
    "date-of-birth": "YYYY-mm-DD",
    "email": "test@test.com"
  }
   */
  val campusReads: Reads[PersonalDetails] = Json.reads[PersonalDetails]

}
