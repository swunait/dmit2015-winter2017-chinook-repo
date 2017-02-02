package chinook.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import chinook.entity.AppUser;
import chinook.entity.AppUserRole;
import chinook.entity.AppUserRolePK;

@Stateful
public class AppUserService {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	public void add(AppUser currentUser, String roleName) throws NoSuchAlgorithmException {
		String plainTextPassword = currentUser.getPassword();
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hash = md.digest( plainTextPassword.getBytes() );
		String hashPassword = Base64.getEncoder().encodeToString(hash);
		currentUser.setPassword(hashPassword);
		entityManager.persist(currentUser);
		
		AppUserRolePK userRoleId = new AppUserRolePK();
		userRoleId.setLoginName(currentUser.getLoginName());
		userRoleId.setRoleName(roleName);
		AppUserRole userRole = new AppUserRole();
		userRole.setId(userRoleId);
		entityManager.persist(userRole);
	}
	
	@SuppressWarnings("unchecked")
	public List<AppUser> findAll() {
		return entityManager.createQuery("SELECT au FROM AppUser au").getResultList();
	}
	
}
