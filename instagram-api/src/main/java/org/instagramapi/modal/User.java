package org.instagramapi.modal;

import jakarta.persistence.*;
import lombok.*;
import org.instagramapi.dto.UserDto;

import java.util.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String name;
    private String email;
    private String mobile;
    private String website;
    private String bio;
    private String gender;
    private String image;
    private String password;
    @Embedded
    @ElementCollection
    private Set<UserDto> follower = new HashSet<UserDto>();
    @Embedded
    @ElementCollection
    private Set<UserDto> following = new HashSet<UserDto>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Story> stories = new ArrayList<>();
    @ManyToMany
    private List<Post> savedPost = new ArrayList<>();

}
