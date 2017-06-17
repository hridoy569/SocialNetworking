/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.social.controller;

import com.social.entity.CoverPhotoAlbum;
import com.social.entity.FriendRequest;
import com.social.entity.Post;
import com.social.entity.ProfilePhotoAlbum;
import com.social.entity.Users;
import com.social.service.IUsersService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Hridoy
 */
@Controller
@SessionAttributes("user-entity")
@Transactional
@Repository
public class LoginController {

    @Autowired
    private IUsersService userService;
    @Autowired
    private SessionFactory sessionFactory;
    

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("user", new Users());
        return mav;
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public ModelAndView loginProcess(WebRequest webRequest,
            @ModelAttribute("user") Users user, HttpSession session) {

        user = userService.loginUsers(user);
        session.setAttribute("u", user);

        // for profile photo
        Session session2 = sessionFactory.getCurrentSession();
        Query query2 = session2.createQuery("FROM ProfilePhotoAlbum p WHERE p.userId=:userId and p.status=:status");

        query2.setInteger("userId", user.getUserId());
        query2.setInteger("status", 1);

        List<ProfilePhotoAlbum> cList2 = query2.list();
        cList2.toString();
        for (ProfilePhotoAlbum p : cList2) {
            System.out.println("Login Controller: " + p.getProfilePhotoId() + " " + p.getUserId() + " " + p.getFileLink() + " " + p.getStatus());
        }
        ProfilePhotoAlbum ppa = cList2.get(0);
        session.setAttribute("ppa", ppa);
        

        // for all users picture
        Session session4 = sessionFactory.getCurrentSession();
        Query query4 = session4.createQuery("FROM ProfilePhotoAlbum p WHERE p.status=:status");
        query4.setInteger("status", 1);

        List<ProfilePhotoAlbum> cList4 = query4.list();
        cList4.toString();
        for (ProfilePhotoAlbum ppa2 : cList4) {
            System.out.println("All Users Photo: " + ppa2.getUserId() + " " + ppa2.getFileLink());
        }
        session.setAttribute("ppaList", cList4);
        
        // for all Cover pictures
        Session session41 = sessionFactory.getCurrentSession();
        Query query41 = session41.createQuery("FROM CoverPhotoAlbum");

        List<CoverPhotoAlbum> cList41 = query41.list();
        cList41.toString();
        for (CoverPhotoAlbum ppa2 : cList41) {
            System.out.println("All Cover Photo: " + ppa2.getUserId()+ " " + ppa2.getFileLink());
        }
        
        session.setAttribute("coverList", cList41);

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

        // for requested friend
        Session session6 = sessionFactory.getCurrentSession();
        Query query6 = session6.createQuery("FROM FriendRequest u WHERE u.userId=:userId and u.status=:status");

        query6.setInteger("userId", user.getUserId());
        query6.setInteger("status", 1);

        List<FriendRequest> totalSentToList = query6.list();
        totalSentToList.toString();

        session.setAttribute("requestSent", totalSentToList);

        //get friend requests
        Session session7 = sessionFactory.getCurrentSession();
        Query query7 = session7.createQuery("from  Users  where userId in (select userId from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc)");

        query7.setInteger("userIdTo", user.getUserId());
        query7.setInteger("status", 1);

        List<Users> cList7 = query7.list();
        cList7.toString();
        for (Users f : cList7) {
            System.out.println("in " + f.getUserId()+" "+f.getFirstName()+" "+f.getLastName());
          
            
        }
        session.setAttribute("getRequests", cList7);
        
        //get friend requests id
        Session session9 = sessionFactory.getCurrentSession();
        Query query9 = session9.createQuery("from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc");

        query9.setInteger("userIdTo", user.getUserId());
        query9.setInteger("status", 1);

        List<FriendRequest> cList9 = query9.list();
        cList9.toString();
        
        session.setAttribute("getRequestsId", cList9);
        
        //get friends
        Session session8 = sessionFactory.getCurrentSession();
        Query query8 = session8.createQuery("from  Users  where userId in (select userId from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc)");

        query8.setInteger("userIdTo", user.getUserId());
        query8.setInteger("status", 2);

        List<Users> cList8 = query8.list();
        cList8.toString();
        
        
        //get friends2
        Session session82 = sessionFactory.getCurrentSession();
        Query query82 = session82.createQuery("from  Users  where userId in (select userIdTo from FriendRequest where userId=:userId and status=:status order by friendRequstId desc)");

        query82.setInteger("userId", user.getUserId());
        query82.setInteger("status", 2);

        List<Users> cList82 = query82.list();
        cList82.toString();
        
        List<Users> newList = Stream.concat(cList8.stream(), cList82.stream()).collect(Collectors.toList());
        
        session.setAttribute("getFriends", newList);
        
        // for all users
        Session session3 = sessionFactory.getCurrentSession();
        Query query3 = session3.createQuery("from  Users");

        List<Users> cList3 = query3.list();
        cList3.toString();
        cList3.removeAll(newList);
        for (Users f : cList3) {
            System.out.println("Not friend yet " + f.getUserId()+" "+f.getFirstName()+" "+f.getLastName());
        }
        session.setAttribute("auList", cList3);
        
        // for all users
        Session session32 = sessionFactory.getCurrentSession();
        Query query32 = session32.createQuery("from  Users");

        List<Users> cList32 = query32.list();
        cList32.toString();
        session.setAttribute("allUsers", cList32);
        

        //get friend id
        Session session10 = sessionFactory.getCurrentSession();
        Query query10 = session10.createQuery("from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc");

        query10.setInteger("userIdTo", user.getUserId());
        query10.setInteger("status", 2);

        List<FriendRequest> cList10 = query10.list();
        cList10.toString();
        
        Session session102 = sessionFactory.getCurrentSession();
        Query query102 = session102.createQuery("from FriendRequest where userId=:userId and status=:status order by friendRequstId desc");

        query102.setInteger("userId", user.getUserId());
        query102.setInteger("status", 2);

        List<FriendRequest> cList102 = query102.list();
        cList102.toString();
        List<FriendRequest> newList2 = Stream.concat(cList10.stream(), cList102.stream()).collect(Collectors.toList());
        
        session.setAttribute("getFriendsId", newList2);
        
        //get friends posts
        Session session83 = sessionFactory.getCurrentSession();
        Query query83 = session83.createQuery("from  Post  where userId in (select userId from FriendRequest where userIdTo=:userIdTo and status=:status) order by postTime desc");

        query83.setInteger("userIdTo", user.getUserId());
        query83.setInteger("status", 2);

        List<Post> cList83 = query83.list();
        cList83.toString();
        
        
        //get friends2 posts
        Session session823 = sessionFactory.getCurrentSession();
        Query query823 = session823.createQuery("from  Post  where userId in (select userIdTo from FriendRequest where userId=:userId and status=:status) order by postTime desc");

        query823.setInteger("userId", user.getUserId());
        query823.setInteger("status", 2);

        List<Post> cList823 = query823.list();
        cList823.toString();
        
        List<Post> newList3 = Stream.concat(cList83.stream(), cList823.stream()).collect(Collectors.toList());
        
        List<Post> homePosts = Stream.concat(cList5.stream(), newList3.stream()).collect(Collectors.toList());
        
        session.setAttribute("homePosts", homePosts);

        return new ModelAndView("home", "user-entity", user);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session, HttpServletResponse response) {
        session.removeAttribute("requestSent");
        session.removeAttribute("u");
        session.invalidate();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setDateHeader("Expires", 0);
        System.out.println("session.invalidate()");
        return "redirect:/";
    }
}
