package org.instagramapi.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto {
    private int id;
    private String username;
    private String email;
    private String name;
    private String userImage;
}
