/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.social.controller;

import com.social.entity.Comment;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author Hridoy
 */
@Controller
@SessionAttributes("user-entity")
@Transactional
@Repository
public class CommentCtrl {
    @Autowired
    private SessionFactory sessionFactory;
    
    @RequestMapping(value = "/homeCommentAdd", method = RequestMethod.POST)
    public String homeCommentAdd(@ModelAttribute("post") Comment com, BindingResult result, HttpSession session) {

//        System.out.println("Comment: "+com.getPostId()+" "+com.getUserId()+" "+com.getCommentContent());
        Comment com2 = new Comment(com.getPostId(), com.getUserId(), new Date(), com.getCommentContent(), 0);
        
        sessionFactory.getCurrentSession().save(com2);
        
        // get comments
        Session session61 = sessionFactory.getCurrentSession();
        Query query61 = session61.createQuery("FROM Comment");
       
        List<Comment> commentsList = query61.list();
        commentsList.toString();

        session.setAttribute("commentsList", commentsList);

        return "home";
    }
}
