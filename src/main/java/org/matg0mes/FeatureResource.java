package org.matg0mes;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.matg0mes.service.IFeatureUseCase;

@Path("/hello")
public class FeatureResource {

    @Inject
    IFeatureUseCase iFeatureUseCase;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return iFeatureUseCase.execute();
    }

}
