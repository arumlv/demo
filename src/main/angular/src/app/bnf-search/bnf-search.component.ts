import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SearchService } from '../service/search.service';
import { SpringPageInfo } from '../model/sping-page-info';
import { Register } from '../model/register';
import { PageEvent } from '@angular/material/paginator';
import { DateFormatPipe } from '../service/dateformatpipe';


@Component({
  selector: 'app-bnf-search',
  templateUrl: './bnf-search.component.html',
  styleUrls: ['./bnf-search.component.css']
})
export class BnfSearchComponent implements OnInit {

  searchForm: FormGroup;
  isLoadingResults = false;
  page: SpringPageInfo;
  fparams: { [key: string]: string; } = {};

  displayedColumns = ['regCode', 'typeText', 'name', 'address', 'registered', 'terminated'];
  innerDisplayedColumns = ['foreName', 'surName', 'latvianIdentityNumber', 'birthDate'];
  data: Register[];

  constructor(private readonly fb: FormBuilder, private readonly searchService: SearchService,
    private readonly dateFormatPipe: DateFormatPipe) {

    this.searchForm = this.fb.group({
      regCode: null,
      foreName: null,
      surName: null,
      birthDate: null,
    });
  }

  ngOnInit(): void {

  }

  pageEvent(event: PageEvent) {
    this.findByParams(event.pageIndex);
  }

  private fillParams(): void {
    for (let [key, value] of Object.entries(this.searchForm.value)) {
      if (value === null || value === '') {
        delete this.fparams[key];
      } else {
        if (key === 'birthDate') {
          this.fparams[key] = this.dateFormatPipe.transform(value);
        } else {
          this.fparams[key] = '' + value;
        }
      }
    }
  }

  public onSubmit(): void {
    this.fillParams();
    this.findByParams(0);
  }

  public findByParams(page: number): void {
    this.isLoadingResults = true;
    this.fparams['page'] = '' + page;
    this.searchService.findByParams(this.fparams).subscribe(
      result => {
        this.page = result.page;
        this.isLoadingResults = false;
        this.data = result._embedded['registers'];
      }
    );
  }
}
