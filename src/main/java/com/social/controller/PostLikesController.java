/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.social.controller;

import com.social.service.PostLikesServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author B1
 */
public class PostLikesController {
    @Autowired
    private PostLikesServiceInterface postLikesServInt;
}
