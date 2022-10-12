package in.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table(name = "USER_MASTER")
@Data
public class UserMaster {

	@Id
	@GeneratedValue
	private Integer uid;
	private String fullName;
	private long mobileNo;
	private LocalDate dob;
	private String emailId;
	private String gender;
	private long ssn;
	private String password;
	private String accStatus;
	
}
