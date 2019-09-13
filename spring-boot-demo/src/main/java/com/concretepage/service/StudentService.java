package com.concretepage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.concretepage.dao.IStudentDAO;

import com.concretepage.entity.Student;


@Service
public class StudentService implements IStudentService {
	@Autowired
	private IStudentDAO studentDAO;
	@Override
	public Student getStudentById(int id) {
		Student obj = studentDAO.getStudentById(id);
		return obj;
	}	
	@Override
	public List<Student> getAllStudents(){
		return studentDAO.getAllStudents();
	}
	@Override
	public synchronized boolean createStudent(Student student){
       if (studentDAO.studentExists(student.getName(), student.getCollege())) {
    	   return false;
       } else {
    	   studentDAO.createStudent(student);
    	   return true;
       }
	}
	@Override
	public void updateStudent(Student student) {
		studentDAO.updateStudent(student);
	}
	@Override
	public void deleteStudent(int id) {
		studentDAO.deleteStudent(id);
	}
}
