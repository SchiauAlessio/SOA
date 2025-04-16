import {Injectable} from "@angular/core";
import {Client, IMessage} from "@stomp/stompjs";
import {CardsService} from "./app/cards/data-access-layer/cards.service";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: Client;
  private kafkaStompClient: Client;

  private cardSubject = new BehaviorSubject<any>(null);
  card$ = this.cardSubject.asObservable();

  private kafkaMessageSubject = new BehaviorSubject<any>(null);
  kafkaMessage$ = this.kafkaMessageSubject.asObservable();

  constructor(private cardsService: CardsService,) {

    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8091/ws',
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

    console.log('Creating Kafka WebSocket client');
    this.kafkaStompClient = new Client({
      brokerURL: 'ws://localhost:8093/ws',
      connectHeaders: {},
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('Connected to kafka WebSocket');
        // Subscribe to the topic after connecting
        this.kafkaStompClient.subscribe('/topic/messages', (message: IMessage) => {
          this.handleKafkaNotification(JSON.parse(message.body));
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

  connectToKafka() {
    this.kafkaStompClient.activate();
  }

  private handleCardSavedNotification(card: any) {
    console.log('Received card saved notification:', card);
    this.cardSubject.next(card);
  }

  handleKafkaNotification(message: any) {
    console.log('Received message:', message);
    this.kafkaMessageSubject.next(message);
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.deactivate().then(r => console.log('Disconnected from WebSocket'));
    }

    if (this.kafkaStompClient) {
      this.kafkaStompClient.deactivate().then(r => console.log('Disconnected from Kafka WebSocket'));
    }
  }
}
