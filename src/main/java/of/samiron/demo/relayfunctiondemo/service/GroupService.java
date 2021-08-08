package of.samiron.demo.relayfunctiondemo.service;

import lombok.AllArgsConstructor;
import of.samiron.demo.relayfunctiondemo.dto.GroupDTO;
import of.samiron.demo.relayfunctiondemo.dto.UserDTO;
import of.samiron.demo.relayfunctiondemo.model.Group;
import of.samiron.demo.relayfunctiondemo.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupService {

	GroupRepository groupRepository;

	public GroupDTO createGroup(GroupDTO groupDTO) {
		return null;
	}

	public Collection<GroupDTO> getGroups() {
		Collection<GroupDTO> groupDTOS = new ArrayList<>();

		for(Group group : groupRepository.findAll()) {
			groupDTOS.add(group.toDto());
		}
		return groupDTOS;
	}
}
