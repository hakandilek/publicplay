package test;

import models.Category;
import models.Comment;
import models.ContentReport;
import models.ContentReport.ContentType;
import models.Post;
import models.SecurityRole;
import models.User;
import models.User.Status;
import models.dao.CategoryDAO;
import models.dao.CommentDAO;
import models.dao.ContentReportDAO;
import models.dao.PostDAO;
import models.dao.PostRatingDAO;
import models.dao.ReputationDAO;
import models.dao.ReputationValueDAO;
import models.dao.SecurityRoleDAO;
import models.dao.UserDAO;
import play.utils.dao.EntityNotFoundException;

public class IntegrationTest extends BaseTest {

	protected UserDAO userDAO;
	protected PostDAO postDAO;
	protected CategoryDAO categoryDAO;
	protected ContentReportDAO contentReportDAO;
	protected SecurityRoleDAO securityRoleDAO;
	protected CommentDAO commentDAO;
	protected PostRatingDAO postRatingDAO;
	protected ReputationDAO reputationDAO;
	protected ReputationValueDAO reputationValueDAO;

	protected void test(Runnable runnable) {
		Runnable setUp = new Runnable() {

			public void run() {
				categoryDAO = getInstance(CategoryDAO.class);
				securityRoleDAO = getInstance(SecurityRoleDAO.class);
				userDAO = getInstance(UserDAO.class);
				postDAO = getInstance(PostDAO.class);
				commentDAO = getInstance(CommentDAO.class);
				contentReportDAO = getInstance(ContentReportDAO.class);
				postRatingDAO = getInstance(PostRatingDAO.class);
				reputationDAO = getInstance(ReputationDAO.class);
				reputationValueDAO = getInstance(ReputationValueDAO.class);

				setUpCategory();
				setUpSecurityRole();
				setUpUser();
				setUpPost();
				setUpComment();
				setUpContentReport();
			}
		};
		Runnable tearDown = new Runnable() {
			public void run() {
				try {
					tearDownComment();
					tearDownPost();
					tearDownUser();
					tearDownSecurityRole();
					tearDownCategory();
					tearDownContentReport();

					categoryDAO = null;
					securityRoleDAO = null;
					userDAO = null;
					postDAO = null;
					commentDAO = null;
					contentReportDAO = null;
					postRatingDAO = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		test(setUp, runnable, tearDown);
	}

	private void setUpCategory() {
		categoryDAO.create(new Category("categoryA"));
		categoryDAO.create(new Category("categoryB"));
		categoryDAO.create(new Category("categoryC"));
		categoryDAO.create(new Category("categoryD"));
		categoryDAO.create(new Category("categoryE"));
	}

	private void tearDownCategory() throws EntityNotFoundException  {
		categoryDAO.remove("categoryA");
		categoryDAO.remove("categoryB");
		categoryDAO.remove("categoryC");
		categoryDAO.remove("categoryD");
		categoryDAO.remove("categoryE");
	}

	private void setUpSecurityRole() {
		securityRoleDAO.create(new SecurityRole(-3L, "role1"));
		securityRoleDAO.create(new SecurityRole(-4L, "role2"));
		securityRoleDAO.create(new SecurityRole(-5L, "role3"));
		securityRoleDAO.create(new SecurityRole(-6L, "role4"));
	}

	private void tearDownSecurityRole() throws EntityNotFoundException {
		securityRoleDAO.remove(-3L);
		securityRoleDAO.remove(-4L);
		securityRoleDAO.remove(-5L);
		securityRoleDAO.remove(-6L);
	}

	private void setUpUser() {
		User user;
		user = new User();
		user.setKey("testuser2");
		user.setOriginalKey("testuser2");
		user.setLoginCount(42);
		user.setFirstName("test");
		user.setLastName("user2");
		user.setEmail("testuser2@test.com");
		user.setProfileImageURL("http://static.ak.fbcdn.net/rsrc.php/v2/yL/r/HsTZSDw4avx.gif");
		user.setProvider("some provider");
		user.setStatus(Status.NEW);
		userDAO.create(user);
		user = new User();
		user.setKey("testuser3");
		user.setOriginalKey("testuser3");
		user.setLoginCount(42);
		user.setFirstName("test");
		user.setLastName("user3");
		user.setEmail("testuser3@test.com");
		user.setProfileImageURL("http://static.ak.fbcdn.net/rsrc.php/v2/yL/r/HsTZSDw4avx.gif");
		user.setProvider("some provider");
		user.setStatus(Status.NEW);
		userDAO.create(user);
	}

	private void tearDownUser() throws EntityNotFoundException {
		userDAO.remove("testuser2");
		userDAO.remove("testuser3");
	}

	private void setUpPost() {
		Category category = categoryDAO.get("categoryA");
		User user = userDAO.get("testuser2");
		Post p;
		
		p = new Post();
		p.setKey(-101L);
		p.setCategory(category);
		p.setTitle("test");
		p.setContent("test");
		p.setCreatedBy(user);
		postDAO.create(p);
		
		p = new Post();
		p.setKey(-102L);
		p.setCategory(category);
		p.setTitle("test");
		p.setContent("test");
		p.setCreatedBy(user);
		postDAO.create(p);
		
		p = new Post();
		p.setKey(-103L);
		p.setCategory(category);
		p.setTitle("test");
		p.setContent("test");
		p.setCreatedBy(user);
		postDAO.create(p);
	}

	private void tearDownPost() throws EntityNotFoundException {
		postDAO.remove(101L);
		postDAO.remove(102L);
		postDAO.remove(103L);
	}

	private void setUpComment() {
		User user = userDAO.get("testuser2");
		Comment c;
		
		c = new Comment();
		c.setKey(-1011L);
		c.setPost(postDAO.get(-101L));
		c.setContent("test");
		c.setCreatedBy(user);
		commentDAO.create(c);
		c = new Comment();
		c.setKey(-1012L);
		c.setPost(postDAO.get(-101L));
		c.setContent("test");
		c.setCreatedBy(user);
		commentDAO.create(c);
		c = new Comment();
		c.setKey(-1013L);
		c.setPost(postDAO.get(-101L));
		c.setContent("test");
		c.setCreatedBy(user);
		commentDAO.create(c);
		c = new Comment();
		c.setKey(-1014L);
		c.setPost(postDAO.get(-101L));
		c.setContent("test");
		c.setCreatedBy(user);
		commentDAO.create(c);
		
		c = new Comment();
		c.setKey(-1021L);
		c.setPost(postDAO.get(-102L));
		c.setContent("test");
		c.setCreatedBy(user);
		commentDAO.create(c);
		
		c = new Comment();
		c.setKey(-1031L);
		c.setPost(postDAO.get(-103L));
		c.setContent("test");
		c.setCreatedBy(user);
		commentDAO.create(c);
		
	}

	private void tearDownComment() {
		// TODO Auto-generated method stub
		
	}

	private void setUpContentReport() {
		User user = userDAO.get("testuser2");
		ContentReport cr;
		
		cr  = new ContentReport(-101L);
		cr.setCreatedBy(user);
		cr.setStatus(ContentReport.Status.NEW);
		cr.setContentType(ContentType.POST);
		contentReportDAO.create(cr);
		
		cr  = new ContentReport(-101L);
		cr.setCreatedBy(user);
		cr.setStatus(ContentReport.Status.NEW);
		cr.setContentType(ContentType.POST);
		contentReportDAO.create(cr);
		
		cr  = new ContentReport(-101L);
		cr.setCreatedBy(user);
		cr.setStatus(ContentReport.Status.NEW);
		cr.setContentType(ContentType.COMMENT);
		contentReportDAO.create(cr);
		
		cr  = new ContentReport(-101L);
		cr.setCreatedBy(user);
		cr.setStatus(ContentReport.Status.NEW);
		cr.setContentType(ContentType.COMMENT);
		contentReportDAO.create(cr);
		
	}

	private void tearDownContentReport() {
		// TODO Auto-generated method stub
		
	}

}
