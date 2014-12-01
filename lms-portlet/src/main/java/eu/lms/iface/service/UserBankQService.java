package eu.lms.iface.service;

import eu.lms.iface.dto.ItemAttemptsDto;
import eu.lms.iface.dto.UserBankQDto;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Service providing access to the data-access-layer.
 */
public interface UserBankQService {

    UserBankQDto getById(Long id);

    UserBankQDto deleteById(Long id);

    void addItemAttempts(ItemAttemptsDto itemDto, UserBankQDto userBankQDto);

}
