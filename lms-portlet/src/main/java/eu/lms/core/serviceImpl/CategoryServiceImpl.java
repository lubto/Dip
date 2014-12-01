package eu.lms.core.serviceImpl;

import eu.lms.core.daoImpl.CategoryDaoImpl;
import eu.lms.core.entity.Category;
import eu.lms.core.mapper.Mapper;
import eu.lms.iface.dto.CategoryDto;
import eu.lms.iface.service.CategoryService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 *
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDaoImpl categoryDao;

    /**
     * This method is used to get all category.
     *
     * @return List This returns the list of category.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategory() {
        List<Category> entities = categoryDao.getAll();

        List<CategoryDto> dtos = new ArrayList<CategoryDto>(entities.size());

        for (Category entity : entities) {
            dtos.add(Mapper.toDto(entity));
        }
        return dtos;
    }

    /**
     * This method is used to save category.
     *
     * @param categoryDto This parameter is entity which we want to save/update.
     */
    @Override
    public void saveCategory(CategoryDto categoryDto) {
        Validate.notNull(categoryDto);
        Validate.notNull(categoryDto.getId());

        Category category = categoryDao.getById(categoryDto.getId());
        //Category updateCategory = categoryDao.save(Mapper.toEntity(category, categoryDto));
        categoryDao.update(Mapper.toEntity(category, categoryDto));
        //return Mapper.toDto(updateCategory);
    }

    /**
     * This method is used to create category.
     *
     * @param categoryDto This parameter is entity which we want to create.
     */
    @Override
    public void createCategory(CategoryDto categoryDto) {
        Validate.notNull(categoryDto);
        Validate.isTrue(categoryDto.getId() == null);

        //Category savedCategoryEntity = categoryDao.save(Mapper.toEntity(null, categoryDto));
        categoryDao.create(Mapper.toEntity(null, categoryDto));
        //return Mapper.toDto(savedCategoryEntity);
    }

    /**
     * This method is used to get category by ID.
     *
     * @param id This parameter is ID of category.
     * @return CategoryDto This returns the category.
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        Validate.notNull(id);

        Category category = categoryDao.getById(id);

        return Mapper.toDto(category);
    }

    /**
     * This method is used to delete category by ID.
     *
     * @param id This parameter is ID of category.
     * @return CategoryDto This returns the category.
     */
    @Override
    public CategoryDto deleteCategoryById(Long id) {
        Validate.notNull(id);

        Category category = categoryDao.getById(id);

        categoryDao.delete(category);

        return Mapper.toDto(category);
    }

    /**
     * This method is used to change visible and invisible category in the
     * portal.
     *
     * @param item This parameter is boolean value, if it is true, category is
     * visible in the portal. If the value is false, category is invisible in
     * the portal.
     * @param id This parameter is ID of category.
     */
    @Override
    public void changeActivate(Boolean item, Long id) {
        Validate.notNull(id);
        Validate.notNull(item);

        Category entity = categoryDao.getById(id);
        entity.setVisible(item);
    }

    /**
     * This method is used to get all category which are free for adding by
     * users.
     *
     * @return List This returns the list of category
     */
    @Override
    public List<CategoryDto> getFreeCategory() {
        List<CategoryDto> listDto = new ArrayList<CategoryDto>();
        List<Category> list = categoryDao.getAll();
        for (Iterator<Category> it = list.iterator(); it.hasNext();) {
            Category category = it.next();
            if (category.isFreeCourse() == true && category.isVisible() == true) {
                listDto.add(Mapper.toDto(category));
            }
        }
        return listDto;
    }

    /**
     * This method is used to search in table of category.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of category.
     * @return List This returns the list of category where the word is
     * being searched.
     */
    @Override
    public List<CategoryDto> searchInList(String searchWord, List<CategoryDto> list) {
        String search = searchWord.toLowerCase();
        List<CategoryDto> listNew = new ArrayList<CategoryDto>();
        for (Iterator<CategoryDto> it = list.iterator(); it.hasNext();) {
            CategoryDto categoryDto = it.next();
            String name = categoryDto.getName().toLowerCase();
            if (name.contains(search)) {
                listNew.add(categoryDto);
            }
        }
        return listNew;
    }

}
