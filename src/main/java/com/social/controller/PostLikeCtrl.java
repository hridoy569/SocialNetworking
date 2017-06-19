/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.social.controller;

import com.social.entity.PostLikes;
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
public class PostLikeCtrl {
     @Autowired
    private SessionFactory sessionFactory;
    private Integer id;
     
     @RequestMapping(value = "/homePostLike", method = RequestMethod.POST)
    public String homePostLike(@ModelAttribute("post") PostLikes pl, BindingResult result, HttpSession session) {

//        System.out.println("Post Likes: "+pl.getPostId()+" "+pl.getUserId()+" "+pl.getStatus());
        
        Session session1 = sessionFactory.getCurrentSession();
        Query query1 = session1.createQuery("FROM PostLikes p where p.postId=:postId and p.userId=:userId");
        
        query1.setInteger("postId", pl.getPostId());
        query1.setInteger("userId", pl.getUserId());
       
        List<PostLikes> status = query1.list();
        status.toString();
        for(PostLikes p: status){
            id = p.getPostLikesId();
        }
        
        if (status.size() > 0) {
        Session session3 = sessionFactory.getCurrentSession();
            Query query3 = session3.createQuery("UPDATE PostLikes p SET p.status=:one Where p.postLikesId=:postLikesId");
            query3.setInteger("one", 1);
            query3.setInteger("postLikesId", id);
            query3.executeUpdate();
        } else {
        PostLikes pl2 = new PostLikes(pl.getPostId(), pl.getUserId(), 1);
        sessionFactory.getCurrentSession().save(pl2);
        }

        Session session26 = sessionFactory.getCurrentSession();
        Query query26 = session26.createQuery("FROM PostLikes");
        
        List<PostLikes> status26 = query26.list();
        status26.toString();
        
        session.setAttribute("LikeStatus", status26);

        return "home";
    }
     @RequestMapping(value = "/homePostLikeUpdate", method = RequestMethod.POST)
    public String homePostLikeUpdate(@ModelAttribute("post") PostLikes pl, BindingResult result, HttpSession session) {

//        System.out.println("Post Likes: "+pl.getPostId()+" "+pl.getUserId()+" "+pl.getStatus());
        Session session1 = sessionFactory.getCurrentSession();
        Query query1 = session1.createQuery("FROM PostLikes p where p.postId=:postId and p.userId=:userId");
        
        query1.setInteger("postId", pl.getPostId());
        query1.setInteger("userId", pl.getUserId());
       
        List<PostLikes> status = query1.list();
        status.toString();
        for(PostLikes p: status){
            id = p.getPostLikesId();
        }
        
        Session session3 = sessionFactory.getCurrentSession();
            Query query3 = session3.createQuery("UPDATE PostLikes p SET p.status=:zero Where p.postLikesId=:postLikesId");
            query3.setInteger("zero", 0);
            query3.setInteger("postLikesId", id);
            query3.executeUpdate();
    

        Session session26 = sessionFactory.getCurrentSession();
        Query query26 = session26.createQuery("FROM PostLikes");
        
        List<PostLikes> status26 = query26.list();
        status26.toString();
        
        session.setAttribute("LikeStatus", status26);

        return "home";
    }
}
