package security.controller;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import security.entity.AppUser;
import security.service.AppUserService;

@Named
@ViewScoped
public class ChangePasswordController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String currentPassword;				// getter/setter
	private String newPassword;					// getter/setter	
	private String confirmedNewPassword;		// getter/setter
	private String currentPasswordHash;
	
	@Inject
	private AppUserService userService;
	
	@PostConstruct
	public void init() {
		//  Get the username of the current authenticated user
		String username = Faces.getRemoteUser();
		//  Get the current AppUser
		AppUser currentAppUser = userService.findByUsername(username);
		//  Store the PasswordHash of the current user
		currentPasswordHash = currentAppUser.getPassword();
	}
	
	public String hashPassword(String plainTextPassword) {
		String passwordHash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest( plainTextPassword.getBytes() );
			passwordHash = Base64.getEncoder().encodeToString(hash);
		} catch (Exception e) {
			Messages.addGlobalError("Hashing algorithm not supported.");
		}
		return passwordHash;
	}
	
	public String changePassword() {
		String nextUrl = null;
		// verify current password value matches user password
		String passwordHash = hashPassword(currentPassword);
		if ( passwordHash.equals( currentPasswordHash ) ) {
			// verify newPassword value matches confirmedNewPassword value
			if ( newPassword.equals( confirmedNewPassword ) ) {
				// update the password in the system
				String username = Faces.getRemoteUser();
				AppUser currentUser = userService.findByUsername(username);
				currentUser.setPassword( hashPassword(newPassword) );
				userService.update(currentUser);				
				// logout current user
				Faces.invalidateSession();
				nextUrl = "/index?faces-redirect-true";
			} else {
				Messages.addGlobalError("New Password value does not match Confirm New Password value");
			}
		} else {
			Messages.addGlobalError("Current Password value is incorrect.");
		}
		return nextUrl;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmedNewPassword() {
		return confirmedNewPassword;
	}

	public void setConfirmedNewPassword(String confirmedNewPassword) {
		this.confirmedNewPassword = confirmedNewPassword;
	}

	
}
