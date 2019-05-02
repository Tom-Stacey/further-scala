package topics.jsonformatting.addresses

import org.scalatest.{Ignore, Matchers, WordSpec}
import play.api.libs.json.{JsSuccess, Json}
import Address.{internationalReads, postOfficeReads}

class AddressSpec extends WordSpec with Matchers {

  "Reading an address from internal structure" should {

    "succeed when all fields are defined" in {
      val testJson = Json.parse(
        """
          |{
          |  "line1": "1 Test Street",
          |  "line2": "Test Hill",
          |  "line3": "Testford",
          |  "line4": "Testshire",
          |  "postcode": "TE1 1ST",
          |  "country": "UK"
          |}
        """.stripMargin)

      val output = Address("1 Test Street", "Test Hill", Some("Testford"), Some("Testshire"), Some("TE1 1ST"), Some("UK"))

      Json.fromJson[Address](testJson) shouldBe JsSuccess(output)
    }

    "succeed when minimal fields are defined" ignore {
      val testJson = Json.parse(
        """
          |{
          |
          |}
        """.stripMargin)

      val output = Address("1 Test Street", "Testford", None, None, None, None)

      Json.fromJson[Address](testJson) shouldBe JsSuccess(output)
    }
  }



}
