import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOwnerOperator, OwnerOperator } from 'app/shared/model/owner-operator.model';
import { OwnerOperatorService } from './owner-operator.service';
import { OwnerOperatorComponent } from './owner-operator.component';
import { OwnerOperatorDetailComponent } from './owner-operator-detail.component';
import { OwnerOperatorUpdateComponent } from './owner-operator-update.component';

@Injectable({ providedIn: 'root' })
export class OwnerOperatorResolve implements Resolve<IOwnerOperator> {
  constructor(private service: OwnerOperatorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOwnerOperator> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ownerOperator: HttpResponse<OwnerOperator>) => {
          if (ownerOperator.body) {
            return of(ownerOperator.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OwnerOperator());
  }
}

export const ownerOperatorRoute: Routes = [
  {
    path: '',
    component: OwnerOperatorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jioTptApplicationApp.ownerOperator.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OwnerOperatorDetailComponent,
    resolve: {
      ownerOperator: OwnerOperatorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jioTptApplicationApp.ownerOperator.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OwnerOperatorUpdateComponent,
    resolve: {
      ownerOperator: OwnerOperatorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jioTptApplicationApp.ownerOperator.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OwnerOperatorUpdateComponent,
    resolve: {
      ownerOperator: OwnerOperatorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jioTptApplicationApp.ownerOperator.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
