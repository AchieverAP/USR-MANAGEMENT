package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.ashokit.entity.UserMaster;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster,Integer> {

	public List<UserMaster> findByEmailIdAndPassword(String email,String password);
	
	public UserMaster findByEmailId(String email);
	
}
