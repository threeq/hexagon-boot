import {assertNotBlank} from "../../../plugins/asserts";
import {Injectable} from "@angular/core";

export class Message {
  time: number;
  message: string;

  constructor(msg) {
    assertNotBlank(msg.trim(), "message is empty !");

    this.time = new Date().getTime();
    this.message = msg;
  }
}

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private readonly messages: Message[] = [];
  private readonly permanentMessages: Message[] = [];
  private readonly newlyKey = "newlyMessages";
  private readonly permanentKey = "permanentMessages";
  private readonly cacheMax = 500;

  constructor() {
    this.messages = JSON.parse(window.localStorage.getItem(this.newlyKey)||"[]");
    this.permanentMessages = JSON.parse(window.localStorage.getItem(this.permanentKey)||"[]");
  }

  add(msg:Message) {
    this.messages.push(msg);
    if(this.messages.length>this.cacheMax) {
      this.permanentMessages.push(this.messages.shift());
    }
    console.log(this.permanentMessages)

    this.saveMessage();

  }
  messageList() {
    return this.messages;
  }

  private saveMessage() {
    window.localStorage.setItem(this.newlyKey, JSON.stringify(this.messages));
    window.localStorage.setItem(this.permanentKey, JSON.stringify(this.permanentMessages));
  }
}
