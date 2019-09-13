package com.concretepage.service;

import java.util.List;

import com.concretepage.entity.Student;

public interface IStudentService {
	  List<Student> getAllStudents();

		Student getStudentById(int id);
		
		void updateStudent(Student student);
		void deleteStudent(int id);
	
		boolean createStudent(Student student);
}
