package md.jamaddar.clearcapitalexercise;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProblemExerciseTest {
	
	@Test
	public void validateInitials() {
		Assert.assertEquals(convertNameToInitials("Bruno Mars"), "B.M.", "Expected initials did not match.");
		Assert.assertEquals(convertNameToInitials("Dave M Jones"), "D.M.J.", "Expected initials did not match.");
		Assert.assertEquals(convertNameToInitials("MichaelSmith"), "M.", "Expected initials did not match.");
	}
	
	public String convertNameToInitials(String name) {
		String initials = "";
		String[] nameSplit = name.split(" ");
		for(String str : nameSplit) {
			initials += str.charAt(0) + ".";
		}
		System.out.println(initials);
		return initials;
	}

}
