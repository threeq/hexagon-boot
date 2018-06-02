import {ExtraOptions, RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {NavComponent} from "./nav/nav.component";
import {TableComponent} from "./table/table.component";
import {FuncViewComponent} from "../@views/func/func-view.component";


const routes: Routes = [
  {
    path: '',
    component: FuncViewComponent,
    children: [{
      path: 'dashboard',
      component: DashboardComponent,
    }, {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
    }, {
      path: 'table',
      component: TableComponent
    }, {
      path: 'topic',
      loadChildren: './topic/topic.module#TopicModule',
    }, {
      path: 'knowledge',
      loadChildren: './knowledge/knowledge.module#KnowledgeModule',
    }],
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModulesRoutingModule {
}
