package com.blog.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.application.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer>{

}
