import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {


  loginName = new FormControl('', [
    Validators.required,
    Validators.minLength(3)]);
  passWord = new FormControl('', [
    Validators.required,
    Validators.minLength(6)]);

  constructor(private translate: TranslateService) { }

  changeLang(lang) {
    console.log("Using language: " + lang);
    this.translate.use(lang);
  }

  getErrorMessage(field) {
    return field.hasError('required') ? 'error-required' :
      field.hasError('email') ? 'error-email' :
      field.hasError('minlength') ? 'error-length' :
        '';
  }

  ngOnInit() {
  }

  gotoHelp() {

  }

  forgetPwd() {

  }
}

