package com.codingchallenge.coinrate.rategateway.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for handling login functionality.
 */
@Controller
public class LoginController {

    /**
     * Handles the request to "/login" URL and returns the "login" view.
     *
     * @return The name of the view to render, in this case, "login".
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
