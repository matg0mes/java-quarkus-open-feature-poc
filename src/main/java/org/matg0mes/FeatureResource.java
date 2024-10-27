package org.matg0mes;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.matg0mes.service.IFeatureUseCase;

@Path("/potato")
public class FeatureResource {

    @Inject
    IFeatureUseCase iFeatureUseCase;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String potato() {
        return iFeatureUseCase.execute();
    }

    @GET
    @Path("/with-context")
    @Produces(MediaType.TEXT_PLAIN)
    public String potatoWithContext(@QueryParam(value = "type") String type) {
        return iFeatureUseCase.executeWithContext(type);
    }

}
