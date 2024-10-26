package org.matg0mes.utils;

import dev.openfeature.contrib.providers.unleash.UnleashProvider;
import dev.openfeature.contrib.providers.unleash.UnleashProviderConfig;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.FeatureProvider;
import dev.openfeature.sdk.OpenFeatureAPI;
import io.getunleash.util.UnleashConfig;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URISyntaxException;
import java.util.Objects;

@ApplicationScoped
public class FeatureFlagConfig {
    @ConfigProperty(name = "open-feature.unleash.url")
    private String unleashUrl;

    @ConfigProperty(name = "open-feature.unleash.api-key")
    private String unleashApiKey;

    private OpenFeatureAPI openFeatureAPI;

    private Client unleashClient;

    public Client getUnleashClient() throws URISyntaxException {
        if (Objects.nonNull(unleashClient)) {
            return unleashClient;
        }

        UnleashConfig.Builder unleashConfigBuilder =
                UnleashConfig.builder()
                        .appName("quarkus-example")
                        .instanceId("quarkus-example")
                        .apiKey(unleashApiKey)
                        .unleashAPI(unleashUrl)
                        .synchronousFetchOnInitialisation(true);

        UnleashProviderConfig unleashProviderConfig = UnleashProviderConfig.builder()
                .unleashConfigBuilder(unleashConfigBuilder)
                .build();

        OpenFeatureAPI.getInstance().setProviderAndWait("sync", new UnleashProvider(unleashProviderConfig));
        unleashClient = OpenFeatureAPI.getInstance().getClient("sync");
        return unleashClient;
    }

}
