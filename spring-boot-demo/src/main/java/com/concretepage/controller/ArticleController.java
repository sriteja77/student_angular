package com.concretepage.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.concretepage.entity.Student;

import com.concretepage.service.IStudentService;

@Controller
@RequestMapping("user")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ArticleController {
	@Autowired
	private IStudentService studentService;
	@GetMapping("article")
	public ResponseEntity<Student> getStudentById(@RequestParam("id") String id) {
		Student student = studentService.getStudentById(Integer.parseInt(id));
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}
	@GetMapping("all-articles")
	public ResponseEntity<List<Student>> getAllStudents() {
		List<Student> list = studentService.getAllStudents();
		return new ResponseEntity<List<Student>>(list, HttpStatus.OK);
	}
	@PostMapping("article")
	public ResponseEntity<Void> createStudent(@RequestBody Student student, UriComponentsBuilder builder) {
        boolean flag = studentService.createStudent(student);
        if (flag == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/article?id={id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	@PutMapping("article")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
		studentService.updateStudent(student);
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}
	@DeleteMapping("article")
	public ResponseEntity<Void> deleteStudent(@RequestParam("id") String id) {
		studentService.deleteStudent(Integer.parseInt(id));
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
} 