import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {
  MatButtonModule,
  MatIconModule, MatListModule,
  MatMenuModule, MatSidenavModule, MatToolbarModule, MatTreeModule
} from "@angular/material";
import {FuncViewComponent} from "./func-view.component";
import {NavbarComponent} from "../../@common/navbar/navbar.component";
import {SidenavComponent} from "../../@common/sidenav/sidenav.component";
import {RouterModule} from "@angular/router";

@NgModule({
  imports: [
    CommonModule,
    RouterModule,

    MatListModule,
    MatMenuModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatTreeModule
  ],
  declarations: [
    FuncViewComponent,
    NavbarComponent,
    SidenavComponent
  ]
})
export class FuncViewModule {
}
