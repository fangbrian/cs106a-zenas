/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

import acm.graphics.*;
import java.util.*;

public class FacePamphletProfile implements FacePamphletConstants {
	
/* Instance Variables */
private HashMap<String, String> status = new HashMap<String, String>();
private HashMap<String, GImage> image = new HashMap<String, GImage>();
private ArrayList<String> friends = new ArrayList<String>();
private String name;
private String imageName = "";

/** 
 * This method takes care of any initialization needed for
 * the profile.
 */
public FacePamphletProfile(String name) {
	image.put(imageName, null);
	this.name = name;
	status.put(name, "");
}

/** This method returns the name associated with the profile. */ 
public String getName() {
	return name;
}

/** 
 * This method returns the image associated with the profile.  
 * If there is no image associated with the profile, the method
 * returns null. */ 
public GImage getImage() {
	if(image.containsKey(imageName))
		return image.get(imageName);
	else
		return null;
}

/** This method sets the image associated with the profile. */ 
public void setImage(GImage image) {
	this.image.put(imageName, image);
}

/** 
 * This method returns the status associated with the profile.
 * If there is no status associated with the profile, the method
 * returns the empty string ("").
 */ 
public String getStatus() {
	if(status.get(name) != null)
		return status.get(name);
	else
		return "";
}

/** This method sets the status associated with the profile. */ 
public void setStatus(String status) {
	this.status.put(name, status);
}

/** 
 * This method adds the named friend to this profile's list of 
 * friends.  It returns true if the friend's name was not already
 * in the list of friends for this profile (and the name is added 
 * to the list).  The method returns false if the given friend name
 * was already in the list of friends for this profile (in which 
 * case, the given friend name is not added to the list of friends 
 * a second time.)
 */
public boolean addFriend(String friend) {
	if(friends.contains(friend))
		return false;
	else {
		friends.add(friend);
		return true;
	}
}

/** 
 * This method removes the named friend from this profile's list
 * of friends.  It returns true if the friend's name was in the 
 * list of friends for this profile (and the name was removed from
 * the list).  The method returns false if the given friend name 
 * was not in the list of friends for this profile (in which case,
 * the given friend name could not be removed.)
 */
public boolean removeFriend(String friend) {
	if(friends.contains(friend)) {
		friends.remove(friend);
		return true;
	}
	else
		return false;
}

/** 
 * This method returns an iterator over the list of friends 
 * associated with the profile.
 */ 
public Iterator<String> getFriends() {
	return friends.iterator();
}

/** 
 * This method returns a string representation of the profile.  
 * This string is of the form: "name (status): list of friends", 
 * where name and status are set accordingly and the list of 
 * friends is a comma separated list of the names of all of the 
 * friends in this profile.
 * 
 * For example, in a profile with name "Alice" whose status is 
 * "coding" and who has friends Don, Chelsea, and Bob, this method 
 * would return the string: "Alice (coding): Don, Chelsea, Bob"
 */ 
public String toString() {		
	String listOfFriends = "";
	if(friends.isEmpty())
		listOfFriends = "";
	else {
		// Retrieve the first friend in the friends list. If the friends list is size is greater than
		// 1, then it will execute the loop below. If the friends list size is 1, then it will skip
		// the loop below. This was done to keep correct punctuation when printing out the friends list.
		// i.e. not having any commas when only one friend is listed, and having commas when we have 2 or
		// more friends in the friends list.
		listOfFriends = friends.get(0);
		//I know you said that I should start for loops with the intiialized variable at 0, but would the below be okay?
		for(int i = 1; i < friends.size(); i++) {
			listOfFriends += (", " + friends.get(i));
		}
	}
	return (name + "(" + status.get(name) + "): " + listOfFriends);
}
}
