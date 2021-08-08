package of.samiron.demo.relayfunctiondemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import of.samiron.demo.relayfunctiondemo.dto.UserDTO;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Table( name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Integer id;

	@Column(name = "first_name")
	String firstName;

	@Column(name = "last_name")
	String lastName;

	@Column(name = "email")
	String email;

//	@Column(name = "group_id")
//	@OneToMany()
//	Group groups;

	public User from(UserDTO userDTO) {
		return User.builder().build();
	}
}
