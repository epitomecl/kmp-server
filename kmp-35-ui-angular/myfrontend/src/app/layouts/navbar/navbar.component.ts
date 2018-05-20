import {Component} from '@angular/core';
import {BreakpointObserver, Breakpoints, BreakpointState} from '@angular/cdk/layout';
import {Observable} from 'rxjs';

import {SERVER_API_URL} from "../../app.constants";

@Component({
  selector: 'my-nav',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  isHandset: Observable<BreakpointState> = this.breakpointObserver.observe(Breakpoints.Handset);
  server_api_url = SERVER_API_URL;

  constructor(private breakpointObserver: BreakpointObserver) {
  }

  isAuthenticated() {
    return false;
  }

  collapseNavbar() {

  }

  logout() {

  }

  login() {

  }
}
