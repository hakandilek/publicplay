package controllers;


import play.mvc.Result;
import socialauth.core.SocialAware;

public class App extends Admin {

	@SocialAware public static Result index() {
		return category.listAll();
	}

	/*
	@Secure @Restrict("admin") @RestrictApproved public static Result fetch() {
		return fetch.newLink();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result fetchCreate() {
		return fetch.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result fetchDelete(Long key) {
		return fetch.delete(key);
	}

	 */
}