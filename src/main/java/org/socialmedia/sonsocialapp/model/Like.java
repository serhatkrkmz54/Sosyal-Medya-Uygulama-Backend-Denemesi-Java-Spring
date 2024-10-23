package org.socialmedia.sonsocialapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

}
