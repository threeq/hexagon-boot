import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";
import {SystemApiService} from "../@common/api/system-api.service";
import {ResponseEntity} from "../@common/api/base-api";
import {Router} from "@angular/router";

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

  private name: string;
  private pwd: string;

  constructor(private translate: TranslateService,
              private systemApi: SystemApiService,
              private route: Router) { }

  ngOnInit() { }

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

  gotoHelp() {

  }

  forgetPwd() {

  }

  doLogin() {
    this.systemApi.login(this.name, this.pwd).subscribe((data)=>{
      console.log("login result: ", data);

      if(this.systemApi.checkOK(<ResponseEntity>data)) {
        this.route.navigateByUrl("/modules/dashboard");
      } else {
        console.log("login error")
      }
    })
  }
}

