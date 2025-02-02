import {Component} from '@angular/core';
import {NgForOf} from "@angular/common";
import {CardsService} from "./data-access-layer/cards.service";
import {FormsModule} from "@angular/forms";
import {WebSocketService} from "../../web-socket-service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-cards',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule
  ],
  templateUrl: './cards.component.html',
  styleUrl: './cards.component.css'
})
export class CardsComponent {

  cards: any[] | undefined;

  content: string | undefined;

  private subscription: Subscription | undefined;

  constructor(private cardsService: CardsService, private webSocketService: WebSocketService) {
  }

  ngOnInit() {
    this.getAllCards();
    this.webSocketService.connect();

    this.subscription = this.webSocketService.card$.subscribe(card => {
      if (card) {
        if (this.cards) {
          console.log(card);
          this.cards.unshift(card);
        }
      }
    });
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
    this.webSocketService.disconnect();
  }

  getAllCards() {
    this.cardsService.findAll().subscribe((data: any) => {
      this.cards = data;
    });
  }

  addCard() {
    this.cardsService.send({message: this.content}).subscribe(() => {
      this.content = '';
    });
  }
}
