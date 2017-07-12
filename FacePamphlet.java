/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {
	
/* Instance Variables */
private JButton addButton;
private JButton deleteButton;
private JButton lookupButton;
private JButton changeStatusButton;
private JButton changePictureButton;
private JButton addFriendButton;
private JTextField nameTextField;
private JTextField changeStatusTextField;
private JTextField changePictureTextField;
private JTextField addFriendTextField;
private FacePamphletCanvas profileDisplayingScreen;

/**
 * This method has the responsibility for initializing the 
 * interactors in the application, and taking care of any other 
 * initialization that needs to be performed.
 */
public void init() {
	//Initializing North Interactors
	// Adding the Name Label
	add(new JLabel("Name"), NORTH);
	// Adding the TextField corresponding to the Add, Delete, and Lookup buttons
	nameTextField = new JTextField(TEXT_FIELD_SIZE);
    nameTextField.addActionListener(this);
    add(nameTextField, NORTH);
    // Adding Add button
    add(addButton = new JButton("Add"), NORTH);
    // Adding Delete button
    add(deleteButton = new JButton("Delete"),NORTH);
    // Adding Lookup button
    add(lookupButton = new JButton("Lookup"), NORTH);
    
    //Initializing West Interactors
    // Adding the Change Status text field
    changeStatusTextField = new JTextField(TEXT_FIELD_SIZE);
    changeStatusTextField.addActionListener(this);
    add(changeStatusTextField, WEST);
    // Adding the Change Status button
    add(changeStatusButton = new JButton("Change Status"), WEST);
    // Adding vertical space buffer between Change Status button and Change Picture text field
    add(new JLabel(EMPTY_LABEL_TEXT), WEST);
    // Adding Change Picture text field
    changePictureTextField = new JTextField(TEXT_FIELD_SIZE);
    changePictureTextField.addActionListener(this);
    add(changePictureTextField, WEST);
    // Adding Change Picture button
    add(changePictureButton = new JButton("Change Picture"), WEST);
    // Adding vertical space buffer between Change Picture button and Add Friend text field
    add(new JLabel(EMPTY_LABEL_TEXT), WEST);
    // Adding Add Friend text field
    addFriendTextField = new JTextField(TEXT_FIELD_SIZE);
    addFriendTextField.addActionListener(this);
    add(addFriendTextField, WEST);
    // Adding Add Friend button
    add(addFriendButton = new JButton("Add Friend"), WEST);
    addActionListeners();
    // Adding a canvas to display profiles
    profileDisplayingScreen = new FacePamphletCanvas();
    add(profileDisplayingScreen);
    }
    
/**
 * This class is responsible for detecting when the buttons are
 * clicked or interactors are used, so you will have to add code
 * to respond to these actions.
 */
public void actionPerformed(ActionEvent e) {
	String name = nameTextField.getText();
	String changeStatusText = changeStatusTextField.getText();
	String changePictureText = changePictureTextField.getText();
	String addFriendText = addFriendTextField.getText();
	String displayMessage = "";
	GImage image = null;
	FacePamphletDatabase profileDataBase = new FacePamphletDatabase();
	//Initialize current profile to be profile with name in the "Name" text field
	FacePamphletProfile currentProfile = profileDataBase.getProfile(name);
	
	//Actions performed based on interactors activated
	// Do nothing if enter is pushed while cursor is in Name text field
	if(e.getSource() == nameTextField)
		return;
	
	// When Add button is clicked:
	if(e.getSource() == addButton) {
		// if current profile does not exist, add a new profile to the data base with the given
		// name. Then display a new message stating that the new profile with the 
		// appropriate name has been added.
		if(currentProfile == null) {
			profileDataBase.addProfile(new FacePamphletProfile(name));
			displayMessage = "New profile " + name + " added!";
			updateCanvas(profileDataBase.getProfile(name), displayMessage);
		}
		// if there is an existing profile with the same name as the name in the Name
		// text field, upon clicking the Add button, profileDisplayingScreen will show
		// message stating that the profile already exists.
		else {
			displayMessage =  "Sorry, a profile with the name, " + currentProfile.getName() + " already exists.";
			updateCanvas(currentProfile, displayMessage);
		}
	}
	
	// When Delete button is clicked:
	if(e.getSource() == deleteButton)	{
		// if current profile does not exist, display a new message stating that the profile 
		// with the inputted name does not exist.
		if(currentProfile == null) {
			displayMessage = "Sorry, a profile with the name, " + name + " does not exist.";
			updateCanvas(null, displayMessage);
		}
		// if there is a current profile with the inputted name(in Name text field) 
		// in the profile database, delete the selected profile and display a message
		// on the profileDisplayingScreen stating that the profile has been deleted.
		else {
			profileDataBase.deleteProfile(name);
			displayMessage = "The profile with the name, " + name + " has been deleted.";
			updateCanvas(null, displayMessage);
		}
	}
	
	// When the Lookup button is clicked:
	if(e.getSource() == lookupButton) {
		// if current profile does not exist, display a message stating that the profile does
		// not exist.
		if(currentProfile == null) {
			displayMessage = "Sorry, a profile with the name, " + name + " does not exist.";
			updateCanvas(null, displayMessage);
		}
		// if there is a current profile with the inputted name(in Name text field) 
		// in the profile database, display the selected profile along with the message
		// stating which profile is being displayed.
		else {
			displayMessage = "Displaying: " + currentProfile.getName();
			updateCanvas(currentProfile, displayMessage);
		}
	}
	
	// When the Enter key is pushed with the cursor in the Change Status text field:
	if(e.getSource() == changeStatusTextField) {
		// if current profile does not exist, message will be displayed asking 
		// user to select an existing profile.
		if(currentProfile == null) {
			displayMessage = "Invalid: Please select a profile.";
			updateCanvas(null, displayMessage);
		}
		// if there is a current profile with the inputted name(in Name text field) 
		// in the profile database, set the status of the current profile to the one 
		// inputted in the Change Status text field and display the updated profile with
		// a message saying the status has been changed to that in the Change Status text field.
		else {
			currentProfile.setStatus(changeStatusText);
			displayMessage = "Status changed to: " + currentProfile.getStatus();
			updateCanvas(currentProfile, displayMessage);
		}
	}
	
	// When the Change Status button is clicked:
	if(e.getSource() == changeStatusButton) {
		// if the current profile does not exist, message will be displayed 
		// asking user to select an existing profile.
		if(currentProfile == null) {
			displayMessage = "Invalid: Please select a profile.";
			updateCanvas(null, displayMessage);
		}
		// if there is a current profile with the inputted name(in Name text field) 
		// in the profile database, set the status of the current profile to the one 
		// inputted in the Change Status text field and display the updated profile with
		// a message saying the status has been changed to that in the Change Status text field.
		else {
			currentProfile.setStatus(changeStatusText);
			displayMessage = "Status changed to: " + currentProfile.getStatus();
			updateCanvas(currentProfile, displayMessage);
		}
	}
	
	// When the Enter key is pushed with the cursor in the Change Picture text field:
	if(e.getSource() == changePictureTextField) {
		// if an image exists with name inputted in the Change Picture text field,
		// set the profile image to the picture indicated by the name in the Change 
		// Picture text field. State that the profile picture has been updated.
		try {
			image = new GImage(changePictureText);
			currentProfile.setImage(image);
			displayMessage = "New profile picture updated";
			updateCanvas(currentProfile, displayMessage);
		} 
		// if image does not exist, display message error saying that we couldn't find an image with that name.
		catch (ErrorException ex) {
			displayMessage = "Sorry but we could not find a file with that name.";
			updateCanvas(currentProfile, displayMessage);
		}
	}
	
	// When the Change Picture button is clicked:
	if(e.getSource() == changePictureButton) {
		// if an image exists with name inputted in the Change Picture text field,
		// set the profile image to the picture indicated by the name in the Change 
		// Picture text field. State that the profile picture has been updated.
		try {
			image = new GImage(changePictureText);
			currentProfile.setImage(image);
			displayMessage = "New profile picture updated";
			updateCanvas(currentProfile, displayMessage);
		} 
		// if image does not exist, display message error saying that we couldn't find an image with that name.
		catch (ErrorException ex) {
			displayMessage = "Sorry but we could not find a file with that name.";
			updateCanvas(currentProfile, displayMessage);
		}
	}
	
	// When the Enter key is pushed with the cursor in the Add Friend text field:
	if(e.getSource() == addFriendTextField) {
		// if the current profile does not exist, display message asking user to select
		// an existing profile.
		if(currentProfile == null) {
			displayMessage = "Please select a profile to add a friend to.";
			updateCanvas(currentProfile, displayMessage);
		}
		// if current profile does exist in profile database:
		else {
			// if profile indicated in Add Friend text field exists in the profile database:
			if(profileDataBase.containsProfile(addFriendText)) {
				// if profile indicated in the Add Friend text field already exists in the
				// current profile's friend list, display message stating the friend already exists
				// in the friend list
				if(friendExists(currentProfile, addFriendText)) {
					displayMessage = "A person with that name already exists in this friends list.";
					updateCanvas(currentProfile, displayMessage);
				}
				// if current profile is the same as the profile indicated in the Add Friend text
				// field, display message stating that you can't add yourself as a friend.
				else if(currentProfile == profileDataBase.getProfile(addFriendText)) {
					displayMessage = "Sorry, you can't add yourself as a friend.";
					updateCanvas(currentProfile, displayMessage);
				}
				// if the profile indicated in the Add Friend text field is not the current profile
				// and does not exist already in the current profile's friend list, add the friend
				// to the current profile's friend list and add the current profile to the friend list
				// of the newly added friend. Display message stating that friend has been added.
				else {
					currentProfile.addFriend(addFriendText);
					profileDataBase.getProfile(addFriendText).addFriend(currentProfile.getName());
					displayMessage = addFriendText + " added as a friend";
					updateCanvas(currentProfile, displayMessage);
				}
			}
			// if profile indicated in Add Friend text field does not exist in profile data base,
			// display message saying so.
			else {
				displayMessage = "Sorry, that profile does not exist.";
				updateCanvas(currentProfile, displayMessage);
			}
		}
	}
	
	// When the Add Friend button is clicked:
	if(e.getSource() == addFriendButton) {
		// if the current profile does not exist, display message asking user to select
		// an existing profile.
		if(currentProfile == null) {
			displayMessage = "Please select a profile to add a friend to.";
			updateCanvas(currentProfile, displayMessage);
		}
		// if current profile does exist in profile database:
		else {
			// if profile indicated in Add Friend text field exists in the profile database:
			if(profileDataBase.containsProfile(addFriendText)) {
				// if profile indicated in the Add Friend text field already exists in the
				// current profile's friend list, display message stating the friend already exists
				// in the friend list
				if(friendExists(currentProfile, addFriendText)) {
					displayMessage = "A person with that name already exists in this friends list.";
					updateCanvas(currentProfile, displayMessage);
				}
				// if current profile is the same as the profile indicated in the Add Friend text
				// field, display message stating that you can't add yourself as a friend.
				else if(currentProfile == profileDataBase.getProfile(addFriendText)) {
					displayMessage = "Sorry, you can't add yourself as a friend.";
					updateCanvas(currentProfile, displayMessage);
				}
				// if the profile indicated in the Add Friend text field is not the current profile
				// and does not exist already in the current profile's friend list, add the friend
				// to the current profile's friend list and add the current profile to the friend list
				// of the newly added friend. Display message stating that friend has been added.
				else {
					currentProfile.addFriend(addFriendText);
					profileDataBase.getProfile(addFriendText).addFriend(currentProfile.getName());
					displayMessage = addFriendText + " added as a friend";
					updateCanvas(currentProfile, displayMessage);
				}
			}
			// if profile indicated in Add Friend text field does not exist in profile data base,
			// display message saying so.
			else {
				displayMessage = "Sorry, that profile does not exist.";
				updateCanvas(currentProfile, displayMessage);
			}
		}
	}
}

/** 
 * This method will take in a profile and a friend name and return a boolean, determining
 * if that friend is within the friends list of the passed in profile.
 * @param profile
 * @param friendName
 * @return friendExists
 */
private boolean friendExists(FacePamphletProfile profile, String friendName) {
	Iterator<String> friendsIterator = profile.getFriends();
	boolean friendExists = false;
	while(friendsIterator.hasNext()) {
		if(friendsIterator.next().equals(friendName)) {
			friendExists = true;
			break;
		}
	}
	return friendExists;
}

/**
 * This method is responsible for updating the canvas with the appropriate profile
 * messages.
 * @param profile
 * @param displayMessage
 */
private void updateCanvas(FacePamphletProfile profile, String displayMessage) {
	profileDisplayingScreen.removeAll();
	profileDisplayingScreen.displayProfile(profile);
	profileDisplayingScreen.showMessage(displayMessage);		
}
}
