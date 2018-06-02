import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {DashboardComponent} from './dashboard/dashboard.component';
import {ModulesRoutingModule} from "./modules-routing.module";
import {
  MatButtonModule,
  MatCardModule,
  MatGridListModule,
  MatIconModule,
  MatListModule,
  MatMenuModule, MatSidenavModule, MatToolbarModule, MatTableModule, MatPaginatorModule, MatSortModule, MatTreeModule
} from "@angular/material";
import { TableComponent } from './table/table.component';
import {FuncViewComponent} from "../@views/func/func-view.component";
import {NavbarComponent} from "../@common/navbar/navbar.component";
import {SidenavComponent} from "../@common/sidenav/sidenav.component";


const PAGES_COMPONENTS = [
  DashboardComponent,
  FuncViewComponent,
  NavbarComponent,
  SidenavComponent
];

@NgModule({
  imports: [
    CommonModule,
    ModulesRoutingModule,

    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatTreeModule
  ],
  declarations: [
    ...PAGES_COMPONENTS,
    TableComponent
  ]
})
export class ModulesModule {
}
