import {Component} from '@angular/core';
import {NgClass, NgForOf} from "@angular/common";
import {CardsService} from "./data-access-layer/cards.service";
import {FormsModule} from "@angular/forms";
import {WebSocketService} from "../../web-socket-service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-cards',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule,
    NgClass
  ],
  templateUrl: './cards.component.html',
  styleUrl: './cards.component.css'
})
export class CardsComponent {

  cards: any[] | undefined;

  content: string | undefined;

  winner: number | null = null;

  private subscription: Subscription | undefined;
  private kafkaSubscription: Subscription | undefined;

  constructor(private cardsService: CardsService, private webSocketService: WebSocketService) {
  }

  ngOnInit() {
    this.getAllCards();
    this.webSocketService.connect();
    this.webSocketService.connectToKafka();

    this.subscription = this.webSocketService.card$.subscribe(card => {
      if (card) {
        if (this.cards) {
          console.log(card);
          this.cards.unshift(card);
        }
      }
    });

    this.kafkaSubscription = this.webSocketService.kafkaMessage$.subscribe(message => {
      if (message) {
        console.log("message is ", message);
        alert(message.message);

        const match = message.message.match(/\d+/);
        const winnerNumber = match ? parseInt(match[0], 10) : null;

        console.log("Extracted winner number: ", winnerNumber);
        this.winner = winnerNumber;
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
