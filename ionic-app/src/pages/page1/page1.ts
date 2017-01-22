import {Component} from '@angular/core';
import {NavController, AlertController} from 'ionic-angular';
import {timeline, utils} from 'timeline-ionicAdapter'

@Component({
  selector: 'page-page1',
  templateUrl: 'page1.html'
})
export class Page1 {

  constructor(public navCtrl: NavController, public alertCtrl: AlertController, public serviceProvider: timeline.IonicServiceProvider) {

  }

  doAlert() {
    let elementService = this.serviceProvider.elementService;
    let start = new utils.date.DateTime(2017, 1, 22, 12, 0, 0, 0);
    let duration = new utils.date.Duration(0);
    let element = new timeline.domain.elements.ScheduledElement(null, "fancy title", "fancy note", start, duration, null);
    let newElement = elementService.saveScheduledElement(element);
    let alert = this.alertCtrl.create({
      title: 'ServiceProviderTest',
      message: newElement.toString(),
      buttons: ['Ok']
    });
    alert.present()
  }

}
