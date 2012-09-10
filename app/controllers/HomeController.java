package controllers;

import java.util.List;

import models.Post;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class HomeController extends Controller {

	public static Result index() {
		List<Post> list = Post.all();
		return ok(index.render(list));
	}

}