package com.concretepage.dao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.concretepage.entity.Student;
@Transactional
@Repository
public class StudentDAO implements IStudentDAO{
	@PersistenceContext	
	private EntityManager entityManager;	
	@Override
	public Student getStudentById(int id) {
		return entityManager.find(Student.class, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getAllStudents() {
		String hql = "FROM Student as std ORDER BY std.id DESC";
		return (List<Student>) entityManager.createQuery(hql).getResultList();
	}	
	@Override
	public void createStudent(Student student) {
		   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();  
		    System.out.println(formatter.format(date));
		    student.setJoinedDate(date);
		entityManager.persist(student);
	}
	@Override
	public void updateStudent(Student student) {
		Student std = getStudentById(student.getId());
		std.setName(student.getName());
		std.setCollege(student.getCollege());
		entityManager.flush();
	}
	@Override
	public void deleteStudent(int id) {
		entityManager.remove(getStudentById(id));
	}
	@Override
	public boolean studentExists(String name, String college) {
		String hql = "FROM Student as std WHERE std.name = ? and std.college = ?";
		int count = entityManager.createQuery(hql).setParameter(1, name)
		              .setParameter(2, college).getResultList().size();
		return count > 0 ? true : false;
	}

}
