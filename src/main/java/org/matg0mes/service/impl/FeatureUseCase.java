package org.matg0mes.service.impl;

import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.FeatureProvider;
import dev.openfeature.sdk.MutableContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.matg0mes.service.IFeatureUseCase;
import org.matg0mes.utils.FeatureFlagConfig;

import java.net.URISyntaxException;

@ApplicationScoped
public class FeatureUseCase implements IFeatureUseCase {
    private static final String FLAG_NAME_POTATO = "is-a-potato";
    private static final String FLAG_NAME_POTATO_WITH_CONTEXT = "is-a-potato-with-context";
    @Inject
    FeatureFlagConfig featureFlagConfig;

    @Override
    public String execute() {
        try {
            Client client = featureFlagConfig.getUnleashClient();
            Boolean featureEnabled = client.getBooleanValue(FLAG_NAME_POTATO, false);

            return "Potato is: " + featureEnabled;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String executeWithContext(String type) {
        try {
            Client client = featureFlagConfig.getUnleashClient();

            MutableContext mutableContext = new MutableContext();
            mutableContext.add("potato-type", type);

            Boolean featureEnabled = client.getBooleanValue(FLAG_NAME_POTATO_WITH_CONTEXT, false, mutableContext);
            return "Potato with context is: " + featureEnabled;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


}