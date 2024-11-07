package org.instagramapi.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.instagramapi.dto.UserDto;

import java.time.LocalDateTime;

@Entity
@Table(name = "stories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Embedded
//    @Convert(converter = GenericMapper.class)
    @AttributeOverrides({
            @AttributeOverride(name = "id",column = @Column(name = "user_id")),
            @AttributeOverride(name ="email",column = @Column(name = "user_email"))
    })
    private UserDto user;
    @NotNull
    private String image;
    private String caption;
    private LocalDateTime timestamp;


}
