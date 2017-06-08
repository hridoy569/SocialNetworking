/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.social.dao;

import com.social.entity.Post;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author B3
 */
public class PostDao implements PostDaoInterface{
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addPost(Post post) {
    }

    @Override
    public void updatePost(Post post) {
    }

    @Override
    public List<Post> listPost() {
        return null;
    }

    @Override
    public Post getPostById(Integer Id) {
        return null;
    }

    @Override
    public void removePost(Integer Id) {
    }

    
}
