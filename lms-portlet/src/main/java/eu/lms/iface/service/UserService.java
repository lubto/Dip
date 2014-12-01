package eu.lms.iface.service;

import eu.lms.iface.dto.UserDto;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Service providing access to the data-access-layer.
 */
public interface UserService {

    List<UserDto> getAllUser();

    void save(UserDto userDto);

    void create(UserDto userDto);

    UserDto getById(Long id);

    UserDto deleteById(Long id);

}
