package topics.jsonformatting.addresses

import play.api.libs.json.{Json, Reads}

case class Address(
                  line1: String,
                  line2: String,
                  line3: Option[String],
                  line4: Option[String],
                  postcode: Option[String],
                  country: Option[String]
                  )

object Address {
  // default reads for your internal microservice to microservice calls
  implicit val reads: Reads[Address] = Json.reads[Address]

  // post office addresses are an array of 2-4 'addressLines' and a postcode
  val postOfficeReads: Reads[Address] = Json.reads[Address]

  // international addresses have 'street1', optional 'street2', 'town', 'area', optional 'areaCode', 'country'
  val internationalReads: Reads[Address] = Json.reads[Address]
}