import java.io.IOException;
import java.util.Arrays;

import tijos.util.base64.Base64;


/**
 * 此例程演示BASE64编解码
 * 
 */
public class base64demo {

	public static void main(String[] args) {
		
		byte [] input = new byte[] {1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8};
		
		String output = Base64.encode(input);
		System.out.println("encode lines output = " + output);

		byte[] temp = null;
		try {
			temp = Base64.decode(output);
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		
		System.out.print(Arrays.toString(temp));			
		
		System.out.println("\nExit");
	}

}
