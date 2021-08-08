package of.samiron.demo.relayfunctiondemo.controller;

import of.samiron.demo.relayfunctiondemo.dto.GroupDTO;
import of.samiron.demo.relayfunctiondemo.dto.UserDTO;
import of.samiron.demo.relayfunctiondemo.model.Group;
import of.samiron.demo.relayfunctiondemo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController()
@RequestMapping("groups")
public class GroupController {

	private final GroupService groupService;

	@Autowired
	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@PostMapping
	public GroupDTO createGroup(GroupDTO groupDTO ) {
		return groupService.createGroup(groupDTO);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<GroupDTO> getGroups() {
		return groupService.getGroups();
	}

	public List<Group> getGroups(List<Integer> groupIds) {
		return null;
	}

	public List<Group> getGroups(List<String> groupIds) {
		return null;
	}

}
