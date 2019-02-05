package com.wol.mock.hplink.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wol.mock.hplink.model.User;
import com.wol.mock.hplink.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("list")
	public ModelAndView getProductList(HttpSession httpSession) {
		LOGGER.debug("Received getProductList request");	
		
		User activeUser = (User) httpSession.getAttribute("activeUser");
		
		if(activeUser == null || !User.State.LOGGED_IN.equals(activeUser.getState())) {
			LOGGER.warn("User is not currently logged in, redirecting to login.");
			return new ModelAndView("redirect:/login");
		}
		
		ModelAndView mav = new ModelAndView("product-list");
		mav.addObject("dashboard", httpSession.getAttribute("dashboard"));
		mav.addObject("products", productService.findAll());
		return mav;
	}
}
