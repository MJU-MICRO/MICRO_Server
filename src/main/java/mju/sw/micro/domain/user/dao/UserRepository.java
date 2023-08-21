package mju.sw.micro.domain.user.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mju.sw.micro.domain.user.domain.Role;
import mju.sw.micro.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Query("SELECT u FROM User u JOIN u.userRoles ur WHERE ur.role = :adminRole")
	List<User> findAllUsersByAdminRole(@Param("adminRole") Role adminRole);

	@Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT ur.user.id FROM UserRole ur WHERE ur.role = :adminRole)")
	List<User> findAllUsersWithoutAdminRole(@Param("adminRole") Role adminRole);
}
