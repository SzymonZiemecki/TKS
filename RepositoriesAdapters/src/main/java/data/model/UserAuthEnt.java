package data.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthEnt {
    private String role;
    private String login;
    private UUID id;
}
