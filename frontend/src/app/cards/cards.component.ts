import {Component} from '@angular/core';
import {NgForOf} from "@angular/common";
import {CardsService} from "./data-access-layer/cards.service";
import {FormsModule} from "@angular/forms";

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

  constructor(private cardsService: CardsService) {
  }

  ngOnInit() {
    this.getAllCards();
  }

  getAllCards() {
    this.cardsService.findAll().subscribe((data: any) => {
      this.cards = data;
    });
  }

  addCard() {
    this.cardsService.send({message: this.content}).subscribe(() => {
      this.content = '';
      setTimeout(() => {
        this.getAllCards()
      }, 2000);
    });
  }
}
