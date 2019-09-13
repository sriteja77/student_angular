import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';


import { Student } from './Student';

@Injectable()
export class ArticleService {
    //URLs for CRUD operations
    allArticlesUrl = "http://localhost:8066/user/all-articles";
	articleUrl = "http://localhost:8066/user/article";
	//Create constructor to get Http instance
	constructor(private http:Http) { 
	}
	//Fetch all articles
    getAllArticles(): Observable<Student[]> {
        return this.http.get(this.allArticlesUrl)
		   		.map(this.extractData)
		        .catch(this.handleError);

    }
	//Create article
    createArticle(Student: Student):Observable<number> {
	    let cpHeaders = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: cpHeaders });
        return this.http.post(this.articleUrl, Student, options)
               .map(success => success.status)
               .catch(this.handleError);
    }
	//Fetch article by id
    getArticleById(studentId: string): Observable<Student> {
		let cpHeaders = new Headers({ 'Content-Type': 'application/json' });
		let cpParams = new URLSearchParams();
		cpParams.set('id', studentId);			
		let options = new RequestOptions({ headers: cpHeaders, params: cpParams });
		return this.http.get(this.articleUrl, options)
			   .map(this.extractData)
			   .catch(this.handleError);
    }	
	//Update article
    updateArticle(Student: Student):Observable<number> {
	    let cpHeaders = new Headers({ 'Content-Type': 'application/json' });
		let options = new RequestOptions({ headers: cpHeaders });
        return this.http.put(this.articleUrl, Student, options)
               .map(success => success.status)
               .catch(this.handleError);
    }
    //Delete article	
    deleteArticleById(studentId: string): Observable<number> {
		let cpHeaders = new Headers({ 'Content-Type': 'application/json' });
		let cpParams = new URLSearchParams();
		cpParams.set('id', studentId);			
		let options = new RequestOptions({ headers: cpHeaders, params: cpParams });
		return this.http.delete(this.articleUrl, options)
			   .map(success => success.status)
			   .catch(this.handleError);
    }		
	private extractData(res: Response) {
	    let body = res.json();
        return body;
    }
    private handleError (error: Response | any) {
		console.error(error.message || error);
		return Observable.throw(error.status);
    }
}