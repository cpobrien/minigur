package org.minigur.site;

import org.minigur.site.configuration.SessionConfiguration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class ConfigurationInitializer extends AbstractHttpSessionApplicationInitializer {
    public ConfigurationInitializer() {
        super(SessionConfiguration.class);
    }
}
