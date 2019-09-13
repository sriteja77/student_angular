package com.concretepage.dao;

import java.util.List;

import com.concretepage.entity.Student;



public interface IStudentDAO {
	  List<Student> getAllStudents();

	Student getStudentById(int id);
	void createStudent(Student student);
	void updateStudent(Student student);
	void deleteStudent(int id);
	boolean studentExists(String name, String college);
	 
}
