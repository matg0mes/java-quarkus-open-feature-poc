package org.matg0mes.service;

import org.eclipse.microprofile.health.Liveness;

public interface IFeatureUseCase {

    String execute();

    String executeWithContext(String type);

}
