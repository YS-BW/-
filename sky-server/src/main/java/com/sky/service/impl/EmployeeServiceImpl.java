package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 添加员工
     *
     * @param dto
     */
    @Override
    public void addEmp(EmployeeDTO dto) {
        Employee employee = new Employee();
        //对象拷贝👇
        BeanUtils.copyProperties(dto, employee);
        //1️⃣补充属性
        //设置初始密码，进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setStatus(StatusConstant.ENABLE);
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        //从BaseContext中获取当前登录用户id
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        //2️⃣调用mapper
        employeeMapper.insert(employee);

    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO dto) {
        //1️⃣设置分页参数
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        //2️⃣调用mapper
        Page<Employee> page = employeeMapper.pageQuery(dto);
        List<Employee> records = page.getResult();
        //3️⃣获取total
        long total = page.getTotal();
        //4️⃣返回结果
        return new PageResult(total, records);
    }

    /**
     * 修改员工状态
     *
     * @param status
     * @param id
     */
    @Override
    public void resetStatus(Integer status, Long id) {
        Employee employee = Employee.builder()
                                    .status(status)
                                    .id(id)
                                    .updateTime(LocalDateTime.now())
                                    .updateUser(BaseContext.getCurrentId())
                                    .build();
        employeeMapper.update(employee);
    }

    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        if (employee != null) {
            return employee;
        }
        return null;
    }

    @Override
    public void updateEmp(EmployeeDTO dto) {
        //1️⃣对象拷贝
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        //2️⃣补全属性
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        //3️⃣调用mapper
        employeeMapper.update(employee);
    }

}
