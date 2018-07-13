import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from "@angular/router";
import { ChatComponent } from './chat/chat.component';
import {FormsModule} from "@angular/forms";
import {MatCardModule, MatInputModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatInputModule,
    MatCardModule,
    RouterModule.forChild([
      {
        path: '',
        component: ChatComponent,
      }]),
  ],
  declarations: [ChatComponent]
})
export class HomeModule { }
