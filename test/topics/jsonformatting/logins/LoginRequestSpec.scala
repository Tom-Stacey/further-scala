package topics.jsonformatting.logins

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json._
import LoginRequest.apiReads

class LoginRequestSpec extends WordSpec with Matchers {

  "LoginRequest" should {
    "successfully read from json" when {

      "username and password are defined" in {
        val testJson = Json.parse(
          """
            |{
            |  "username": "testUsername",
            |  "password": "testPassword"
            |}
          """.stripMargin)

        val output = PlainTextLoginRequest("testUsername", "testPassword")
        Json.fromJson[LoginRequest](testJson)(apiReads) shouldBe JsSuccess(output)
      }

      "username and hashed password are defined" in {
        val testJson = Json.parse(
          """
            |{
            |  "username": "testUsername",
            |  "hashedPassword": "testHashedPassword"
            |}
          """.stripMargin)

        val output = HashedPasswordLoginRequest("testUsername", "testHashedPassword")
        Json.fromJson[LoginRequest](testJson)(apiReads) shouldBe JsSuccess(output)
      }
    }
  }

  "fail to read from json" when {

    "there is no username" in {
      val testJson = Json.parse(
        """
          |{
          |  "password": "testPassword"
          |}
        """.stripMargin)

      Json.fromJson[LoginRequest](testJson)(apiReads) shouldBe JsError("Username must be supplied")
    }

    "there is no password" in {
      val testJson = Json.parse(
        """
          |{
          |  "username": "testUsername"
          |}
        """.stripMargin)

      Json.fromJson[LoginRequest](testJson)(apiReads) shouldBe JsError("Either password or hashed password must be present in request")
    }

    "there are both passwords" in {
      val testJson = Json.parse(
        """
          |{
          |  "username": "testUsername",
          |  "password": "testPassword",
          |  "hashedPassword": "testHashedPassword"
          |}
        """.stripMargin)

      Json.fromJson[LoginRequest](testJson)(apiReads) shouldBe JsError("Both password and hashed password are present in request")
    }

    "Json provided can't be parsed to an object" in {
      val testJson = Json.parse(""" "notAnObject" """)

      Json.fromJson[LoginRequest](testJson)(apiReads) shouldBe JsError("error.expected.jsobject")
    }

    "username can't be parsed to a String" in {
      val testJson = Json.parse(
        """
          |{
          |  "username": { "innerUsername": "testUsername" },
          |  "password": "testPassword"
          |}
        """.stripMargin)

      Json.fromJson[LoginRequest](testJson)(apiReads) shouldBe JsError("error.expected.jsstring")
    }
  }

}
