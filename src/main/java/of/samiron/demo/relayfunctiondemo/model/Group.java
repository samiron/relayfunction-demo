package of.samiron.demo.relayfunctiondemo.model;

import lombok.*;
import of.samiron.demo.relayfunctiondemo.dto.GroupDTO;
import of.samiron.demo.relayfunctiondemo.dto.UserDTO;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
public class Group {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "group_name")
	private String name;

//	@ManyToMany
//	@Column
//	List<User> users;

	public Group from(GroupDTO groupDTO) {
		return Group.builder().build();
	}

	public GroupDTO toDto() {
		return GroupDTO.builder()
				.id(this.id)
				.name(this.name)
				.build();
	}
}
