package com.tks.api;

import com.tks.dto.LoginDTO;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Remote;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth/login")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Remote
public interface AuthRestApi {

    @POST
    @PermitAll
    Response authenticate(@NotNull LoginDTO loginDTO);
}
