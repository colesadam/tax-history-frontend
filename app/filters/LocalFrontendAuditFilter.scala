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

package filters

import javax.inject._

import com.typesafe.config.Config
import config.FrontendAuditConnector
import net.ceedubs.ficus.Ficus._
import play.api.Configuration
import uk.gov.hmrc.play.audit.filters.FrontendAuditFilter
import uk.gov.hmrc.play.config.{AppName, ControllerConfig, RunMode}
import uk.gov.hmrc.play.filters.MicroserviceFilterSupport


@Singleton
class LocalFrontendAuditFilter @Inject()(
  val config: Configuration,
  val auditConnector: FrontendAuditConnector
) extends FrontendAuditFilter with MicroserviceFilterSupport with RunMode with AppName with ControllerConfig {

  lazy val controllerConfigs = config.underlying.as[Config]("controllers")

  override lazy val maskedFormFields = Seq("password")
  override lazy val applicationPort = None
  override def controllerNeedsAuditing(controllerName: String) = paramsForController(controllerName).needsAuditing
}
