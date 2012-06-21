package vggames.shared.auth

import org.scribe.builder.api.TwitterApi
import org.scribe.builder.ServiceBuilder
import org.scribe.model.Token
import org.scribe.model.Verifier
import org.scribe.oauth.OAuthService
import br.com.caelum.vraptor.ioc.ApplicationScoped
import br.com.caelum.vraptor.ioc.Component
import vggames.shared.vraptor.OAuthSecrets

object OAuthServiceBuilder {
  val apis = Map("twitter" -> classOf[TwitterApi])

  def apply(provider : AuthProvider, secrets : OAuthSecrets) = {
    val authService = new ServiceBuilder().provider(apis(provider.name)).apiKey(secrets.apiKeyFor(provider.name))
      .apiSecret(secrets.apiSecretFor(provider.name)).callback("http://games.vidageek.net/authorization").build
    new OAuthServiceBuilder(authService)
  }
}

class OAuthServiceBuilder(val authService : OAuthService) {
  val requestToken = authService.getRequestToken
  val autorizationUrl = authService.getAuthorizationUrl(requestToken)

  def accessToken(verifier : Verifier) : Token = {
    authService.getAccessToken(requestToken, verifier)
  }
}