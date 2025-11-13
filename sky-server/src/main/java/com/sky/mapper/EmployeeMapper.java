package com.sky.mapper;
import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @author 32770
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     *
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入员工数据
     * @param employee
     */
    @AutoFill(OperationType.UPDATE)
    @Insert("insert into employee values (null,#{name}, #{username}, #{password},#{phone}, #{sex}, #{idNumber}," +
            "#{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO dto);

    @AutoFill(OperationType.UPDATE)
    void update(Employee employee);

    Employee getById(Long id);
}
