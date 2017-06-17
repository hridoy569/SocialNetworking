/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.social.controller;

import com.social.entity.Post;
import com.social.entity.ProfilePhotoAlbum;
import com.social.entity.Users;
import com.social.service.PostServiceInterface;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author B3
 */
@Controller
@SessionAttributes("user-entity")
@Transactional
@Repository
public class PostCtrl {
    @Autowired
    private PostServiceInterface postServInt;
    @Autowired
    private SessionFactory sessionFactory;
    private Users user;
    
    @RequestMapping(value = "/homePostAdd", method = RequestMethod.POST)
    public String homePostAdd(@ModelAttribute("post") Post post, BindingResult result) {
        
            postServInt.addPost(new Post(post.getUserId(), post.getUserIdTo(),post.getPostTitle(),post.getPostContext(),"test",new Date(),"n/a"));
            System.out.println(post.getUserId()+""+post.getPostTitle()+""+post.getPostContext()+""+"test"+""+new Date()+""+"n/a");
        
        return "home";
    }
    
    @RequestMapping(value = "/profilePostAdd", method = RequestMethod.POST)
    public String profilePostAdd(@ModelAttribute("post") Post post, BindingResult result, HttpSession session) {
        
            postServInt.addPost(new Post(post.getUserId(), post.getUserIdTo(), post.getPostTitle(),post.getPostContext(),"test",new Date(),"n/a"));
            System.out.println(post.getUserId()+""+post.getPostTitle()+""+post.getPostContext()+""+"test"+""+new Date()+""+"n/a");
            
            // for user's posts
        Session session5 = sessionFactory.getCurrentSession();
        Query query5 = session5.createQuery("FROM Post p WHERE p.userIdTo=:userIdTo order by postTime desc");
        query5.setInteger("userIdTo", user.getUserId());

        List<Post> cList5 = query5.list();
        cList5.toString();
        for (Post p : cList5) {
            System.out.println("Login Controller: " + p.getUserId() + " " + p.getPostTitle());
        }
        session.setAttribute("pst", cList5);
        
        return "profile";
    }
    
    @RequestMapping(value = "/showProfile{userId}", method = RequestMethod.GET)
    public String profilePostAdd454(@PathVariable("userId") Integer userId, HttpSession session) {
        
            System.out.println("showProfile: "+userId);
            
        Session session1 = sessionFactory.getCurrentSession();
        Query query = session1.createQuery("FROM Users u WHERE u.userId=:userId");
        query.setInteger("userId", userId);

        List<Users> targetUser = query.list();
        targetUser.toString();
        for (Users p : targetUser) {
            System.out.println("ProfilePhotoAlbum: " + p.getFirstName()+ " " + p.getLastName());
        }
        session.setAttribute("targetUser", targetUser.get(0));        
        
        Session session2 = sessionFactory.getCurrentSession();
        Query query2 = session2.createQuery("FROM ProfilePhotoAlbum p WHERE p.userId=:userId and p.status=:status");
        query2.setInteger("userId", userId);
        query2.setInteger("status", 1);

        List<ProfilePhotoAlbum> targetUserPP = query2.list();
        targetUserPP.toString();
        for (ProfilePhotoAlbum p : targetUserPP) {
            System.out.println("ProfilePhotoAlbum: " + p.getFileLink()+ " " + p.getUserId());
        }
        session.setAttribute("targetUserPP", targetUserPP.get(0));
        
        Session session5 = sessionFactory.getCurrentSession();
        Query query5 = session5.createQuery("FROM Post p WHERE p.userId=:userId order by postTime desc");
        query5.setInteger("userId", userId);

        List<Post> posts = query5.list();
        posts.toString();
        for (Post p : posts) {
            System.out.println("Login Controller: " + p.getUserId() + " " + p.getPostTitle());
        }
        session.setAttribute("targetUserPosts", posts);
        
        return "showProfile";
    }
    @RequestMapping(value = "/showFriendProfile{userId}", method = RequestMethod.GET)
    public String profilePostAdd45(@PathVariable("userId") Integer userId, HttpSession session) {
        
            System.out.println("showProfile: "+userId);
            
        Session session1 = sessionFactory.getCurrentSession();
        Query query = session1.createQuery("FROM Users u WHERE u.userId=:userId");
        query.setInteger("userId", userId);

        List<Users> targetUser = query.list();
        targetUser.toString();
        for (Users p : targetUser) {
            System.out.println("ProfilePhotoAlbum: " + p.getFirstName()+ " " + p.getLastName());
        }
        session.setAttribute("targetUser", targetUser.get(0));        
        
        Session session2 = sessionFactory.getCurrentSession();
        Query query2 = session2.createQuery("FROM ProfilePhotoAlbum p WHERE p.userId=:userId and p.status=:status");
        query2.setInteger("userId", userId);
        query2.setInteger("status", 1);

        List<ProfilePhotoAlbum> targetUserPP = query2.list();
        targetUserPP.toString();
        for (ProfilePhotoAlbum p : targetUserPP) {
            System.out.println("ProfilePhotoAlbum: " + p.getFileLink()+ " " + p.getUserId());
        }
        session.setAttribute("targetUserPP", targetUserPP.get(0));
        
        Session session5 = sessionFactory.getCurrentSession();
        Query query5 = session5.createQuery("FROM Post p WHERE p.userId=:userId order by postTime desc");
        query5.setInteger("userId", userId);

        List<Post> posts = query5.list();
        posts.toString();
        for (Post p : posts) {
            System.out.println("Login Controller: " + p.getUserId() + " " + p.getPostTitle());
        }
        session.setAttribute("targetUserPosts", posts);
        
        return "showFriendProfile";
    }
}
