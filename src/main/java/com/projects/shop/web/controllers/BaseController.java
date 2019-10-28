package com.projects.shop.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    public ModelAndView view(String viewName, ModelAndView model) {
        model.setViewName(viewName);
        return model;
    }

    public ModelAndView view(String viewName) {
        return view(viewName, new ModelAndView());
    }

    public ModelAndView redirect(String url) {
        return view("redirect" + url);
    }
}
