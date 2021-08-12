package of.samiron.demo.relayfunctiondemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@Entity
@Table( name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Integer id;

	@Column(name = "full_name")
	String fullName;

	@Column(name = "email")
	String email;

	@Column(name="activation_date")
	LocalDate activationDate;

	@Column(name="expiration_date")
	LocalDate expirationDate;

}
