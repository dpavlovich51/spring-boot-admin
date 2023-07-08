package com.dpavlovich.configuration

import de.codecentric.boot.admin.server.config.AdminServerProperties
import io.netty.handler.codec.http.HttpMethod
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.util.*


@Configuration
@EnableWebSecurity
class WebSecurityConfig(private val adminServer: AdminServerProperties) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val successHandler = SavedRequestAwareAuthenticationSuccessHandler()
        successHandler.setTargetUrlParameter("redirectTo")
        successHandler.setDefaultTargetUrl(adminServer.contextPath + "/")

        http.authorizeHttpRequests {
                it.requestMatchers(adminServer.contextPath + "/login")
                    .permitAll()
                    .anyRequest().authenticated()
            }.formLogin {
                it.loginPage(adminServer.contextPath + "/login")
                    .successHandler(successHandler)
            }
            .logout {
                it.logoutUrl(adminServer.contextPath + "/logout")
            }
            .httpBasic { }
            .csrf {
                it.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers(
                        AntPathRequestMatcher(
                            adminServer.contextPath +
                                    "/instances", HttpMethod.POST.toString()
                        ),
                        AntPathRequestMatcher(
                            adminServer.contextPath +
                                    "/instances/*", HttpMethod.DELETE.toString()
                        ),
                        AntPathRequestMatcher(adminServer.contextPath + "/actuator/**")
                    )
            }
            .rememberMe {
                it.key(UUID.randomUUID().toString())
                    .tokenValiditySeconds(1209600)
            }

        return http.build()
    }
}