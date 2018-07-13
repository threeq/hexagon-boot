import {AfterViewChecked, AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Message, MessageService} from "./message";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit,AfterViewChecked {

  @ViewChild('messageBottom')
  messageBottom: ElementRef;

  constructor(private messageService: MessageService) {

  }

  ngOnInit() {
    this._messageScrollBottom();
  }
  ngAfterViewChecked() {
    this._messageScrollBottom();
  }

  commitMessage(element: any) {
    if(!element.value.trim()) {
      return false;
    }
    this.messageService.add(new Message(element.value));
    element.value = '';
    this._messageScrollBottom();

  }

  _messageScrollBottom() {
    try {
      let element = this.messageBottom.nativeElement;
      element.scrollIntoView({ behavior: 'smooth', block: "start" });
    }catch (e) {
      console.log(e)
    }
  }
}
