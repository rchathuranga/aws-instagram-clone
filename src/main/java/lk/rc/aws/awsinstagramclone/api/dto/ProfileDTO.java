package lk.rc.aws.awsinstagramclone.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.rc.aws.awsinstagramclone.model.Post;
import lombok.Data;

import java.sql.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private int profileId;
    private String fullName;
    private String firstName;
    private Integer age;
    private String lastName;
    private Date dateOfBirth;
    private String status;
    private String mobileNo;
    private String email;
    private PostDTO profilePicture;
}
