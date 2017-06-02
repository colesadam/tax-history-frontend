/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import org.joda.time.{DateTime, DateTimeZone, Duration}
import play.api.libs.json.Json
import play.api.mvc._
import uk.gov.hmrc.play.frontend.controller.FrontendController

import scala.concurrent.Future


class MainController extends FrontendController {

  val index = Action.async { implicit request =>

    request.session.data.get("ts") match {
      case Some(ts) =>
        val x = (DateTime.now(DateTimeZone.UTC).getMillis - ts.toLong) / 1000
        val secondsUntil15MinTimeout = Duration.standardMinutes(15).toStandardSeconds.getSeconds - x
        Future.successful(Ok(Json.obj(
          "secondsUntil15MinTimeout" -> secondsUntil15MinTimeout,
          "showWarning" -> (secondsUntil15MinTimeout < 60),
          "sessionExpired" -> (secondsUntil15MinTimeout <= 0)
        )))
      case None =>
        Future.successful(Gone)
    }




  }
}
