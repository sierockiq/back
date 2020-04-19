package com.quentin.sierocki.legume.back.ti.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.quentin.sierocki.legume.back.controller.ControllerException;
import com.quentin.sierocki.legume.back.controller.UserController;
import com.quentin.sierocki.legume.back.controller.model.UserDTO;
import com.quentin.sierocki.legume.back.domain.entity.UserDAO;
import com.quentin.sierocki.legume.back.service.UserService;
import com.quentin.sierocki.legume.back.ti.SpringBootRestApplicationTest;
import com.quentin.sierocki.legume.back.ti.util.Builder;

public class TestUserController extends SpringBootRestApplicationTest {
	@Autowired
	UserController userController;

	@Autowired
	UserService userService;

	// findAll
	@Test(expected = ControllerException.class)
	public void getUsers_no_users_exist() throws Exception {
		userController.getUsers(1);
		Assert.fail();
	}

	@Test
	public void getUsers_OK() throws Exception {
		userService.save(Builder.createUser());
		List<UserDTO> users = userController.getUsers(1);
		Assert.assertTrue(users.size() == 1);
		Assert.assertEquals(users.get(0).getUsername(), "quentin");
	}

	@Test(expected = ControllerException.class)
	public void getUser_no_users_exist() throws Exception {
		UserDTO user = userController.getUser(1);
		Assert.fail();
	}

	@Test
	public void getUser_OK() throws Exception {
		userService.save(Builder.createUser());
		UserDAO user = userService.findUserByUserName("quentin");
		Assert.assertEquals(user.getUsername(), "quentin");
	}

}
