package com.wol.mock.hplink.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wol.mock.hplink.Utils;
import com.wol.mock.hplink.model.User;
import com.wol.mock.hplink.service.UserService;

@Controller
@RequestMapping("/account")
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	public LoginController() {}
	
	@GetMapping("/login")
	public String getLogin(ModelMap map) {
		LOGGER.debug("Received getLogin request");	
		map.put("loginForm", new LoginForm());
		return "pages/account/login";
	}
	
	@PostMapping("/doLogin")
	public String doLogin(@Valid @ModelAttribute("loginForm")LoginForm form, 
			BindingResult bindingResult, HttpSession httpSession) {
		LOGGER.debug("Received doLogin request");
		
		validateLogin(form, bindingResult);
		
		if(bindingResult.hasErrors()) {
			return "pages/account/login";
		}
		
		User user = null;
			
		try {
			user = userService.findByEmail(form.email);
		}
		catch(Exception e) {
			LOGGER.error("Failed to load user account: " + e.getMessage());
		}
			
		if(user == null) {
			bindingResult.addError(new ObjectError("error", 
					"User with email [" + form.email  + "] is unknown. "
							+ "Please register for an account."));
			return "pages/account/login";
		}
		else {
			LOGGER.info("Attempted login from username: " + form.email);
			
			if(form.password.equals(Utils.Crypto.decrypt(user.getPassword()))) {
				userService.login(user);	
				httpSession.setAttribute("activeUser", user);
			}
			else {
				bindingResult.addError(new ObjectError("error", 
						"Passwords did not match our records"));
				return "pages/account/login";
			}
		}
			
		return "redirect:/dashboard";	
	}
	
	@PostMapping("doLogout")
	public String doLogout(HttpSession httpSession) {
		LOGGER.debug("Received doLogout request");
		User activeUser = (User) httpSession.getAttribute("activeUser");
		userService.logout(activeUser);
		httpSession.removeAttribute("activeUser");
		return "redirect:/login";
	}
	
	@GetMapping("/register")
	public String getRegistration(ModelMap modelMap) {
		LOGGER.debug("Received getRegistration request");
		modelMap.put("registrationForm", new RegistrationForm());
		return "pages/account/register";
	}
	
	@PostMapping("/doRegister")
	public String doRegistration(@Valid @ModelAttribute("registrationForm") RegistrationForm form, 
			BindingResult bindingResult) {
		LOGGER.debug("Received doRegistration request");
		
		vaidateRegistration(form, bindingResult);
		
		if(bindingResult.hasErrors()) {
			return "pages/account/register";
		}
		
		User user = createUser(form);
		userService.save(user);
		LOGGER.info("Created " + user);
		return "pages/account/login";
	}
	
	@GetMapping("/reset/password")
	public String getPasswordReset(ModelMap modelMap) {
		LOGGER.debug("Received getPasswordReset request");
		modelMap.put("resetPasswordForm", new ResetPasswordForm());
		return "pages/account/reset-password";
	}
	
	@PostMapping("/doPasswordReset")
	public String doPasswordReset(@Valid @ModelAttribute("resetPasswordForm") ResetPasswordForm form, 
			BindingResult bindingResult, HttpServletRequest request) {
		LOGGER.debug("Received doPasswordReset request");
		
		if(StringUtils.isBlank(form.email)) {
			bindingResult.addError(new ObjectError("email", 
					"Email is required in order to reset your password"));
			return "pages/account/reset-password";
		}
		
		User user = userService.findByEmail(form.email);
		
		if(user == null) {
			bindingResult.addError(new ObjectError("error", 
					("Unknown email: " + form.email)));
			return "pages/account/reset-password";
		}
		
		return "pages/account/login";
	}

	private User createUser(RegistrationForm form) {
		User user = userService.create(form.firstName, form.lastName, 
				form.password, form.email, User.Role.USER);
		return user;
	}
	
	private void validateLogin(LoginForm form, BindingResult bindingResult) {
		if(StringUtils.isBlank(form.email)) {
			LOGGER.warn("doLogin request contained a blank email.");
			bindingResult.addError(new ObjectError("email", 
					"No email provided with login"));
		}
		
		if(StringUtils.isBlank(form.password)) {
			LOGGER.warn("doLogin request contained a blank password.");
			bindingResult.addError(new ObjectError("password", 
					"No password provided with login"));
		}
	}
	
	private void vaidateRegistration(RegistrationForm form, BindingResult bindingResult) {
		if(StringUtils.isBlank(form.firstName)) {
			LOGGER.warn("doRegistration request contained a blank first name.");
			bindingResult.addError(new ObjectError("firstName", 
					"First name is required for registration"));
		}
		
		if(StringUtils.isBlank(form.lastName)) {
			LOGGER.warn("doRegistration request contained a blank last name.");
			bindingResult.addError(new ObjectError("lastName", 
					"Last name is required for registration"));
		}
		
		if(StringUtils.isBlank(form.email)) {
			LOGGER.warn("doRegistration request contained a blank email.");
			bindingResult.addError(new ObjectError("email", 
					"Email is required for registration"));
		}
		
		if(StringUtils.isBlank(form.password) || StringUtils.isBlank(form.confirmedPassword)) {
			LOGGER.warn("doRegistration request contained a blank password.");
			bindingResult.addError(new ObjectError("password", 
					"Password is required for registration"));
		}
		
		if(!form.password.equals(form.confirmedPassword)) {
			LOGGER.warn("doRegistration request passwords did not match.");
			bindingResult.addError(new ObjectError("confirmedPassword", 
					"Passwords do not match."));
		}
	}
}
