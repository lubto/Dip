package eu.lms.iface.service;

import eu.lms.iface.dto.AccountUserDto;
import eu.lms.iface.dto.CourseItemDto;
import eu.lms.iface.dto.UserBankQDto;
import java.util.List;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 *
 */
public interface AccountUserService {

    /**
     * This method is used to get all AccountUsers.
     *
     * @return List This returns the list of AccountUsers.
     */
    List<AccountUserDto> getAllAccountUser();

    /**
     * This method is used to save AccountUser.
     *
     * @param accountItemDto This parameter is entity which we want to
     * save/update.
     */
    void saveAccountUser(AccountUserDto accountItemDto);

    /**
     * This method is used to create AccountUser.
     *
     * @param accountUserDto This parameter is entity which we want to create.
     */
    void createAccounUserDto(AccountUserDto accountUserDto);

    /**
     * This method is used to get AccountUser by ID.
     *
     * @param id This parameter is ID of account user.
     * @return AccountUsserDto This returns the AccountUserDto.
     */
    AccountUserDto getAccountUserId(Long id);

    /**
     * This method is used to delete AccountUser by ID.
     *
     * @param id This parameter is ID of account user.
     * @return AccountUsserDto This returns the AccountUserDto.
     */
    AccountUserDto deleteAccountUserById(Long id);

    //List<CourseItemDto> getAllCourseItemByUserId(String userId);
    /**
     * This method is used to get AccountUser by user´s ID.
     *
     * @param userId This parameter is ID of user.
     * @return AccountUsserDto This returns the AccountUserDto.
     */
    AccountUserDto getAccountUserByUserId(String userId);

    /**
     * This method is used to add course item to account user.
     *
     * @param courseItemDto This parameter is courseItemDto.
     * @param id This parameter is ID of account user.
     * @param List This parameter is list of user´s bank of questions.
     */
    void addCourseItemToAccountUser(CourseItemDto courseItemDto, Long id, List<UserBankQDto> listUserBankQ);

    /**
     * This method is used to remove course item from account user.
     *
     * @param courseItemDto This parameter is courseItemDto.
     * @param id This parameter is ID of account user.
     */
    void removeCourseItem(CourseItemDto courseItemDto, Long id);

    /**
     * This method is used to get all course items.
     *
     * @param accountUserDto This parameter is accountUserDto.
     * @return List This returns the list of CourseItemDto.
     */
    List<CourseItemDto> getAllCourseItems(AccountUserDto accountUserDto);

    /**
     * This method is used to get all user´s IDs from Account users.
     *
     * @return List This returns the list of users IDs.
     */
    List<String> getAllUserIdFromAccounUser();

    /**
     * This method is used to change visible and invisible account user in the
     * portal.
     *
     * @param item This parameter is boolean value, if it is true, account user
     * is visible in the portal. If the value is false, account user is
     * invisible in the portal.
     * @param id This parameter is ID of account user.
     */
    void changeActivateAccountUser(Boolean item, Long id);

    /**
     * This method is used to search in table of account users.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of account users.
     * @return List This returns the list of AccountUserDto where the word is
     * being searched.
     */
    List<AccountUserDto> searchInList(String searchWord, List<AccountUserDto> list);
}
