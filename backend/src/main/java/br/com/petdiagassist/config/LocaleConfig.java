package br.com.petdiagassist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.server.i18n.LocaleContextResolver;

/**
 * Locale configuration class
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Configuration
public class LocaleConfig extends DelegatingWebFluxConfiguration {

    @Override
    protected LocaleContextResolver createLocaleContextResolver() {
        return new LocaleResolverConfig();
    }
}
