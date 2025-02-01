import {Component} from '@angular/core';
import {NgForOf} from "@angular/common";
import {CardsService} from "./data-access-layer/cards.service";

@Component({
  selector: 'app-cards',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './cards.component.html',
  styleUrl: './cards.component.css'
})
export class CardsComponent {

  cards: any[] = [
    {
      title: 'Card 1',
      content: 'This is the first card'
    },
    {
      title: 'Card 2',
      content: 'This is the second card'
    }
  ];

  constructor(private cardsService: CardsService) {
  }

  ngOnInit() {
    this.cardsService.findAll().subscribe((data: any) => {
      this.cards = data;
    });
  }
}
