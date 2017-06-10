/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.social.controller;

import com.social.entity.Post;
import com.social.service.PostServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author B3
 */
@Controller
public class PostCtrl {
    @Autowired
    private PostServiceInterface postServInt;
    
    @RequestMapping(value = "/post/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("post") Post post, BindingResult result) {
        if (post.getPostId() == null) {
            postServInt.addPost(post);
        } else {
            postServInt.updatePost(post);
        }
        return "redirect:/";
    }
}
