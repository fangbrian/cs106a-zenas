/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
/** 
 * Constructor
 * This method takes care of any initialization needed for 
 * the display
 */
public FacePamphletCanvas() {
}

/** 
 * This method displays a message string near the bottom of the 
 * canvas.  Every time this method is called, the previously 
 * displayed message (if any) is replaced by the new message text 
 * passed in.
 */
public void showMessage(String msg) {
	GLabel displayedMessage = new GLabel(msg);
	displayedMessage.setFont(MESSAGE_FONT);
	displayedMessage.setLocation(getWidth() / 2 - displayedMessage.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	add(displayedMessage);
}

/** 
 * This method displays the given profile on the canvas.  The 
 * canvas is first cleared of all existing items (including 
 * messages displayed near the bottom of the screen) and then the 
 * given profile is displayed.  The profile display includes the 
 * name of the user from the profile, the corresponding image 
 * (or an indication that an image does not exist), the status of
 * the user, and a list of the user's friends in the social network.
 */
public void displayProfile(FacePamphletProfile profile) {
	if(profile != null) {
		//Adding the Name of the Profile
		GLabel name = new GLabel(profile.getName());
		name.setFont(PROFILE_NAME_FONT);
		double nameAscent = name.getAscent();
		name.setLocation(LEFT_MARGIN, (TOP_MARGIN + nameAscent));
		add(name);
		
		//Adding the Profile Pic/No Image Text
		if(profile.getImage() == null) {
			// Adding the GRect for when there is no image available for the profile
			GRect emptyImageRectangle = new GRect(LEFT_MARGIN, TOP_MARGIN + nameAscent + IMAGE_MARGIN, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(emptyImageRectangle);
			GLabel noImageText = new GLabel("NO IMAGE");
			noImageText.setFont(PROFILE_IMAGE_FONT);
			// Defining noImageText's X and Y coordinates
			double noImageTextX = LEFT_MARGIN + ((IMAGE_WIDTH / 2) - (noImageText.getWidth() / 2));
			double noImageTextY = TOP_MARGIN + nameAscent + IMAGE_MARGIN + (IMAGE_HEIGHT / 2) + (noImageText.getAscent() / 2);
			noImageText.setLocation(noImageTextX, noImageTextY);
			add(noImageText);
		}
		else {
			// Adding the image for the profile
			GImage image = profile.getImage();
			double imageY = TOP_MARGIN + nameAscent + IMAGE_MARGIN;
			image.setLocation(LEFT_MARGIN, imageY);
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image);
		}
		
		//Adding the Status below the profile picture
		if(profile.getStatus() == "") {
			// What to display when there is no current status
			GLabel noStatus = new GLabel("No current status");
			noStatus.setFont(PROFILE_STATUS_FONT);
			noStatus.setLocation(LEFT_MARGIN, TOP_MARGIN + nameAscent + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + noStatus.getAscent());
			add(noStatus);
		}
		else {
			// Displaying the non-empty profile status
			GLabel status = new GLabel(profile.getName() + " is " + profile.getStatus());
			status.setFont(PROFILE_STATUS_FONT);
			status.setLocation(LEFT_MARGIN, TOP_MARGIN + nameAscent + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + status.getAscent());
			add(status);
		}
		
		//Adding the List of Friends
		// Adding the friends list Title
		double friendsListY = TOP_MARGIN + nameAscent + IMAGE_MARGIN;
		GLabel friendsList = new GLabel("Friends:", getWidth() / 2, friendsListY);
		friendsList.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendsList);
		// Adding the list of friends
		int nameCount = 1;
		Iterator<String> friendsIterator = profile.getFriends();
		while(friendsIterator.hasNext()) {
			GLabel friendName = new GLabel(friendsIterator.next());
			friendName.setFont(PROFILE_FRIEND_FONT);
			friendName.setLocation(getWidth() / 2, friendsListY + (friendName.getAscent() * nameCount) + (PROFILE_FRIEND_FONT_VERTICAL_MARGIN * nameCount));
			add(friendName);
			nameCount++;
		}
	}
	
	else {
		return;
	}
}	
}
