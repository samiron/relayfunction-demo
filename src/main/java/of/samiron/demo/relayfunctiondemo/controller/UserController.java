package of.samiron.demo.relayfunctiondemo.controller;

import of.samiron.demo.relayfunctiondemo.dto.UserDTO;
import of.samiron.demo.relayfunctiondemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping()
	public UserDTO createUser(UserDTO userDTO ) {
		return userService.createUser(userDTO);
	}


}
