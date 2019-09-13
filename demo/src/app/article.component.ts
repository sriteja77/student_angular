import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ArticleService } from './article.service';
import { Student } from './Student';

@Component({
   selector: 'app-article',
   templateUrl: './article.component.html',
   styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit { 
   //Component properties
   allArticles: Student[];
   statusCode: number;
   requestProcessing = false;
   articleIdToUpdate = null;
   processValidation = false;
   //Create form
   articleForm = new FormGroup({
       name: new FormControl('', Validators.required),
       college: new FormControl('', Validators.required),
       email: new FormControl('', Validators.required)	   
   });
   //Create constructor to get service instance
   constructor(private articleService: ArticleService) {
   }
   //Create ngOnInit() and and load articles
   ngOnInit(): void {
	   this.getAllArticles();
   }   
   //Fetch all articles
   getAllArticles() {
        this.articleService.getAllArticles()
		  .subscribe(
                data => this.allArticles = data,
                errorCode =>  this.statusCode = errorCode); 
                 
   }
   print() {
      console.log(this.allArticles);
   }
   //Handle create and update article
   onArticleFormSubmit() {
	  this.processValidation = true;   
	  if (this.articleForm.invalid) {
	       return; //Validation failed, exit from method.
	  }   
	  //Form is valid, now perform create or update
     //; this.preProcessConfigurations();
	  let name = this.articleForm.get('name').value.trim();
      let college = this.articleForm.get('college').value.trim();	
      let email = this.articleForm.get('email').value.trim();	

	  if (this.articleIdToUpdate === null) {  
	    //Handle create article
	    let article= new Student(null, name, college,email,null);	  
	    this.articleService.createArticle(article)
	      .subscribe(successCode => {
		            this.statusCode = successCode;
				    this.getAllArticles();	
					this.backToCreateArticle();
			    },
		        errorCode => this.statusCode = errorCode);
    }
     else {  
         //Handle update article
      let article= new Student(this.articleIdToUpdate, name, college,email,null); 
	    this.articleService.updateArticle(article)
	      .subscribe(successCode => {
		            this.statusCode = successCode;
				    this.getAllArticles();	
					this.backToCreateArticle();
			    },
		        errorCode => this.statusCode = errorCode);	  
	  }
   }
   //Load article by id to edit
   loadArticleToEdit(articleId: string) {
      this.preProcessConfigurations();
      this.articleService.getArticleById(articleId)
	      .subscribe(article => {
                this.articleIdToUpdate = articleId;
		            this.articleForm.setValue({ name: article.name, college: article.college, email: article.email, joinedDate: article.joinedDate });
					this.processValidation = true;
					this.requestProcessing = false;   
		        },
		        errorCode =>  this.statusCode = errorCode);   
   }
 //Delete article
   deleteArticle(articleId: string) {
      this.preProcessConfigurations();
     this.articleService.deleteArticleById(articleId)
	      .subscribe(successCode => {
		            this.statusCode = successCode;
	 			    this.getAllArticles();	
				    //this.backToCreateArticle();
		    },
		        errorCode => this.statusCode = errorCode);    
   }
 //Perform preliminary processing configurations
  preProcessConfigurations() {
      this.statusCode = null;
 }
 // Go back from update to create
   backToCreateArticle() {
     this.articleIdToUpdate = null;
     this.articleForm.reset();	  
	  this.processValidation = false;
    }
}
    