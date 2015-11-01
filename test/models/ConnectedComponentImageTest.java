package models;

import static org.junit.Assert.*;
import imageprocessing.ConnectedComponentImage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.introcs.Picture;

public class ConnectedComponentImageTest {

	private ConnectedComponentImage connectedComponentImage1;

	@Before
	public void setup()
	{
		String filelocation1  = "images/shapes.bmp";
		connectedComponentImage1 = new ConnectedComponentImage(filelocation1);
	}

	@After
	public void tearDown()
	{
		connectedComponentImage1 = null;
	}

	@Test
	public void testPictureType()
	{
		if(connectedComponentImage1.getPicture() instanceof Picture)
		{ System.out.println("it's a picture object"); }
	}

	@Test
	public void testCountComponents()
	{
		int counting = connectedComponentImage1.countComponents();
		assertTrue(counting == 3);
	}

	@Test
	public void testBoundingBoxForEveryObject()
	{
		connectedComponentImage1.boundingBoxForEveryObject();
		if(connectedComponentImage1.getPicture() instanceof Picture)
		{ System.out.println("It's still a picture object after this "
				+ "method has been run"); }
	}

	@Test
	public void testRandomColour()
	{
		connectedComponentImage1.randomColour();
		if(connectedComponentImage1.getPicture() instanceof Picture)
		{ System.out.println("It's still a picture object after this "
				+ "method has been run"); }
	}		

	@Test
	public void testBinaryComponentImage()
	{
		connectedComponentImage1.randomColour();
		if(connectedComponentImage1.getPicture() instanceof Picture)
		{ System.out.println("It's still a picture object after this "
				+ "method has been run"); }
	}

	@Test
	public void testBinaryComponentImage2()
	{
		connectedComponentImage1.binaryComponentImage();
	}
}