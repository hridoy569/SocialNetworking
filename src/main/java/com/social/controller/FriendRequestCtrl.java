/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.social.controller;

import com.social.entity.FriendRequest;
import com.social.entity.Users;
import com.social.service.FriendRequestServiceInterface;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
public class FriendRequestCtrl {
    @Autowired
    private FriendRequestServiceInterface frsi;
    @Autowired
    private SessionFactory sessionFactory;
    
    List<FriendRequest> totalSentToList;
    
    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    public String add(@ModelAttribute("fr") FriendRequest fr, BindingResult result, HttpSession session) {
        totalSentToList = frsi.add(fr);
//        System.out.println("Ctrl: Friend request sent to "+totalSentToList.size()+" persons");
//            System.out.println("Ctrl: "+fr.getUserId()+" "+fr.getUserIdTo()+" "+fr.getStatus());
        session.removeAttribute("requestSent");
        session.setAttribute("requestSent", totalSentToList);
        return "people_directory";
    }
    
    @RequestMapping(value = "/cancelRequest", method = RequestMethod.POST)
    public String updateCancel(@ModelAttribute("fr") FriendRequest fr, BindingResult result, HttpSession session) {
        totalSentToList = frsi.update(fr);
//        System.out.println("Ctrl: "+fr.getFriendRequstId()+" "+fr.getUserId()+" "+fr.getUserIdTo()+" "+fr.getStatus());
        session.removeAttribute("requestSent");
        session.setAttribute("requestSent", totalSentToList);
        return "people_directory";
    }
    
    @RequestMapping(value = "/acceptRequest", method = RequestMethod.POST)
    public String updateAccept(@ModelAttribute("fr") FriendRequest fr, BindingResult result, HttpSession session) {
//        System.out.println("acceptRequest: "+fr.getFriendRequstId()+" "+fr.getUserId()+" "+fr.getUserIdTo()+" "+fr.getStatus());
        List<Users> auList = frsi.accept(fr);
        session.setAttribute("auList", auList);
        
        //get friend requests
        Session session7 = sessionFactory.getCurrentSession();
        Query query7 = session7.createQuery("from  Users  where userId in (select userId from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc)");

        query7.setInteger("userIdTo", fr.getUserIdTo());
        query7.setInteger("status", 1);

        List<Users> cList7 = query7.list();
        cList7.toString();
        session.setAttribute("getRequests", cList7);
        
         //get friends
        Session session8 = sessionFactory.getCurrentSession();
        Query query8 = session8.createQuery("from  Users  where userId in (select userId from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc)");

        query8.setInteger("userIdTo", fr.getUserIdTo());
        query8.setInteger("status", 2);

        List<Users> cList8 = query8.list();
        cList8.toString();
        
        
        //get friends2
        Session session82 = sessionFactory.getCurrentSession();
        Query query82 = session82.createQuery("from  Users  where userId in (select userIdTo from FriendRequest where userId=:userId and status=:status order by friendRequstId desc)");

        query82.setInteger("userId", fr.getUserIdTo());
        query82.setInteger("status", 2);

        List<Users> cList82 = query82.list();
        cList82.toString();
        
        List<Users> newList = Stream.concat(cList8.stream(), cList82.stream()).collect(Collectors.toList());
        
        session.setAttribute("getFriends", newList);
        
        //get friend id
        Session session10 = sessionFactory.getCurrentSession();
        Query query10 = session10.createQuery("from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc");

        query10.setInteger("userIdTo", fr.getUserIdTo());
        query10.setInteger("status", 2);

        List<FriendRequest> cList10 = query10.list();
        cList10.toString();
        
        Session session102 = sessionFactory.getCurrentSession();
        Query query102 = session102.createQuery("from FriendRequest where userId=:userId and status=:status order by friendRequstId desc");

        query102.setInteger("userId", fr.getUserIdTo());
        query102.setInteger("status", 2);

        List<FriendRequest> cList102 = query102.list();
        cList102.toString();
        List<FriendRequest> newList2 = Stream.concat(cList10.stream(), cList102.stream()).collect(Collectors.toList());
        
        session.setAttribute("getFriendsId", newList2);
        return "people_directory";
    }
    
    @RequestMapping(value = "/acceptRequestHome", method = RequestMethod.POST)
    public String updateAcceptHome(@ModelAttribute("fr") FriendRequest fr, BindingResult result, HttpSession session) {
//        System.out.println("acceptRequest: "+fr.getFriendRequstId()+" "+fr.getUserId()+" "+fr.getUserIdTo()+" "+fr.getStatus());
        List<Users> auList = frsi.accept(fr);
        session.setAttribute("auList", auList);
        
        //get friend requests
        Session session7 = sessionFactory.getCurrentSession();
        Query query7 = session7.createQuery("from  Users  where userId in (select userId from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc)");

        query7.setInteger("userIdTo", fr.getUserIdTo());
        query7.setInteger("status", 1);

        List<Users> cList7 = query7.list();
        cList7.toString();
        session.setAttribute("getRequests", cList7);
        
        //get friends
        Session session8 = sessionFactory.getCurrentSession();
        Query query8 = session8.createQuery("from  Users  where userId in (select userId from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc)");

        query8.setInteger("userIdTo", fr.getUserIdTo());
        query8.setInteger("status", 2);

        List<Users> cList8 = query8.list();
        cList8.toString();
        
        
        //get friends2
        Session session82 = sessionFactory.getCurrentSession();
        Query query82 = session82.createQuery("from  Users  where userId in (select userIdTo from FriendRequest where userId=:userId and status=:status order by friendRequstId desc)");

        query82.setInteger("userId", fr.getUserIdTo());
        query82.setInteger("status", 2);

        List<Users> cList82 = query82.list();
        cList82.toString();
        
        List<Users> newList = Stream.concat(cList8.stream(), cList82.stream()).collect(Collectors.toList());
        
        session.setAttribute("getFriends", newList);
        
        //get friend id
        Session session10 = sessionFactory.getCurrentSession();
        Query query10 = session10.createQuery("from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc");

        query10.setInteger("userIdTo", fr.getUserIdTo());
        query10.setInteger("status", 2);

        List<FriendRequest> cList10 = query10.list();
        cList10.toString();
        
        Session session102 = sessionFactory.getCurrentSession();
        Query query102 = session102.createQuery("from FriendRequest where userId=:userId and status=:status order by friendRequstId desc");

        query102.setInteger("userId", fr.getUserIdTo());
        query102.setInteger("status", 2);

        List<FriendRequest> cList102 = query102.list();
        cList102.toString();
        List<FriendRequest> newList2 = Stream.concat(cList10.stream(), cList102.stream()).collect(Collectors.toList());
        
        session.setAttribute("getFriendsId", newList2);
        
        
        return "home";
    }
    
    @RequestMapping(value = "/rejectRequest", method = RequestMethod.POST)
    public String updateReject(@ModelAttribute("fr") FriendRequest fr, BindingResult result, HttpSession session) {
//        System.out.println("acceptRequest: "+fr.getFriendRequstId()+" "+fr.getUserId()+" "+fr.getUserIdTo()+" "+fr.getStatus());
        List<Users> getRequests = frsi.reject(fr);
        session.setAttribute("getRequests", getRequests);
        return "people_directory";
    }
    
     @RequestMapping(value = "/rejectRequestHome", method = RequestMethod.POST)
    public String updateRejectHome(@ModelAttribute("fr") FriendRequest fr, BindingResult result, HttpSession session) {
//        System.out.println("acceptRequest: "+fr.getFriendRequstId()+" "+fr.getUserId()+" "+fr.getUserIdTo()+" "+fr.getStatus());
        List<Users> getRequests = frsi.reject(fr);
        session.setAttribute("getRequests", getRequests);
        return "home";
    }
    
    @RequestMapping(value = "/unfriend", method = RequestMethod.POST)
    public String unfriend(@ModelAttribute("fr") FriendRequest fr, BindingResult result, HttpSession session) {
        frsi.unfriend(fr);
//        System.out.println("unfriend: "+fr.getFriendRequstId()+" "+fr.getUserId()+" "+fr.getUserIdTo()+" "+fr.getStatus());

        //get friends
        Session session8 = sessionFactory.getCurrentSession();
        Query query8 = session8.createQuery("from  Users  where userId in (select userId from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc)");

        query8.setInteger("userIdTo", fr.getUserIdTo());
        query8.setInteger("status", 2);

        List<Users> cList8 = query8.list();
        cList8.toString();
        
        
        //get friends2
        Session session82 = sessionFactory.getCurrentSession();
        Query query82 = session82.createQuery("from  Users  where userId in (select userIdTo from FriendRequest where userId=:userId and status=:status order by friendRequstId desc)");

        query82.setInteger("userId", fr.getUserIdTo());
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
        return "friends";
    }
    @RequestMapping(value = "/unfriend2", method = RequestMethod.POST)
    public String unfriend2(@ModelAttribute("fr") FriendRequest fr, BindingResult result, HttpSession session) {
        frsi.unfriend(fr);
//        System.out.println("unfriend: "+fr.getFriendRequstId()+" "+fr.getUserId()+" "+fr.getUserIdTo()+" "+fr.getStatus());

        //get friends
        Session session8 = sessionFactory.getCurrentSession();
        Query query8 = session8.createQuery("from  Users  where userId in (select userId from FriendRequest where userIdTo=:userIdTo and status=:status order by friendRequstId desc)");

        query8.setInteger("userIdTo", fr.getUserId());
        query8.setInteger("status", 2);

        List<Users> cList8 = query8.list();
        cList8.toString();
        
        
        //get friends2
        Session session82 = sessionFactory.getCurrentSession();
        Query query82 = session82.createQuery("from  Users  where userId in (select userIdTo from FriendRequest where userId=:userId and status=:status order by friendRequstId desc)");

        query82.setInteger("userId", fr.getUserId());
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
        return "friends";
    }
}
