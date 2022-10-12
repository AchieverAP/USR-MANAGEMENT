package in.ashokit.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
	
	private String fullName;
	private long mobileNo;
	private LocalDate dob;
	private String emailId;
	private String gender;
	private long ssn;
	private String password;
	private String accStatus;
	
}


