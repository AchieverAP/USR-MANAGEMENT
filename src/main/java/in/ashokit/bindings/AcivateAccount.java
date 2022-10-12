package in.ashokit.bindings;

import lombok.Data;

@Data
public class AcivateAccount {

	private String email;
	private String tempPwd;
	private String newPwd;
}
