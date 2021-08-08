package of.samiron.demo.relayfunctiondemo.repository;

import of.samiron.demo.relayfunctiondemo.model.Group;
import of.samiron.demo.relayfunctiondemo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {
}
