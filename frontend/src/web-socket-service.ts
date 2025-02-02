import {Injectable} from "@angular/core";
import {Client, IMessage} from "@stomp/stompjs";
import {CardsService} from "./app/cards/data-access-layer/cards.service";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: Client;

  private cardSubject = new BehaviorSubject<any>(null);
  card$ = this.cardSubject.asObservable();

  constructor(private cardsService: CardsService,) {

    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8081/ws',
      connectHeaders: {},
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('Connected to WebSocket');
        // Subscribe to the topic after connecting
        this.stompClient.subscribe('/topic/cardSaved', (message: IMessage) => {
          this.handleCardSavedNotification(JSON.parse(message.body));
        });
      },
      onStompError: (frame) => {
        console.error('Error on WebSocket connection', frame);
      }
    });
  }

  connect() {
    this.stompClient.activate();
  }

  private handleCardSavedNotification(card: any) {
    console.log('Received card saved notification:', card);
    this.cardSubject.next(card);
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.deactivate().then(r => console.log('Disconnected from WebSocket'));
    }
  }
}
