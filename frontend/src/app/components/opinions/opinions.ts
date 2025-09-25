import { Component } from '@angular/core';

@Component({
  selector: 'app-opinions',
  imports: [],
  templateUrl: './opinions.html',
  styleUrl: './opinions.css'
})
export class Opinions {
  quotes = [
    {
      text: 'I am Groot.',
      image: 'https://cdn.wallpapersafari.com/41/38/ruKsD7.jpg',
    },
    {
      text: "You're the head of security and your password is 'password'?",
      image:
        'https://r4.wallpaperflare.com/wallpaper/340/7/602/spider-man-spider-man-far-from-home-tom-holland-hd-wallpaper-fd155d90a9273dced67d96fdc3b7e466.jpg',
    },
    {
      text: "That really is America's ass.",
      image: 'https://images8.alphacoders.com/101/1012160.jpg',
    },
  ];
}
