package org.instagramapi.modal;

import jakarta.persistence.*;
import lombok.Data;

import org.instagramapi.dto.UserDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String caption;
    private String image;
    private String location;
    private LocalDateTime createdAt;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id",column = @Column(name = "user_id")),
            @AttributeOverride(name ="email",column = @Column(name = "user_email"))
    })
    private UserDto user;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();
    @Embedded
    @ElementCollection
    @JoinTable(name = "likedByUsers", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserDto> likedByUsers = new HashSet<>();
}