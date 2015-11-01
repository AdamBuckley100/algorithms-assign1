package imageprocessing;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import edu.princeton.cs.introcs.Picture;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/**
 * 
 * This class provides the user with a command line interface to choose the image that they would
 * like to possibly perform changes to. The changes are to view the image in a binarized form.
 * The user can also receive the number of components in the image chosen.
 *
 * @author Adam Buckley (Student I.D: 20062910)
 * @version 1
 * @date 23/10/2015
 */

public class ConnectedComponentImage {

	/**
	 * instance field variables.
	 */
	private Picture picture;
	private Scanner input;

	/**
	 * Constructor for objects of class ConnectedComponentImage - i.e. creates a connectedComponentImage
	 * object.
	 * 
	 * Initialize instance fields (input of type Scanner
	 * and picture of type Picture).
	 * 
	 * @param fileLocation - this method takes in the full
	 * directory path of the image, this directory path is 
	 * of type String.
	 * The global instance variable "picture" then gets the
	 * picture for which the user has input the String directory path for.
	 */
	public ConnectedComponentImage(String fileLocation) {
		picture = new Picture(fileLocation);
		input = new Scanner(System.in);
	}

	/**
	 * The main method
	 * @param argvs - unused
	 * @throws IOException 
	 * 
	 * This is the main method, what is contained in the method
	 * is what will immediately run when the connectedComponentImage object is run.
	 * The first parts of the method will be run first followed by the next line. etc.
	 */
	public static void main(String[] args) throws IOException {	
		StdOut.println("Welcome, Please enter the full directory path of the image ==>");
		String input = StdIn.readString();
		ConnectedComponentImage app = new ConnectedComponentImage(input);
		app.run();
	}

	/**
	 * showPicture method - this method reads in a Picture object
	 * from parameter and simply shows the picture to the user
	 * using the Picture class's show ".show()" method which
	 * displays the picture in a window on the screen in a java
	 * program pop-up window.
	 * 
	 * @param pic - this method reads in an object called pic
	 * which is a picture object of type Picture.
	 */
	public void showPicture(Picture pic) {
		pic.show();
	}

	/**
	 * showOriginalPicture method - this method takes the
	 * global instance variable "picture" which is of type Picture
	 * and simple shows the picture to the user
	 * using the Picture class's show ".show()" method.
	 * The picture in a java program pop-up window. 
	 */
	public void showOriginalPicture() {
		Picture originalPicture = new Picture(picture);
		originalPicture.show();
	}

	/**
	 * countComponents method returns the number of components
	 * that have been identified in the previously chosen image.
	 * 
	 * @return int - returns a single integer: 
	 * the number of independent separate components
	 * (i.e. single objects) that exist 
	 * in the image previously chosen. (0+)
	 */
	public int countComponents() {
		Picture attainBinerizedPic = binaryComponentImage();
		WeightedQuickUnion weightQuickUnion = new WeightedQuickUnion(attainBinerizedPic.width()*attainBinerizedPic.height());

		for (int i = 0; i < attainBinerizedPic.width(); i++)
		{
			for (int j = 0; j < attainBinerizedPic.height(); j++)
			{
				if (j+1 < attainBinerizedPic.height())
				{
					if (attainBinerizedPic.get(i,j).equals(attainBinerizedPic.get(i, j+1)))
					{
						weightQuickUnion.union(linear(i, j), linear(i, j+1));
					}
				}
				if (i+1 < attainBinerizedPic.width())
				{
					if (attainBinerizedPic.get(i,j).equals(attainBinerizedPic.get(i+1, j)))
					{
						weightQuickUnion.union(linear(i, j), linear(i+1, j));
					}
				}
			}
		}	
		// returned the number of components (minus 1 because the background is an
		// object objectively but not subjectively in the way the user typically will 
		// look at things.
		return weightQuickUnion.count() - 1;
	}

	/**
	 * The linear method is a method created to take a single pixel
	 * in a picture's current X co-ordinate and Y co-ordinate and return
	 * a single number which is the pixel's position in the picture.
	 * (note: picture pixel counting starting from the bottom left hand
	 * corner and starts at 0).
	 * 
	 * @return int - returns a single integer that represents the pixel number
	 * in the picture.
	 */
	public int linear (int x, int y)
	{
		return (y*(picture.width())+x);
	}

	/**
	 * Accessor method - returns the picture instance variable field.
	 * 
	 * @return the picture instance variable field of type Picture.
	 */
	public Picture getPicture() {
		return picture;
	}

	/**
	 * binaryCompomentImage method returns a binerized version
	 * of the original image (the original image is assumed
	 * to be originally coloured).
	 * 
	 * @return a picture object of type Picture which is
	 * a binarized image of the original image picture 
	 * of type Picture.
	 */
	public Picture binaryComponentImage() {
		Picture pic = new Picture(picture);

		int width = pic.width();
		int height = pic.height();

		double thresholdPixelValue = 128.0;

		// Convert to black or white.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				/* 
				 * important method part below: if the color at the pixel
				 * 'colourAtOnePixel' has a smaller value than the threshold
				 * value then the color at that picture is changed to black
				 * otherwise the color at that pixel is changed to white.
				 */
				Color colourAtOnePixel = pic.get(x, y);
				if (Luminance.lum(colourAtOnePixel) < thresholdPixelValue) {
					pic.set(x, y, Color.BLACK);
				} else {
					pic.set(x, y, Color.WHITE);
				}
			}
		}
		return pic;
	}

	/**
	 * mainMenu() -  this method displays  menu for the application, 
	 * it reads the menu option that the user inputs and returns it.
	 * 
	 * @return inputted menu choice from the user
	 */
	private int mainMenu()     
	{ 
		StdOut.println("Put input the number of your choice:");
		StdOut.println("---------");     
		StdOut.println("  1) See the origial picture"); 
		StdOut.println("  2) Show the picture: in Binarized form");
		StdOut.println("  3) Find out how many components are in the picture");
		StdOut.println("  4) Put a bounding box around each component");
		StdOut.println("  5) Make each component in the picture a different colour");
		StdOut.println("  0) Exit");
		StdOut.print("==>> ");
		int option = -1;
		try
		{
			option = input.nextInt();
		}
		catch (InputMismatchException e)
		{
			//default option in the run method (method directly below) will be executed when 0-12 
			// has not been inputted in try, a different charactar was passed instead.
		}
		return option;
	}

	/**
	 *  This method returns a pic object of type Picture.
	 *  This pic object will be the original user-inputted
	 *  image, but it will be binarized and there will be a red box
	 *  surrounding each separate singular component from the original image.
	 *  
	 * @return - An object called pic which is of type Picture
	 * is returned. it is the original image except with a red
	 * box surrounding each component.
	 * 
	 */
	public Picture boundingBoxForEveryObject()
	{
		ListMultimap<Integer, Integer> multimap = ArrayListMultimap.create();
		
		//get the binary version of the image and assign it to pic.
		Picture pic = binaryComponentImage();

		WeightedQuickUnion weightQuickUnion = new WeightedQuickUnion(pic.width()*pic.height());

		//An array list of int's is created called roots
		//This list contains all the roots in the picture of each component.
		ArrayList<Integer> roots = new ArrayList<Integer>();

		//these "for" loops go through every pixel in the picture (One x+y co-ordinate at a time)
		for (int i = 0; i < pic.width(); i++)
		{
			for (int j = 0; j < pic.height(); j++)
			{
				if (!roots.contains(weightQuickUnion.root(linear(i,j))))
				{
					roots.add(weightQuickUnion.root(linear(i,j)));
				}

			}
		}

		for(Integer root: roots) 
		{
			// two "for" loops going through every pixel again.
			for (int i = 0; i < pic.width(); i++)
			{
				for (int j =0; j < pic.height(); j++)
				{
					// if the pixel we are current at is the same as the current root
					// we are currently looking at then add to the empty multimap:
					// key as the root int and one of the values in the multimap is
					// the pixel we are currently at.
					if (weightQuickUnion.root(linear(i,j)) == root)
					{
						multimap.put(root, linear(i,j));
					}
				}
			}
		}

		for(Integer root: roots)
		{
			int maxX = 0;
			int minX = pic.width();
			int maxY = 0;
			int minY = pic.height();

			// Go through every pixel.
			for (int x = 0; x < pic.width(); x++) {
				for (int y = 0; y < pic.height(); y++) {
					// If the pixel in question in contained within the root we ae
					// currently looking at then end this if loop.
					if (multimap.containsEntry((root), (linear(x,y))))
					{
						// if the pixels x value is smaller number than out currently assigned
						// minX value within this root then change the minX value to x and so on.
						if (x < minX)
							minX = x;
						if (x > maxX)
							maxX = x;
						if (y < minY)
							minY = y;
						if (y > maxY)
							maxY = y;
					}
				}
			}

			// Red coloured lines drawn across the single component (horizontally and vertically).
			for (int x = minX; x <= maxX; x++) {
				if (multimap.containsEntry((root), (linear(x,minY)))) 
				{
					pic.set(x, minY, Color.RED);
				}
				if (multimap.containsEntry((root), (linear(x,maxY))))
				{
					pic.set(x, maxY, Color.RED);
				}
			}

			for (int y = minY; y <= maxY; y++) {
				if (multimap.containsEntry((root), (linear(minX,y))))
				{
					pic.set(minX, y, Color.RED);
				}
				if (multimap.containsEntry((root), (linear(maxX,y))))
				{
					pic.set(maxX, y, Color.RED);
				}
			}
		}
		return pic;
	}

	/**
	 * 
	 * The randomColour method creates a coloured image
	 * representation of the original image.
	 * 
	 * In this method, first the picture is binarized then
	 * essentially every separate singular component in
	 * the picture will be given a different colour.
	 * 
	 * Note: No two components will have the same exact colour.
	 * 
	 */
	public Picture randomColour()
	{
		ListMultimap<Integer, Integer> multimap = ArrayListMultimap.create();

		//get the binary version of the image and assign it to pic.
		Picture pic = binaryComponentImage();

		WeightedQuickUnion weightQuickUnion = new WeightedQuickUnion(pic.width()*pic.height());

		//An array list of int's is created called roots
		//This list contains all the roots in the picture of each component.
		ArrayList<Integer> roots = new ArrayList<Integer>();

		//these "for" loops go through every pixel in the picture (One x+y co-ordinate at a time)
		for (int i = 0; i < pic.width(); i++)
		{
			for (int j = 0; j < pic.height(); j++)
			{
				if (!roots.contains(weightQuickUnion.root(linear(i,j))))
				{
					roots.add(weightQuickUnion.root(linear(i,j)));
				}

			}
		}

		for(Integer root: roots) 
		{
			// two "for" loops going through every pixel again.
			for (int i = 0; i < pic.width(); i++)
			{
				for (int j =0; j < pic.height(); j++)
				{
					// if the pixel we are current at is the same as the current root
					// we are currently looking at then add to the empty multimap:
					// key as the root int and one of the values in the multimap is
					// the pixel we are currently at.
					if (weightQuickUnion.root(linear(i,j)) == root)
					{
						multimap.put(root, linear(i,j));
					}
				}
			}
		}

		for(Integer root: roots)
		{
			Random randomization = new Random();
			Color colour = new Color(randomization.nextInt(255),randomization.nextInt(255),randomization.nextInt(255));

			// Going through every pixel.
			for (int x = 0; x < pic.width(); x++) {
				for (int y = 0; y < pic.height(); y++) {
					// If for the root we are currently looking through, the
					// multimap contains the pixel we are currently at,
					// then set that pixel we are at to a certain colour.
					if (multimap.containsEntry((root), (linear(x,y))))
					{
						pic.set(x, y, colour);
					}
				}
			}
		}
		return pic;
	}

	/**
	 * This method is in control of the switch case loop.
	 * This method calls the mainMenu() method which prompts
	 * the user to input an int to represent the choice
	 * that they want to choose from the choices printed
	 * out to them in the CLI. 
	 * 
	 * By calling the mainMenu() method, an int is returned
	 * which is then matched to the corresponding switch case
	 * in this run() method.
	 */
	private void run()
	{
		int option = mainMenu();
		while (option != 0)
		{
			switch (option)
			{
			case 1:   showOriginalPicture(); 
			break;
			case 2:   showPicture(binaryComponentImage()); 
			break;
			case 3:   countComponents();
			System.out.println(countComponents());
			break;
			case 4:   showPicture(boundingBoxForEveryObject());
			break;
			case 5:   showPicture(randomColour());
			break;
			default:  System.out.println("Invalid option entered (you must enter 0-2 inclusive)");
			break;
			}
			//pause the program so that the user can read what we just printed to the terminal window
			StdOut.println("\nPress any key to continue...");
			input.nextLine();
			input.nextLine();  //this second read is required - bug in Scanner class; String read is ignored after just reading an int.

			//display the main menu again
			option = mainMenu();
		}
		//the user chose option 0, so program is exited
		StdOut.println("Exiting... bye");
		System.exit(0);
	}
}