package com.pas.controller.User;

import com.pas.controller.Conversational;
import com.pas.model.User.User;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@ViewScoped
@Named
@Getter
@Setter
public class EditProfileController implements Serializable {

    UserDTO currentUser;

    @Inject
    UserApiClient userApiClient;

    @PostConstruct()
    public void init(){
        currentUser = userApiClient.findOneByLogin(context().getUserPrincipal().getName()).get(0);
    }

    public static ExternalContext context(){
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public String update() {
        userApiClient.updateUser(currentUser.getId(), currentUser);
        return "Start";
    }
}
