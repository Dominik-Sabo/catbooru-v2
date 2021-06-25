import { formatDate } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Contest } from 'src/app/models/Contest';

@Component({
  selector: 'app-contest',
  templateUrl: './contest.component.html',
  styleUrls: ['./contest.component.css']
})

export class ContestComponent implements OnInit {
  @Input() contest: Contest;
  filepath:string = null;
  date:string = null;
  hour:string = null;

  constructor(private router:Router) { }

  ngOnInit(): void {
    if(this.contest.winnerPath!=null) this.filepath = this.contest.winnerPath;
    this.date = formatDate(this.contest.timeStop, "dd. MM. yyyy", "en-US");
    this.hour = formatDate(this.contest.timeStop, "ha", "en-US");
  
  }

  onNewClick(){
    this.router.navigate(['new', this.contest.id]);
  }

  onWinnerClick(){
    this.router.navigate(['posts/post', this.contest.winnerId])
  }

  onContestClick(){
    this.router.navigate(['posts', "contest:"+this.contest.name]);
  }
  
}
