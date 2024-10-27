package org.matg0mes.utils;

import dev.openfeature.contrib.providers.unleash.UnleashProvider;
import dev.openfeature.contrib.providers.unleash.UnleashProviderConfig;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.OpenFeatureAPI;
import io.getunleash.util.UnleashConfig;
import io.quarkus.runtime.Shutdown;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.UUID;


@Singleton
public class FeatureFlagConfig {
    @ConfigProperty(name = "open-feature.unleash.url")
    private String unleashUrl;

    @ConfigProperty(name = "open-feature.unleash.api-key")
    private String unleashApiKey;

    private OpenFeatureAPI openFeatureAPI;

    private Client unleashClient;

    public Client getUnleashClient() throws URISyntaxException {
        return OpenFeatureAPI.getInstance().getClient("sync");
    }

    @Startup
    void startup() {
        UnleashConfig.Builder unleashConfigBuilder =
                UnleashConfig.builder()
                        .appName("quarkus-example")
                        .apiKey(unleashApiKey)
                        .unleashAPI(unleashUrl)
                        .instanceId("quarkus-example-" + UUID.randomUUID())
                        .synchronousFetchOnInitialisation(true);

        UnleashProviderConfig unleashProviderConfig = UnleashProviderConfig.builder()
                .unleashConfigBuilder(unleashConfigBuilder)
                .build();

        OpenFeatureAPI.getInstance().setProviderAndWait("sync", new UnleashProvider(unleashProviderConfig));
    }

    @Shutdown
    void shutdown() {
        OpenFeatureAPI.getInstance().shutdown();
    }

}
