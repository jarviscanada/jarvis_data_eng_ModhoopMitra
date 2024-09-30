import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TraderAccountComponent } from './trader-account/trader-account.component';
import { QuoteListComponent } from './quote-list/quote-list.component';

const routes: Routes = [
  {path: 'dashboard', component: DashboardComponent},
  {path: '', component: DashboardComponent},
  {path: 'trader/:id', component: TraderAccountComponent},
  {path: 'dashboard/trader/:id', redirectTo: 'trader/:id', pathMatch: 'full'},
  {path: 'quotes', component: QuoteListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
