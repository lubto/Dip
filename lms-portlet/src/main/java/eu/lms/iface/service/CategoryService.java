package eu.lms.iface.service;

import eu.lms.iface.dto.CategoryDto;
import java.util.List;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 *
 */
public interface CategoryService {

    /**
     * This method is used to get all category.
     *
     * @return List This returns the list of category.
     */
    List<CategoryDto> getAllCategory();

    /**
     * This method is used to save category.
     *
     * @param categoryDto This parameter is entity which we want to save/update.
     */
    void saveCategory(CategoryDto categoryDto);

    /**
     * This method is used to create category.
     *
     * @param categoryDto This parameter is entity which we want to create.
     */
    void createCategory(CategoryDto categoryDto);

    /**
     * This method is used to get category by ID.
     *
     * @param id This parameter is ID of category.
     * @return CategoryDto This returns the category.
     */
    CategoryDto getCategoryById(Long id);

    /**
     * This method is used to delete category by ID.
     *
     * @param id This parameter is ID of category.
     * @return CategoryDto This returns the category.
     */
    CategoryDto deleteCategoryById(Long id);

    /**
     * This method is used to change visible and invisible category in the
     * portal.
     *
     * @param item This parameter is boolean value, if it is true, category is
     * visible in the portal. If the value is false, category is invisible in
     * the portal.
     * @param id This parameter is ID of category.
     */
    void changeActivate(Boolean item, Long id);

    /**
     * This method is used to get all category which are free for adding by
     * users.
     *
     * @return List This returns the list of category
     */
    List<CategoryDto> getFreeCategory();

    /**
     * This method is used to search in table of category.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of category.
     * @return List This returns the list of category where the word is being
     * searched.
     */
    List<CategoryDto> searchInList(String searchWord, List<CategoryDto> list);
}
