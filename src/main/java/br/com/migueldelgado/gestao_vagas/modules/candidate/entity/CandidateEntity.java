package br.com.migueldelgado.gestao_vagas.modules.candidate.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.rmi.server.UID;

@Data
public class CandidateEntity {

    private UID id;
    private String name;

    @Pattern(regexp = "^(?!\\s*$).+", message = "O campo [username] não deve conter espaço. ")
    private String username;

    @Email(message = "O campo [email] deve conter um email válido.")
    private String email;

    @Length(min = 10, max = 100)
    private String password;
    private String description;
    private String curriculum;

}
