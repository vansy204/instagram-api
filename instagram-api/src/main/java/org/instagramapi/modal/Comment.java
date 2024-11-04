package org.instagramapi.modal;

import jakarta.persistence.*;
import lombok.Data;
import org.instagramapi.dto.UserDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id",column = @Column(name = "user_id")),
            @AttributeOverride(name ="email",column = @Column(name = "user_email"))
    })
    private UserDto user;
    private String content;
    @ElementCollection
    @Embedded
    private Set<UserDto> likeByUser = new HashSet<UserDto>( );
    private LocalDateTime createdAt;

}
