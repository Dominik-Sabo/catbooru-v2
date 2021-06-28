import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ContestService } from '../../services/contest.service'
import { Contest } from '../../models/Contest'
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-contests',
  templateUrl: './contests.component.html',
  styleUrls: ['./contests.component.css']
})
export class ContestsComponent implements OnInit {

  @Output() emitter = new EventEmitter<string>();
  newFlag:boolean = false;
  admin:boolean;
  contests:Contest[];
  notfound:string = ''
  name:string = '';
  description:string = '';
  duration:string = '';
  show:boolean = false;

  constructor(private contestService:ContestService, private userService:UserService) { }

  ngOnInit(): void {
    if (this.userService.user!=null) this.admin = this.userService.user.admin
    this.contestService.getContests().subscribe(contests => {
      this.contests = contests;
      if(this.contests.length == 0) this.notfound = "No contests found";
    });
  }

  onNewClick(){
    this.newFlag = !this.newFlag;
  }

  onSubmitClick(){
    this.contestService.newContest(this.name, this.description, this.duration).subscribe(
      uploaded => {
        this.emitter.emit('contest');
      },
      error => {
        this.show = true;
      });
  }

  onNameKey(event) {this.name = event.target.value; if(event.key === 'Enter') this.onSubmitClick();}

  onDescriptionKey(event) {this.description = event.target.value;}

  onDurationKey(event) {this.duration = event.target.value; if(event.key === 'Enter') this.onSubmitClick();}


}
