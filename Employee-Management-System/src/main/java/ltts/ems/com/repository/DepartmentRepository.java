package ltts.ems.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ltts.ems.com.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

    List<Department> findById(int deptId);;

}
