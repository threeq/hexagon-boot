import { Component, OnInit } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private translate: TranslateService) { }

  changeLang(lang) {
    console.log("Using language: " + lang);
    this.translate.use(lang);
  }
  toggleLang() {
    console.log(this.translate.getBrowserLang());
    //获取语言风格，相当于更详细的语言类型，比如zh-CN、zh-TW、en-US
    console.log(this.translate.getBrowserCultureLang());
  }

  ngOnInit() {
  }

}
