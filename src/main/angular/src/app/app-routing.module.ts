import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JobListComponent } from './job-list/job-list.component';
import { BnfSearchComponent } from './bnf-search/bnf-search.component';

const routes: Routes = [
  { path: '', redirectTo: '/job', pathMatch: 'full' },
  { path: 'job', component: JobListComponent },
  { path: 'search', component: BnfSearchComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
