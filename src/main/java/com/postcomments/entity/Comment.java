package com.postcomments.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comments")
public class Comment {

    @Id
    @Column
    private String id;

    @Column
    private String postId;

    @Column
    private String content;

    @Column
    private String author;

}
