import { Component, OnInit } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-login-header',
  templateUrl: './login-header.component.html',
  styleUrls: ['./login-header.component.scss']
})
export class LoginHeaderComponent implements OnInit {

  constructor(private translate: TranslateService) { }

  ngOnInit() {
  }

  changeLang(lang) {
    console.log("Using language: " + lang);
    this.translate.use(lang);
  }
}
