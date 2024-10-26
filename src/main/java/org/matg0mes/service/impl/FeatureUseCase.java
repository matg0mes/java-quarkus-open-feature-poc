package org.matg0mes.service.impl;

import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.FeatureProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.matg0mes.service.IFeatureUseCase;
import org.matg0mes.utils.FeatureFlagConfig;

import java.net.URISyntaxException;

@ApplicationScoped
public class FeatureUseCase implements IFeatureUseCase {
    private static final String FLAG_NAME_POTATO = "is-a-potato";

    @Inject
    FeatureFlagConfig featureFlagConfig;

    public String execute() {
        try {
            Client client = featureFlagConfig.getUnleashClient();
            Boolean featureEnabled = client.getBooleanValue(FLAG_NAME_POTATO, false);

            return "Potato is: " + featureEnabled;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}